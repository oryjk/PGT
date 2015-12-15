package com.pgt.city.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.city.bean.Area;
import com.pgt.city.bean.City;
import com.pgt.city.bean.Province;
import com.pgt.city.dao.CityMapper;

@Service
public class CityService {
	@Autowired
	private CityMapper cityMapper;

	public List<Province> getAllProvince() {
		return cityMapper.getAllProvince();
	}

	public List<City> getCityByProvinceId(Integer procinceId) {
		return cityMapper.getCityByProvinceId(procinceId);
	}

	public List<Area> getAreaByCityId(Integer cityId) {
		return cityMapper.getAreaByCityId(cityId);
	}
}