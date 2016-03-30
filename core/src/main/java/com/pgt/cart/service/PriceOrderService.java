package com.pgt.cart.service;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.ProductPriceVector;
import com.pgt.cart.dao.ShoppingCartDao;
import com.pgt.cart.exception.PriceOrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created by Yove on 10/29/2015.
 */
@Service(value = "priceOrderService")
public class PriceOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PriceOrderService.class);

	@Resource(name = "shoppingCartDao")
	private ShoppingCartDao mShoppingCartDao;

	public static double roundPrice(double pPrice) {
		return new BigDecimal(pPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public void priceOrder(Order pOrder) throws PriceOrderException {
		pOrder.resetOrderPrice();
		priceCommerceItemsOfOrder(pOrder);
		priceShipping(pOrder);
		double orderTotal = roundPrice(pOrder.getSubtotal());
		pOrder.setTotal(orderTotal);
		LOGGER.debug("Priced order and get order total: {}", pOrder.getTotal());
	}

	public void priceCommerceItemsOfOrder(Order pOrder) throws PriceOrderException {
		LOGGER.debug("Price commerce items for order: {}", pOrder.getId());
		if (pOrder.emptyOrder()) {
			LOGGER.debug("None commerce items for order: {}, skip price commerce items.", pOrder.getId());
			return;
		}
		double orderSubtotal = 0d;
		Set<Integer> productIds = pOrder.populateProductIdSet();
		Map<Integer, ProductPriceVector> priceVectorMap = getShoppingCartDao().loadProductPrice(productIds.toArray(new Integer[0]));
		for (CommerceItem ci : pOrder.getCommerceItems()) {
			int productId = ci.getReferenceId();
			if (!priceVectorMap.containsKey(productId)) {
				String msg = String.format("No price found for commerce item: %d with product: %d", ci.getId(), productId);
				throw new PriceOrderException(msg);
			}
			ProductPriceVector priceVector = priceVectorMap.get(productId);
			if (!priceVector.isListPriceValid() && !priceVector.isSalePriceValid()) {
				String msg = String.format("No price found for commerce item: %d with product: %d", ci.getId(), productId);
				throw new PriceOrderException(msg);
			}
			if (priceVector.isListPriceValid()) {
				ci.setListPrice(priceVector.getListPrice());
				LOGGER.debug("Get list price: {} for commerce item: {} with product: {}", priceVector.getListPrice(), ci.getId(), ci.getReferenceId());
			} else {
				ci.setListPrice(0d);
			}
			if (priceVector.isSalePriceValid()) {
				ci.setSalePrice(priceVector.getSalePrice());
				LOGGER.debug("Get sale price: {} for commerce item: {} with product: {}", priceVector.getSalePrice(), ci.getId(), ci.getReferenceId());
			} else {
				ci.setSalePrice(0d);
			}
			// unit price * quantity
			ci.setAmount(roundPrice(priceVector.getFinalPrice() * ci.getQuantity()));
			orderSubtotal += ci.getAmount();
			LOGGER.debug("Get price: {} * quantity: {} = amount: {} for commerce item: {} with product: {}", priceVector.getFinalPrice(), ci.getQuantity()
					, ci.getAmount(), ci.getId(), ci.getReferenceId());
		}
		orderSubtotal = roundPrice(orderSubtotal);
		LOGGER.debug("Get subtotal {} after finished price commerce items for order: {}", orderSubtotal, pOrder.getId());
		pOrder.setSubtotal(orderSubtotal);
	}

	public void priceShipping(Order pOrder) {
	}


	public ShoppingCartDao getShoppingCartDao() {
		return mShoppingCartDao;
	}

	public void setShoppingCartDao(final ShoppingCartDao pShoppingCartDao) {
		mShoppingCartDao = pShoppingCartDao;
	}
}
