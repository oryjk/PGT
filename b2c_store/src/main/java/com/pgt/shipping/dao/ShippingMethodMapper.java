package com.pgt.shipping.dao;

import org.springframework.stereotype.Repository;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.shipping.bean.ShippingMethod;

/**
 * 
 * @author royic
 *
 */
@Repository
public interface ShippingMethodMapper extends SqlMapper {

	void addShippingMethod(ShippingMethod shippingMethod);

	void delete(int id);

	void update(ShippingMethod shippingMethod);

	ShippingMethod selectShippingMethod(int id);
}
