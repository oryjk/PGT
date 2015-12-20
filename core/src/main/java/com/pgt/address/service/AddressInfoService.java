package com.pgt.address.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.dao.AddressInfoMapper;

@Service
public class AddressInfoService {
	@Autowired
	AddressInfoMapper addressInfoMapper;

	public void addAddress(AddressInfo addressInfo) {
		addressInfoMapper.add(addressInfo);
	}

	public void deleteAddress(int id) {
		addressInfoMapper.delete(id);
	}

	public void updateAddress(AddressInfo addressInfo) {
		addressInfoMapper.update(addressInfo);
	}

	public AddressInfo findAddress(int id) {
		return addressInfoMapper.select(id);
	}

	public List<AddressInfo> queryAddressByUserId(int userId) {
		return addressInfoMapper.queryAddressByUserId(userId);
	}

	public List<AddressInfo> sortAddress(Integer defaultAddressId, List<AddressInfo> addressList) {
		List<AddressInfo> sortedAddressList = new ArrayList<AddressInfo>();
		sortedAddressList.add(null);
		for (AddressInfo address : addressList) {
			if (defaultAddressId.equals(address.getId())) {
				sortedAddressList.set(0, address);
			} else {
				sortedAddressList.add(address);
			}
		}
		return sortedAddressList;
	}
}
