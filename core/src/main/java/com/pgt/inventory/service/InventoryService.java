package com.pgt.inventory.service;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.dao.OrderMapper;
import com.pgt.cart.service.ShoppingCartConfiguration;
import com.pgt.inventory.LockInventoryException;
import com.pgt.inventory.bean.InventoryLock;
import com.pgt.inventory.dao.InventoryLockMapper;
import com.pgt.product.bean.InventoryType;
import com.pgt.product.bean.Product;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.Transactionable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by samli on 2015/12/20.
 */
@Service(value="inventoryService")
public class InventoryService extends Transactionable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    private static final int INVENTORY_LOCK_MIN_COMMIT_ORDER = 15;

    private static final int INVENTORY_LOCK_MIN_START_PAYMENT = 35;

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private InventoryLockMapper inventoryLockMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Resource(name = "shoppingCartConfiguration")
    private ShoppingCartConfiguration mShoppingCartConfiguration;

    public void lockInventory(final Order order) throws LockInventoryException {

        if (null == order) {
            LOGGER.debug("Order is null return.s");
            throw new IllegalArgumentException("order is null");
        }
        int orderId = order.getId();
        LOGGER.debug("-------------------- Lock Inventory for Order(id=" + orderId + ") --------------------");
        List<CommerceItem> commerceItems = order.getCommerceItems();
        if (null == commerceItems || commerceItems.isEmpty()) {
            LOGGER.debug("--------------------  End lock Inventory for Order(id=" + orderId +
                    ") No commerce Item return; -------------------- ");
            return;
        }
        Set<Integer> productIdsOnOrder = commerceItems.stream().map(CommerceItem::getReferenceId).collect(Collectors.toSet());
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + ") productIdsOnOrder: "
                + StringUtils.join(productIdsOnOrder, ","));
        Set<Integer> needLockIdsSet = new HashSet<Integer>(productIdsOnOrder);
        Map<Integer, InventoryLock> existLocks = findExistLock(orderId);
        if (null != existLocks && !existLocks.isEmpty()) {
            needLockIdsSet.addAll(existLocks.keySet());
        }
        List<Integer> needLockIds = new ArrayList<>(needLockIdsSet);
        Collections.sort(needLockIds);
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + ") productIds: " + StringUtils.join(needLockIds, ","));
        Date now = new Date();
        List<Pair<Integer, Integer>> inventoryStatistic =  new ArrayList<Pair<Integer, Integer>>();
        boolean isRollbackOnly = false;
        ensureTransaction();

        try {
            acquireRowLock(needLockIds, orderId);
            removeNonExistLock(existLocks, productIdsOnOrder, orderId, inventoryStatistic);
            lockInventory(order, existLocks, now, inventoryStatistic);
        } catch (Exception e) {
            setAsRollback();
            LOGGER.error("lock inventory failed", e);
            throw e;
        } finally {
            isRollbackOnly = isRollbackOnly();
            commit();
            LOGGER.debug("--------------------  End lock Inventory for Order(id=" + orderId + ") --------------------");
            if (!isRollbackOnly) {
                modifyProductInventoryIndex(inventoryStatistic);
            }
        }
    }

    public void modifyProductInventoryIndex(List<Pair<Integer, Integer>> inventoryStatistic) {
        getEsSearchService().modifyProductInventory(inventoryStatistic);
    }

    public void releaseInventories() {
        LOGGER.debug("-------------------- start release inventories --------------------");
        List<Integer> orderIds = findExpiredOrders();
        if (null == orderIds || orderIds.isEmpty()) {
            LOGGER.debug("-------------------- end release inventories --------------------");
            return;
        }
        LOGGER.debug("Inventory Lock need release for orders: " + StringUtils.join(orderIds, ",") );
        for (Integer orderId : orderIds) {
            releaseInventory(orderId);
        }
        LOGGER.debug("-------------------- end release inventories --------------------");
    }

    private void releaseInventory(Integer orderId) {
        LOGGER.debug("Release inventories for order(id= " + orderId + ")");
        List<InventoryLock> expiredLocks = findExpiredInventoryLock(orderId);
        if (null == expiredLocks || expiredLocks.isEmpty()) {
            LOGGER.debug("no inventories need release for order(id=" + orderId + ")");
            return;
        }
        List<Integer> productIds = new ArrayList<Integer>();
        for (InventoryLock expiredLock : expiredLocks) {
            productIds.add(expiredLock.getProductId().intValue());
        }
        Collections.sort(productIds);
        LOGGER.debug("Release inventories for order(id= " + orderId + ") products:" + StringUtils.join(productIds, ","));
        List<Pair<Integer, Integer>> inventoryStatistic = new ArrayList<Pair<Integer, Integer>>();
        ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        boolean orderCancelled = false;
        boolean isRollbackOnly = false;
        try {
            acquireRowLock(productIds, orderId);
            for (InventoryLock expiredLock : expiredLocks) {
                int productId = expiredLock.getProductId().intValue();
                int quantity = expiredLock.getQuantity();
                LOGGER.debug("Release inventory lock(orderId=" + orderId + "; productId=" + productId +  "; quantity="
                        + quantity+")");
                deleteInventoryLock(expiredLock);
                changeInventory(productId, quantity,InventoryType.INCREASE, orderId,inventoryStatistic);
                if (!orderCancelled) {
                    cancelOrder(expiredLock.getOrderId().intValue());
                }
                orderCancelled = true;
            }
        } catch (Exception e) {
            setAsRollback();
            LOGGER.error("Failed release lock for order(id="  + orderId  + ") ", e);
        } finally {
            isRollbackOnly = isRollbackOnly();
            commit();
            if (!isRollbackOnly) {
                modifyProductInventoryIndex(inventoryStatistic);
            }
        }

    }

    private void cancelOrder(int orderId) {
        LOGGER.debug("Update order status to cancel. order(id= " + orderId + ")");
        Order order = new Order(getShoppingCartConfiguration().getDefaultOrderType());
        order.setId(orderId);
        order.setStatus(OrderStatus.CANCEL);
        getOrderMapper().updateOrderStatus(order);
    }

    private List<InventoryLock> findExpiredInventoryLock(Integer orderId) {
        return getInventoryLockMapper().findExpiredInventoryLock(orderId);
    }

    private List<Integer> findExpiredOrders() {
        return getInventoryLockMapper().findExpiredOrders();
    }

    /**
     * 1. if inventory lock is already exist. maintain quantity and expire date.
     * 2. if inventory lock is not exist. create a new one.
     *
     * @param order
     * @param existLocks
     * @param now
     */
    private void lockInventory(final Order order, final Map<Integer, InventoryLock> existLocks, final Date now, List<Pair<Integer, Integer>> inventoryStatistic) throws LockInventoryException {
        LOGGER.debug("Lock Inventory for Order(id=" + order.getId() + "). getting Inventory lock");
        int lockExpireMin = INVENTORY_LOCK_MIN_COMMIT_ORDER;
        if (OrderStatus.START_PAY == order.getStatus()) {
            lockExpireMin = INVENTORY_LOCK_MIN_START_PAYMENT;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.MINUTE, lockExpireMin);
        Date inventoryLockExpireDate = calendar.getTime();

        boolean allLocked = true;
        Set<Integer> oosProductIds = new HashSet<Integer>();
        for (CommerceItem commerceItem : order.getCommerceItems()) {
            Integer productId = Integer.valueOf(commerceItem.getReferenceId());
            int quantityLeft = queryInventoryQuantity(productId);
            int quantityRequire = commerceItem.getQuantity();
            LOGGER.debug("Lock Inventory for Order(id=" + order.getId() + "). Get Inventory lock. productId="
                    + productId +"; quantityLeft=" + quantityLeft + "; quantityRequire=" + quantityRequire +".");

            if (null == existLocks || null == existLocks.get(productId)) {
                LOGGER.debug("Lock Inventory for Order(id=" + order.getId() + "). No Inventory lock. for product(id="
                        + productId + ")");
                // create a new inventory lock
                if (quantityLeft < quantityRequire) {
                    allLocked = false;
                    oosProductIds.add(productId);
                    LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                            + "). Get Inventory lock failed for product(id=" + productId
                            + "), cause not enough quantity left.");
                    continue;
                }
                InventoryLock newLock = new InventoryLock();
                newLock.setOrderId(Long.valueOf(order.getId()));
                newLock.setProductId(Long.valueOf(productId));
                newLock.setQuantity(quantityRequire);
                newLock.setCreationDate(now);
                newLock.setUpdateDate(now);
                newLock.setExpiredDate(inventoryLockExpireDate);
                createInventoryLock(newLock);
                changeInventory(productId, quantityRequire, InventoryType.DEDUCT, order.getId(), inventoryStatistic);
                LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                        + "). Create Inventory lock success. Inventory lock info(productId=" + productId + "; quantity="
                        + quantityRequire + "; expiredDate= " + inventoryLockExpireDate + ")");
                continue;
            }

            InventoryLock existLock = existLocks.get(Integer.valueOf(productId));
            int quantityLocked = existLock.getQuantity();
            LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                    + "). Exist Inventory lock(productId=" + productId+ "; quantity" + quantityLocked + ")");
            if (quantityLocked == quantityRequire) {
                existLock.setExpiredDate(inventoryLockExpireDate);
                updateInventoryLock(existLock, order.getId());
                LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                        + "). quantityRequire equals exist lock quantity just update expire date to "
                        + inventoryLockExpireDate);
                continue;
            }
            if (quantityLocked > quantityRequire) {
                // release from lock and increase inventory
                existLock.setQuantity(quantityRequire);
                existLock.setExpiredDate(inventoryLockExpireDate);
                updateInventoryLock(existLock, order.getId());
                changeInventory(productId, quantityLocked - quantityRequire, InventoryType.INCREASE, order.getId(), inventoryStatistic);
                LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                        + "). update Inventory lock success. Inventory lock info(productId=" + productId + "; quantity="
                        + quantityRequire + "; expiredDate= " + inventoryLockExpireDate + ")");
                continue;
            }
            if (quantityLeft < (quantityRequire - quantityLocked)) {
                allLocked = false;
                oosProductIds.add(productId);
                LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                        + "). Get Inventory lock failed for product(id=" + productId
                        + "), cause not enough quantity left.");

                continue;
            }
            // deduct inventory
            existLock.setQuantity(quantityRequire);
            existLock.setExpiredDate(inventoryLockExpireDate);
            updateInventoryLock(existLock, order.getId());
            changeInventory(productId, quantityRequire - quantityLocked, InventoryType.DEDUCT, order.getId(), inventoryStatistic);
            LOGGER.debug("Lock Inventory for Order(id=" + order.getId()
                    + "). update Inventory lock success. Inventory lock info(productId=" + productId + "; quantity="
                    + quantityRequire + "; expiredDate= " + inventoryLockExpireDate + ")");
        }
        if (!allLocked) {
            LOGGER.error("Lock Inventory for Order(id=" + order.getId() +
                    ") failed. Cause can't lock inventory for these products: " + StringUtils.join(oosProductIds, ","));
            LockInventoryException exception = new LockInventoryException("Lock Inventory Failed");
            exception.setOosProductIds(oosProductIds);
            throw exception;
        }

    }

    /**
     *  1. remove non-exist commerce item inventory lock
     *  2. increase inventory quantity.
     *  @param existLocks
     * @param productIds
     * @param orderId
     */
    private void removeNonExistLock(final Map<Integer, InventoryLock> existLocks, final Collection<Integer> productIds,
                                    int orderId, List<Pair<Integer, Integer>> inventoryStatistic) {
        if (null == existLocks || existLocks.isEmpty()) {
            LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). No exist locks");
            return ;
        }

        for (Integer productId : existLocks.keySet()) {
            if (!productIds.contains(productId)) {
                LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). Need release Inventory Lock for product(id="
                        + productIds + ")");
                InventoryLock nonExistLock = existLocks.get(productId);
                if (null == nonExistLock) {
                    continue;
                }
                int quantity = nonExistLock.getQuantity();
                deleteInventoryLock(nonExistLock);
                changeInventory(productId, quantity, InventoryType.INCREASE, orderId, inventoryStatistic);

            }
        }
    }

    private void updateInventoryLock(InventoryLock existLock, int orderId) {
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). Inventory Info (productId="
                + existLock.getProductId() + "; quantity=" + existLock.getQuantity() + "); expireDate= "
                + existLock.getExpiredDate());
        getInventoryLockMapper().updateInventoryLock(existLock);
    }

    private void createInventoryLock(InventoryLock newLock) {
        getInventoryLockMapper().createInventoryLock(newLock);
    }

    private void deleteInventoryLock(InventoryLock nonExistLock) {
        Long orderId = nonExistLock.getOrderId();
        Long productId = nonExistLock.getProductId();
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). Remove Inventory Lock(orderId=" + orderId
                + ", productId=" + productId + ")");
        getInventoryLockMapper().deleteInventoryLock(nonExistLock);
    }

    private int queryInventoryQuantity(final int productId) {

        return getInventoryLockMapper().queryInventoryQuantity(productId);
    }



    private void changeInventory(Integer productId, int quantity, InventoryType action, int orderId, List<Pair<Integer, Integer>> inventoryStatistic) {
        // increase inventory in DB

        int quantityLeft = queryInventoryQuantity(productId);
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). Inventory Info (productId=" + productId +
                "), quantity=" + quantityLeft + "). " + " Action: " + action + ". quantity: " + quantity);
        if (InventoryType.DEDUCT == action) {
            quantityLeft -= quantity;
        }
        if (InventoryType.INCREASE == action) {
            quantityLeft += quantity;
        }
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). " + action + " Inventory (productId=" + productId
                + "), to quantity=" + quantityLeft + ")");
        Product product = new Product();
        product.setProductId(productId);
        product.setStock(quantityLeft);
        getInventoryLockMapper().updateInventoryQuantity(product);
        inventoryStatistic.add(Pair.of(productId, quantityLeft));
    }

    /**
     * Find already exist inventory lock in DB.
     * @param orderId
     * @return
     */
    private Map<Integer, InventoryLock> findExistLock(final int orderId) {
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). find exist lock for order(id=" + orderId +")");
        List<InventoryLock> existLocks = getInventoryLockMapper().findInventoryLockByOrderId(orderId);
        if (null == existLocks || existLocks.isEmpty()) {
            LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). No exist lock for order(id=" + orderId +")");
            return Collections.emptyMap();
        }
        Map<Integer, InventoryLock> result = new HashMap<Integer, InventoryLock> ();
        for (InventoryLock existLock : existLocks) {
            result.put(existLock.getProductId().intValue(), existLock);
        }
        LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). exist locks for order(id=" + orderId
                +") products: (" + StringUtils.join(result.keySet(), ",") + ")"  );
        return result;
    }


    private void acquireRowLock(final List<Integer> productIds, int orderId) {
        for (Integer productId : productIds) {
            LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). locking product(id=" + productId +")");
            getInventoryLockMapper().acquireRowLock(productId);
            LOGGER.debug("Lock Inventory for Order(id=" + orderId + "). product(id=" + productId + ") locked.");
        }
    }

    public InventoryLockMapper getInventoryLockMapper() {
        return inventoryLockMapper;
    }

    public void setInventoryLockMapper(InventoryLockMapper inventoryLockMapper) {
        this.inventoryLockMapper = inventoryLockMapper;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public ESSearchService getEsSearchService() {
        return esSearchService;
    }

    public void setEsSearchService(ESSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }

    public ShoppingCartConfiguration getShoppingCartConfiguration () {
        return mShoppingCartConfiguration;
    }

    public void setShoppingCartConfiguration (final ShoppingCartConfiguration pShoppingCartConfiguration) {
        mShoppingCartConfiguration = pShoppingCartConfiguration;
    }
}
