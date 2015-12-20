package com.pgt.inventory;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.utils.Transactionable;

import java.util.*;

/**
 * Created by samli on 2015/12/20.
 */
public class InventoryService extends Transactionable {


    public void lockInventory(Order order) {
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
            Map<Integer, InventoryLock> existLocks = findExistLock(order.getId(), productIds);
            updateExistLockExpireDate(existLocks, now);
            checkInventoryLock(order, existLocks);
        } catch (Exception e) {
            setAsRollback();
            // TODO: THROW WRAPPED EXCEPTION
        } finally {
            commit();
        }
    }

    private void checkInventoryLock(Order order, Map<Integer, InventoryLock> existLocks) {
        for (CommerceItem commerceItem : order.getCommerceItems()) {
            int productId = commerceItem.getReferenceId();
            int quantityLeft = queryInventoryQuantiry(productId);
            int quantityLocked = 0;
            if (null != existLocks && null != existLocks.get(Integer.valueOf(productId))) {
                quantityLocked = existLocks.get(Integer.valueOf(productId)).getQuantity();
            }
            int quantityRequire = commerceItem.getQuantity();
            if (quantityLocked == quantityRequire) {
                continue;
            }
            if (quantityLocked > quantityRequire) {
                // TODO Increace inventory;
                continue;
            }
            if (quantityLeft < (quantityRequire - quantityLocked)) {
                // TODO throw check exception (InventoryException)
            }
            // TODO deduct inventory
        }
    }

    private int queryInventoryQuantiry(int productId) {
        return 0;
    }


    /**
     * release expred lock and return valid lock;
     * @param existLocks
     * @param now
     * @return
     */
    private void updateExistLockExpireDate(Map<Integer, InventoryLock> existLocks, Date now) {
        if (null == existLocks || existLocks.isEmpty()) {
            return ;
        }

        for (InventoryLock inventoryLock : existLocks.values() ) {
            // TODO: Update inventoryLock expireDate depend on order status. (cart 15 min, start payment 35 min)

        }

    }

    private Map<Integer, InventoryLock> findExistLock(int orderId, List<Integer> productIds) {
        return null;
    }


    private void acquireRowLock(List<Integer> productIds) {

    }
}
