package com.pgt.address.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pgt.address.bean.AddressInfo;
import com.pgt.base.mapper.SqlMapper;

@Repository
public interface AddressInfoMapper extends SqlMapper{
	public void add(AddressInfo addressInfo);

	public AddressInfo select(int id);

	public void update(AddressInfo addressInfo);

	public void delete(int id);

	public List<AddressInfo> queryAddressByUserId(int userId);
}
