package com.pgt.address.controller;

import com.google.gson.Gson;
import com.pgt.address.bean.AddressInfo;
import com.pgt.address.service.AddressInfoService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/my-account/person-info")
public class AddressInfoController {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(AddressInfoController.class);
	@Autowired
	private URLConfiguration	urlConfiguration;
	@Autowired
	private AddressInfoService	addressInfoService;
	@Autowired
	private UserService			userService;

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAddress(HttpSession session, @Validated AddressInfo addressInfo,
			BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			map.put("success", "false");
			map.put("redirectUrl", urlConfiguration.getLoginPage());
			return map;
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			map.put("success", "false");
			map.put("errors", errors);
			return map;
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
		map.put("addedAddressId", addressInfo.getId());
		map.put("success", "true");
		return map;
	}

	@RequestMapping(value = "/deleteAddress/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> deleteAddress(@PathVariable("addressId") String addressId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			map.put("success", "false");
			map.put("redirectUrl", urlConfiguration.getLoginPage());
			return map;
		}
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			map.put("success", "false");
			return map;
		}
		getAddressInfoService().deleteAddress(Integer.parseInt(addressId));
		Integer defaultAddressId = user.getDefaultAddressId();
		if(addressId.equals(defaultAddressId)){
			user.setDefaultAddressId(null);
			userService.updateUser(user);
		}
		map.put("success", "true");
		return map;
	}

	@RequestMapping(value = "/updateAddress/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAddress(@PathVariable("addressId") String addressId, HttpSession session,
			@Validated AddressInfo addressInfo, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			map.put("success", "false");
			map.put("redirectUrl", urlConfiguration.getLoginPage());
			return map;
		}
		if (StringUtils.isBlank(addressId)) {
			map.put("success", "false");
			return map;
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			map.put("success", "false");
			map.put("errors", errors);
			return map;
		}
		addressInfo.setId(Integer.parseInt(addressId));
		addressInfo.setStatus(0);
		addressInfo.setUpdateDate(new Date());
		getAddressInfoService().updateAddress(addressInfo);
		map.put("success", "true");
		return map;
	}

	@RequestMapping(value = "/findAddress/{addressId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> findAddress(@PathVariable("addressId") String addressId) {
		Map<String, String> map = new HashMap<String, String>();
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			map.put("success", "false");
			return map;
		}
		AddressInfo addressInfo = getAddressInfoService().findAddress(Integer.parseInt(addressId));
		Gson gson = new Gson();
		map.put("success", "true");
		map.put("data", gson.toJson(addressInfo));
		return map;
	}

	@RequestMapping(value = "/setDefaultAddress/{addressId}", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> setDefaultAdress(@PathVariable("addressId") String addressId, HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			map.put("success", "false");
			map.put("redirectUrl", urlConfiguration.getLoginPage());
			return map;
		}
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			map.put("success", "false");
			return map;
		}
		user.setDefaultAddressId(Integer.parseInt(addressId));
		getUserService().updateUser(user);
		map.put("success", "true");
		return map;
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
