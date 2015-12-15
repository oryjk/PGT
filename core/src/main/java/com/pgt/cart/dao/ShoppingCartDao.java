package com.pgt.cart.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.ProductPriceVector;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Yove on 10/27/2015.
 */
@Repository(value = "shoppingCartDao")
public interface ShoppingCartDao extends SqlMapper {

	Order loadInitialOrderByUserId(@Param("userId") final int pUserId);

	int createOrder(Order pOrder);

	int updateOrder(Order pOrder);

	int createCommerceItem(CommerceItem pTransientItem);

	int updateCommerceItems(List<CommerceItem> pPersistentItems);

	List<CommerceItem> loadCommerceItemsFromOrderWithRealTimePrice(@Param("orderId") int pOrderId);

	@MapKey("productId")
	Map<Integer, ProductPriceVector> loadProductPrice(Integer[] pProductIds);

	int deleteCommerceItem(@Param("commerceItemId") int pCommerceItemId);

	void deleteAllCommerceItems(@Param("orderId") int pOrderId);

	int deleteCommerceItems(List<Integer> pCommerceItemIds);
}
