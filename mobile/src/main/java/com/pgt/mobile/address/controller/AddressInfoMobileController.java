package com.pgt.mobile.address.controller;
import com.google.gson.Gson;
import com.pgt.address.bean.AddressInfo;
import com.pgt.address.service.AddressInfoService;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/my-account/person-info")
public class AddressInfoMobileController extends BaseMobileController{
	private static final Logger	LOGGER	= LoggerFactory.getLogger(AddressInfoMobileController.class);

	@Autowired
	private AddressInfoService	addressInfoService;
	@Autowired
	private UserService			userService;
	@Autowired
	private SimpleCacheManager cacheManager;

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public Map<String, Object> addAddress(String phoneId, @Validated AddressInfo addressInfo, BindingResult bindingResult) {

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
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, Object> errors = new HashMap<String, Object>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			responseMap.put("errors",errors);
			return responseMobileFail(responseMap, "false");
		}
		addressInfo.setUserId(user.getId().intValue());
		addressInfo.setStatus(0);
		addressInfo.setCreationDate(new Date());
		addressInfo.setUpdateDate(new Date());
		getAddressInfoService().addAddress(addressInfo);
		Integer defaultAddressId = user.getDefaultAddressId();
		if (defaultAddressId == null || addressInfo.isPrimary()) {
			user.setDefaultAddressId(addressInfo.getId());
			userService.updateUser(user);
		}
		responseMap.put("addedAddressId",addressInfo.getId());
		responseMap.put(MobileConstans.MOBILE_STATUS,MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
	}

	@RequestMapping(value = "/deleteAddress", method = RequestMethod.POST)
	public Map<String, Object> deleteAddress(String phoneId,String addressId) {

		Map<String, Object> responseMap  = new HashMap<String,Object>();
		if(StringUtils.isEmpty(phoneId)){
			return responseMobileFail(responseMap, "PhoneId.empty");
		}
		Cache cache = cacheManager.getCache(MobileConstans.PHONE_USER);
		Cache.ValueWrapper valueWrapper = cache.get(phoneId);
		if (ObjectUtils.isEmpty(valueWrapper)) {
			return responseMobileFail(responseMap, "User.empty");
		}
		User user= (User) valueWrapper.get();

		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			return responseMobileFail(responseMap, "address.empty");
		}

		getAddressInfoService().deleteAddress(Integer.parseInt(addressId));
		Integer defaultAddressId = user.getDefaultAddressId();
		if(addressId.equals(defaultAddressId)){
			user.setDefaultAddressId(null);
			userService.updateUser(user);
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		return responseMap;
	}

	@RequestMapping(value = "/updateAddress", method = RequestMethod.POST)
	public Map<String, Object> updateAddress(@PathVariable("addressId") String addressId,String phoneId, @Validated AddressInfo addressInfo, BindingResult bindingResult) {
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

		if (StringUtils.isBlank(addressId)) {
			return responseMobileFail(responseMap, "addressId.empty");
		}

		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			return responseMobileFail(responseMap, "addressId.empty");
		}
		addressInfo.setId(Integer.parseInt(addressId));
		addressInfo.setStatus(0);
		addressInfo.setUpdateDate(new Date());
		getAddressInfoService().updateAddress(addressInfo);

		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		return responseMap;
	}

	@RequestMapping(value = "/findAddress", method = RequestMethod.POST)
	public Map<String, Object> findAddress (String addressId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			return responseMobileFail(responseMap, "addressId.empty");
		}
		AddressInfo addressInfo = getAddressInfoService().findAddress(Integer.parseInt(addressId));
		Gson gson = new Gson();

		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		responseMap.put("data",gson.toJson(addressInfo));
		return responseMap;
	}

	@RequestMapping(value = "/setDefaultAddress", method = RequestMethod.POST)
	public Map<String,Object> setDefaultAdress(String addressId,String phoneId) {
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

		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			return responseMobileFail(responseMap, "addressId.empty");
		}
		user.setDefaultAddressId(Integer.parseInt(addressId));
		getUserService().updateUser(user);
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		return responseMap;
	}

	public AddressInfoService getAddressInfoService() {
		return addressInfoService;
	}

	public void setAddressInfoService(AddressInfoService addressInfoService) {
		this.addressInfoService = addressInfoService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
