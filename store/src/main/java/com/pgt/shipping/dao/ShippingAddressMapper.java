package com.pgt.shipping.dao;

import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.shipping.bean.ShippingAddress;

/**
 * @author ethanli
 */
@Component
public interface ShippingAddressMapper extends SqlMapper {

	void addAddress(ShippingAddress address);

	void delete(int id);

	void update(ShippingAddress address);

	ShippingAddress selectAddress(int id);

}
