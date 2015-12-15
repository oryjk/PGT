package com.pgt.city.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.pgt.city.bean.Area;
import com.pgt.city.bean.City;
import com.pgt.city.service.CityService;

@Controller
public class CityController {
	@Autowired
	private CityService cityService;

	@RequestMapping(value = "/getCityByProvinceId/{provinceId}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getCityByProvinceId(@PathVariable("provinceId") String provinceId) {
		if (StringUtils.isBlank(provinceId)) {
			return "error";
		}
		List<City> citys = getCityService().getCityByProvinceId(Integer.parseInt(provinceId));
		Gson gson = new Gson();
		return gson.toJson(citys);
	}

	@RequestMapping(value = "/getAreaByCityId/{cityId}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getAreaByCityId(@PathVariable("cityId") String cityId) {
		if (StringUtils.isBlank(cityId)) {
			return "error";
		}
		List<Area> areas = getCityService().getAreaByCityId(Integer.parseInt(cityId));
		Gson gson = new Gson();
		return gson.toJson(areas);
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

}
