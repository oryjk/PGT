package com.pgt.shipping.dao;

import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.Order;
import com.pgt.shipping.bean.ShippingVO;

@Component
public interface ShippingVOMapper extends SqlMapper {

	public void addShipping(ShippingVO shippingVO);

	public void update(ShippingVO shippingVO);

	public void delete(int shippingId);

	public void saveShippingToOrder(Order order);

	public void deleteLinkAddressId(int addressId);

	public void deleteLinkShippingMethodId(int shippingMethodId);

	public ShippingVO findShipping(int shippingId);

	public ShippingVO findShippingByOrderId(String orderId);
}
