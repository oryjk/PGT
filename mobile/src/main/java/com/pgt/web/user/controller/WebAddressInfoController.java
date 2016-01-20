package com.pgt.web.user.controller;

import com.google.gson.Gson;
import com.pgt.address.bean.AddressInfo;
import com.pgt.address.service.AddressInfoService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
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
import org.springframework.web.servlet.ModelAndView;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web")
public class WebAddressInfoController {
	private static final Logger	LOGGER	= LoggerFactory.getLogger(WebAddressInfoController.class);
	@Autowired
	private URLConfiguration	urlConfiguration;
	@Autowired
	private AddressInfoService	addressInfoService;
	@Autowired
	private UserService			userService;
    @Autowired
    private CityService cityService;


    @RequestMapping(value = "/addAddress", method = RequestMethod.GET)
    public ModelAndView addAddressPage(ModelAndView modelAndView,HttpSession session){
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            LOGGER.debug("User should login firstly when accessing address book.");
            String redirectUrl = "redirect:" + "/web/wlogin" + "?redirect="
                    + "/web/addAddress";
            return new ModelAndView(redirectUrl);

        }
        List<Province> provinceList = getCityService().getAllProvince();
        modelAndView.addObject("provinceList", provinceList);

        modelAndView.setViewName("my-account/person-info/newaddress");
        return modelAndView;
    }

	@RequestMapping(value = "/addAddress", method = RequestMethod.POST)
	public/* Map<String, Object>*/ ModelAndView addAddress(ModelAndView modelAndView,HttpSession session, @Validated AddressInfo addressInfo,
			BindingResult bindingResult) {
		//Map<String, Object> map = new HashMap<String, Object>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
            LOGGER.debug("User should login firstly when accessing address book.");
            String redirectUrl = "redirect:" + "web/wlogin" + "?redirect="
                    + "/web/addAddress";
            return new ModelAndView(redirectUrl);

		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
            LOGGER.debug("add address information error");
            modelAndView.setViewName("redirect:/web/addAddress");
            return modelAndView;
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
        modelAndView.setViewName("redirect:/web/waddress");
        return modelAndView;
	}



	@RequestMapping(value = "/deleteAddress/{addressId}", method = RequestMethod.GET)
	public ModelAndView deleteAddress(@PathVariable("addressId") String addressId, HttpSession session,ModelAndView modelAndView) {

		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {

            modelAndView.setViewName("redirect:/web/wlogin");
			return modelAndView;
		}
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
            modelAndView.setViewName("redirect:/web/waddress");
            return modelAndView;
		}
        int addressIdTemp = Integer.parseInt(addressId);
        if (addressIdTemp==user.getDefaultAddressId()) {
            LOGGER.error("The address id is empty.");

            modelAndView.setViewName("redirect:/web/waddress");
            return modelAndView;
        }
		getAddressInfoService().deleteAddress(Integer.parseInt(addressId));
		Integer defaultAddressId = user.getDefaultAddressId();
		if(defaultAddressId != null && addressId.equals(defaultAddressId.toString())){
			user.setDefaultAddressId(null);
			userService.updateUser(user);
		}

        modelAndView.setViewName("redirect:/web/waddress");
		return modelAndView;
	}

	@RequestMapping(value = "/updateAddress/{addressId}", method = RequestMethod.POST)
	public ModelAndView updateAddress(@PathVariable("addressId") String addressId, HttpSession session,
			@Validated AddressInfo addressInfo, BindingResult bindingResult,ModelAndView modelAndView) {

		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
            modelAndView.setViewName("redirectUrl:/web/wlogin");
			return modelAndView;
		}
		if (StringUtils.isBlank(addressId)) {
            modelAndView.setViewName("redirect:/web/waddress");
			return modelAndView;
		}
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}

			modelAndView.addObject("errors", errors);
			return modelAndView;
		}
		addressInfo.setId(Integer.parseInt(addressId));
		addressInfo.setStatus(0);
		addressInfo.setUpdateDate(new Date());
		getAddressInfoService().updateAddress(addressInfo);
		if (addressInfo.isPrimary()) {
			user.setDefaultAddressId(addressInfo.getId());
			userService.updateUser(user);
		}
        modelAndView.setViewName("redirect:/web/waddress");
		return modelAndView;
	}

	@RequestMapping(value = "/findAddress/{addressId}", method = RequestMethod.GET)
	public ModelAndView findAddress(@PathVariable("addressId") String addressId,ModelAndView modelAndView) {
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
			modelAndView.setViewName("redirect:/web/waddress");
			return modelAndView;
		}
		AddressInfo addressInfo = getAddressInfoService().findAddress(Integer.parseInt(addressId));

        List<Province> provinceList = getCityService().getAllProvince();
        modelAndView.addObject("provinceList", provinceList);
        modelAndView.addObject("addressId",addressId);
        modelAndView.addObject("addressInfo",addressInfo);
		modelAndView.setViewName("my-account/person-info/reviseaddress");
		return modelAndView;
	}

	@RequestMapping(value = "/setDefaultAddress/{addressId}", method = RequestMethod.GET)
	public ModelAndView setDefaultAdress(@PathVariable("addressId") String addressId, HttpSession session,ModelAndView modelAndView) {
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
            modelAndView.setViewName("redirectUrl:/web/wlogin");
            return modelAndView;
		}
		if (StringUtils.isEmpty(addressId)) {
			LOGGER.error("The address id is empty.");
            modelAndView.setViewName("redirect:/web/waddress");
            return modelAndView;
		}
		user.setDefaultAddressId(Integer.parseInt(addressId));
		getUserService().updateUser(user);
        modelAndView.setViewName("redirect:/web/waddress");
        return modelAndView;
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

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

}
