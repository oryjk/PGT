package com.pgt.mobile.address.controller;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.controller.AddressInfoController;
import com.pgt.address.service.AddressInfoService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/my-account/person-info")
public class UserAddressMobileController extends BaseMobileController{

	private static final Logger	LOGGER	= LoggerFactory.getLogger(AddressInfoController.class);
	@Autowired
	private AddressInfoService	addressInfoService;
	@Autowired
	private CityService			cityService;
	@Autowired
	private SimpleCacheManager cacheManager;

	@RequestMapping(value = "/address")
	public Map<String,Object> listAllAddress(String phoneId) {

		Map<String, Object> responseMap = new HashMap<String, Object>();
		if(StringUtils.isEmpty(phoneId)){
			return responseMobileFail(responseMap, "PhoneId.empty");
		}
		Cache cache = cacheManager.getCache(MobileConstans.PHONE_USER);
		Cache.ValueWrapper valueWrapper = cache.get(phoneId);
		if (ObjectUtils.isEmpty(valueWrapper)) {
			return responseMobileFail(responseMap, "User.empty");
		}
		User user= (User) valueWrapper.get();

		List<Province> provinceList = getCityService().getAllProvince();
        responseMap.put("provinceList",provinceList);

		List<AddressInfo> addressList = getAddressInfoService().queryAddressByUserId(user.getId().intValue());
		if (CollectionUtils.isEmpty(addressList) || addressList.size() == 1) {
			responseMap.put("addressList",addressList);
			return responseMap;
		}
		List<AddressInfo> sortedAddressList = new ArrayList<AddressInfo>();
		sortedAddressList.add(null);
		Integer defaultAddressId = user.getDefaultAddressId();
		for (AddressInfo address : addressList) {
			if (defaultAddressId.equals(address.getId())) {
				sortedAddressList.set(0, address);
			} else {
				sortedAddressList.add(address);
			}
		}
		responseMap.put("addressList",sortedAddressList);
		return responseMap;
	}

	public AddressInfoService getAddressInfoService() {
		return addressInfoService;
	}

	public void setAddressInfoService(AddressInfoService addressInfoService) {
		this.addressInfoService = addressInfoService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

}
