package com.pgt.address.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pgt.address.bean.Store;
import com.pgt.base.mapper.SqlMapper;

@Repository
public interface StoreMapper extends SqlMapper {
	List<Store> findStoreByProductIds(List<Integer> productIds);
}
