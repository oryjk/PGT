package com.pgt.shipping.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.bean.Store;
import com.pgt.address.dao.StoreMapper;
import com.pgt.address.service.AddressInfoService;
import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.constant.Constants;
import com.pgt.shipping.bean.ShippingAddress;
import com.pgt.shipping.bean.ShippingMethod;
import com.pgt.shipping.bean.ShippingVO;
import com.pgt.shipping.dao.ShippingAddressMapper;
import com.pgt.shipping.dao.ShippingMethodMapper;
import com.pgt.shipping.dao.ShippingVOMapper;
import org.springframework.util.ObjectUtils;

@Service
public class ShippingService {
    @Autowired
    ShippingVOMapper shippingVOMapper;
    @Autowired
    ShippingAddressMapper addressMapper;
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    ShippingMethodMapper shippingMethodMapper;

    public boolean addAddress(ShippingAddress address, Order order) {
        if (order == null) {
            return false;
        }
        int orderId = order.getId();
        addressMapper.addAddress(address);
        ShippingVO shipping = order.getShippingVO();
        if (shipping == null) {
            shipping = new ShippingVO();
            shipping.setShippingAddress(address);
            shipping.setShippingType(Constants.DELIVERY);
            shipping.setOrderId(String.valueOf(orderId));
            shipping.setCreationDate(new Date());
            shipping.setUpdateDate(new Date());
            shippingVOMapper.addShipping(shipping);
            order.setShippingVO(shipping);
            saveShippingToOrder(order);
        } else {
            shipping.setShippingMethod(null);
            shipping.setShippingAddress(address);
            shipping.setShippingType(Constants.DELIVERY);
            shipping.setUpdateDate(new Date());
            shippingVOMapper.update(shipping);
        }
        return true;

    }

    public boolean addAddress(Integer addressInfoId, Order order) {
        if (addressInfoId == null || order == null) {
            return false;
        }
        AddressInfo addressInfo = getAddressInfoService().findAddress(addressInfoId);
        ShippingAddress address = copyAddress(addressInfo);
        if (addAddress(address, order)) {
            return true;
        }
        return false;
    }

    public void saveShippingToOrder(Order order) {
        shippingVOMapper.saveShippingToOrder(order);
    }

    public void deleteAddress(int id) {
        addressMapper.delete(id);
        shippingVOMapper.deleteLinkAddressId(id);
    }

    public void updateAddress(ShippingAddress address) {
        addressMapper.update(address);
    }

    public ShippingAddress findAddress(int id) {
        return addressMapper.selectAddress(id);
    }

    public ShippingVO findShipping(int shippingId) {
        return shippingVOMapper.findShipping(shippingId);
    }

    public ShippingVO findShippingByOrderId(String orderId) {
        return shippingVOMapper.findShippingByOrderId(orderId);
    }

    public boolean addShippingMethod(ShippingMethod method, Order order) {
        if (order == null) {
            return false;
        }
        int orderId = order.getId();
        ShippingVO shipping = order.getShippingVO();
        shippingMethodMapper.addShippingMethod(method);
        if (shipping == null) {
            shipping = new ShippingVO();
            shipping.setShippingMethod(method);
            shipping.setOrderId(String.valueOf(orderId));
            shipping.setShippingType(Constants.PICKUP);
            shipping.setCreationDate(new Date());
            shipping.setUpdateDate(new Date());
            shippingVOMapper.addShipping(shipping);
            order.setShippingVO(shipping);
            saveShippingToOrder(order);
            return true;
        } else {
            shipping.setShippingAddress(null);
            shipping.setShippingMethod(method);
            shipping.setShippingType(Constants.PICKUP);
            shipping.setUpdateDate(new Date());
            shippingVOMapper.update(shipping);
        }
        return true;
    }

    public void deleteShippingMethod(int id) {
        shippingMethodMapper.delete(id);
        shippingVOMapper.deleteLinkShippingMethodId(id);
    }

    public void updateShippingMethod(ShippingMethod shippingMethod) {
        shippingMethodMapper.update(shippingMethod);
    }

    public ShippingMethod findShippingMethod(int id) {
        return shippingMethodMapper.selectShippingMethod(id);
    }

    public ShippingAddress copyAddress(AddressInfo addressInfo) {
        ShippingAddress dest = new ShippingAddress();
        if (!ObjectUtils.isEmpty(addressInfo)) {
            dest.setCity(addressInfo.getCity());
            dest.setDistrict(addressInfo.getDistrict());
            dest.setEmail(addressInfo.getEmail());
            dest.setName(addressInfo.getName());
            dest.setPhone(addressInfo.getPhone());
            dest.setTelephone(addressInfo.getTelephone());
            dest.setProvince(addressInfo.getProvince());
            dest.setStatus(0);
            dest.setAddress(addressInfo.getAddress());
            dest.setAddressInfoId(addressInfo.getId());
        }
        return dest;
    }

    public List<Integer> getProductIdsFromOrder(Order order) {
        if (order == null) {
            return null;
        }
        List<CommerceItem> ciList = order.getCommerceItems();
        if (ciList == null || ciList.isEmpty()) {
            return null;
        }
        List<Integer> productIds = new ArrayList<Integer>();
        for (CommerceItem ci : ciList) {
            productIds.add(ci.getReferenceId());
        }
        return productIds;

    }

    public List<Store> findStoreByProductIds(List<Integer> productIds) {
        return getStoreMapper().findStoreByProductIds(productIds);
    }

    public ShippingVOMapper getShippingVOMapper() {
        return shippingVOMapper;
    }

    public void setShippingVOMapper(ShippingVOMapper shippingVOMapper) {
        this.shippingVOMapper = shippingVOMapper;
    }

    public ShippingAddressMapper getAddressMapper() {
        return addressMapper;
    }

    public void setAddressMapper(ShippingAddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public StoreMapper getStoreMapper() {
        return storeMapper;
    }

    public void setStoreMapper(StoreMapper storeMapper) {
        this.storeMapper = storeMapper;
    }

    public ShippingMethodMapper getShippingMethodMapper() {
        return shippingMethodMapper;
    }

    public void setShippingMethodMapper(ShippingMethodMapper shippingMethodMapper) {
        this.shippingMethodMapper = shippingMethodMapper;
    }

    public AddressInfoService getAddressInfoService() {
        return addressInfoService;
    }

    public void setAddressInfoService(AddressInfoService addressInfoService) {
        this.addressInfoService = addressInfoService;
    }

}
