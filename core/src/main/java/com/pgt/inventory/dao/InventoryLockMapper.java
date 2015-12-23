package com.pgt.inventory.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.inventory.bean.InventoryLock;
import com.pgt.product.bean.Product;

import java.util.List;

/**
 * Created by samli on 2015/12/22.
 */
public interface InventoryLockMapper extends SqlMapper {

    void createInventoryLock(final InventoryLock newLock);

    void updateInventoryLock(final InventoryLock existLock);

    void deleteInventoryLock(final InventoryLock nonExistLock);

    List<InventoryLock> findInventoryLockByOrderId(final int orderId);

    int queryInventoryQuantity(final int productId);

    void updateInventoryQuantity(Product product);

    int acquireRowLock(final int productId);

    List<Integer> findExpiredOrders();

    List<InventoryLock> findExpiredInventoryLock(Integer orderId);
}
