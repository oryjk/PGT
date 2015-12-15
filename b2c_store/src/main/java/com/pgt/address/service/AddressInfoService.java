package com.pgt.address.service;

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

}
