package com.pgt.inventory.service;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.dao.OrderMapper;
import com.pgt.inventory.bean.InventoryLock;
import com.pgt.inventory.dao.InventoryLockMapper;
import com.pgt.product.bean.InventoryType;
import com.pgt.product.bean.Product;
import com.pgt.utils.Transactionable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;

import java.util.*;

/**
 * Created by samli on 2015/12/20.
 */
@Service(value="inventoryService")
public class InventoryService extends Transactionable {

    private InventoryLockMapper inventoryLockMapper;
    private OrderMapper orderMapper;

    public void lockInventory(final Order order) {
        if (null == order) {
            throw new IllegalArgumentException("order is null");
        }

        List<CommerceItem> commerceItems = order.getCommerceItems();
        if (null == commerceItems || commerceItems.isEmpty()) {
            return;
        }
        List<Integer> productIds = new ArrayList<Integer>();
        for (CommerceItem commerceItem : commerceItems) {
            productIds.add(commerceItem.getReferenceId());
        }
        Collections.sort(productIds);
        Date now = new Date();
        ensureTransaction();
        try {
            acquireRowLock(productIds);
            Map<Integer, InventoryLock> existLocks = findExistLock(order.getId());
            removeNonExistLock(existLocks, productIds);
            lockInventory(order, existLocks, now);
        } catch (Exception e) {
            setAsRollback();
            // TODO: THROW WRAPPED EXCEPTION
        } finally {
            commit();
        }
    }

    public void releaseInventories() {
        List<Integer> orderIds = findExpiredOrders();
        if (null == orderIds || orderIds.isEmpty()) {
            // TODO LOG
            return;
        }
        for (Integer orderId : orderIds) {
            releaseInventory(orderId);
        }
    }

    private void releaseInventory(Integer orderId) {
        List<InventoryLock> expiredLocks = findExpiredInventoryLock(orderId);
        if (null == expiredLocks || expiredLocks.isEmpty()) {
            // TODO LOG
            return;
        }
        List<Integer> productIds = new ArrayList<Integer>();
        for (InventoryLock expiredLock : expiredLocks) {
            productIds.add(expiredLock.getProductId().intValue());
        }
        Collections.sort(productIds);
        ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        try {
            acquireRowLock(productIds);
            for (InventoryLock expiredLock : expiredLocks) {
                int productId = expiredLock.getProductId().intValue();
                int quantity = expiredLock.getQuantity();
                deleteInventoryLock(expiredLock);
                changeInventory(productId, quantity,InventoryType.INCREASE);
                cancelOrder(expiredLock.getOrderId().intValue());
            }
        } catch (Exception e) {
            setAsRollback();
            // TODO LOG
        } finally {
            commit();
        }

    }

    private void cancelOrder(int orderId) {
        Order order = new Order();
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
    private void lockInventory(final Order order, final Map<Integer, InventoryLock> existLocks, final Date now) {
        boolean allLocked = true;
        Set<Integer> oosProductIds = new HashSet<Integer>();
        for (CommerceItem commerceItem : order.getCommerceItems()) {
            Integer productId = Integer.valueOf(commerceItem.getReferenceId());
            int quantityLeft = queryInventoryQuantity(productId);
            int quantityRequire = commerceItem.getQuantity();

            if (null == existLocks || null == existLocks.get(productId)) {
                // create a new inventory lock
                if (quantityLeft < quantityRequire) {
                    allLocked = false;
                    oosProductIds.add(productId);
                    continue;
                }
                InventoryLock newLock = new InventoryLock();
                newLock.setOrderId(Long.valueOf(order.getId()));
                newLock.setProductId(Long.valueOf(productId));
                newLock.setQuantity(quantityRequire);
                newLock.setCreationDate(now);
                newLock.setUpdateDate(now);
                // TODO: set expire Date base on order status
                // newLock.setExpiredDate();
                createInventoryLock(newLock);
                changeInventory(productId, quantityRequire, InventoryType.DEDUCT);
                continue;
            }

            InventoryLock existLock = existLocks.get(Integer.valueOf(productId));

            int quantityLocked = existLock.getQuantity();

            if (quantityLocked == quantityRequire) {
                // TODO: set expire Date base on order status
                // existLock.setExpiredDate();
                updateInventoryLock(existLock);
                continue;
            }
            if (quantityLocked > quantityRequire) {
                // release from lock and increase inventory
                existLock.setQuantity(quantityRequire);
                // TODO: set expire Date base on order status
                // existLock.setExpiredDate();
                updateInventoryLock(existLock);
                changeInventory(productId, quantityLocked - quantityRequire, InventoryType.INCREASE);
                continue;
            }
            if (quantityLeft < (quantityRequire - quantityLocked)) {
                allLocked = false;
                oosProductIds.add(productId);
                continue;
            }
            // deduct inventory
            existLock.setQuantity(quantityRequire);
            // TODO: set expire Date base on order status
            // existLock.setExpiredDate();
            updateInventoryLock(existLock);
            changeInventory(productId, quantityRequire - quantityLocked, InventoryType.DEDUCT);
        }
        if (!allLocked) {
            // TODO throw check exception (InventoryException)
        }

    }

    /**
     *  1. remove non-exist commerce item inventory lock
     *  2. increase inventory quantity.
     *
     * @param existLocks
     * @param productIds
     */
    private void removeNonExistLock(final Map<Integer, InventoryLock> existLocks, final List<Integer> productIds) {
        if (null == existLocks || existLocks.isEmpty()) {
            return ;
        }

        for (Integer productId : existLocks.keySet()) {
            if (!productIds.contains(productId)) {
                InventoryLock nonExistLock = existLocks.get(productId);
                if (null == nonExistLock) {
                    continue;
                }
                int quantity = nonExistLock.getQuantity();
                deleteInventoryLock(nonExistLock);
                changeInventory(productId, quantity, InventoryType.INCREASE);

            }
        }
    }

    private void updateInventoryLock(InventoryLock existLock) {
        getInventoryLockMapper().updateInventoryLock(existLock);
    }

    private void createInventoryLock(InventoryLock newLock) {
        getInventoryLockMapper().createInventoryLock(newLock);
    }

    private void deleteInventoryLock(InventoryLock nonExistLock) {
        getInventoryLockMapper().deleteInventoryLock(nonExistLock);
    }

    private int queryInventoryQuantity(final int productId) {

        return getInventoryLockMapper().queryInventoryQuantity(productId);
    }



    private void changeInventory(Integer productId, int quantity, InventoryType action) {
        // increase inventory in DB
        int quantityLeft = queryInventoryQuantity(productId);
        if (InventoryType.DEDUCT == action) {
            quantityLeft -= quantity;
        }
        if (InventoryType.INCREASE == action) {
            quantityLeft += quantity;
        }
        Product product = new Product();
        product.setProductId(productId);
        product.setStock(quantityLeft);
        getInventoryLockMapper().updateInventoryQuantity(product);
        // TODO increase inventory in index
    }

    /**
     * Find already exist inventory lock in DB.
     * @param orderId
     * @return
     */
    private Map<Integer, InventoryLock> findExistLock(final int orderId) {
        List<InventoryLock> existLocks = getInventoryLockMapper().findInventoryLockByOrderId(orderId);
        if (null == existLocks || existLocks.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<Integer, InventoryLock> result = new HashMap<Integer, InventoryLock> ();
        for (InventoryLock existLock : existLocks) {
            result.put(existLock.getProductId().intValue(), existLock);
        }
        return result;
    }


    private void acquireRowLock(final List<Integer> productIds) {
        for (Integer productId : productIds) {
            getInventoryLockMapper().acquireRowLock(productId);
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
}
