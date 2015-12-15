package com.pgt.city.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.city.bean.Area;
import com.pgt.city.bean.City;
import com.pgt.city.bean.Province;

@Repository
public interface CityMapper extends SqlMapper {

	List<Province> getAllProvince();

	List<City> getCityByProvinceId(Integer provinceId);

	List<Area> getAreaByCityId(Integer cityId);
}
