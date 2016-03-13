package com.pgt.address.controller;

import com.google.gson.Gson;
import com.pgt.address.bean.AddressInfo;
import com.pgt.address.service.AddressInfoService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import com.pgt.utils.WebServiceConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/my-account/person-info")
public class AddressInfoController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressInfoController.class);
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;

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
        if (defaultAddressId != null && addressId.equals(defaultAddressId.toString())) {
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
        if (addressInfo.isPrimary()) {
            user.setDefaultAddressId(addressInfo.getId());
            userService.updateUser(user);
        }
        map.put("success", "true");
        map.put("action", "updateAddress");
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


    @RequestMapping(value = "/address")
    public ModelAndView listAllAddress(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (currentUser == null) {
            LOGGER.debug("User should login firstly when accessing address book.");
            String redirectUrl = "redirect:" + urlConfiguration.getLoginPage() + "?redirect="
                    + urlConfiguration.getAddressBookPage();
            return new ModelAndView(redirectUrl);
        }
        ModelAndView mav = new ModelAndView(urlConfiguration.getAddressBookPage());
        List<Province> provinceList = cityService.getAllProvince();
        mav.addObject("provinceList", provinceList);
        List<AddressInfo> addressList = getAddressInfoService().queryAddressByUserId(currentUser.getId().intValue());
        if (CollectionUtils.isEmpty(addressList) || addressList.size() == 1) {
            mav.addObject("addressList", addressList);
            return mav;
        }
        List<AddressInfo> sortedAddressList = new ArrayList<AddressInfo>();
        sortedAddressList.add(null);
        Integer defaultAddressId = currentUser.getDefaultAddressId();
        for (AddressInfo address : addressList) {
            if (ObjectUtils.isEmpty(defaultAddressId)) {
                defaultAddressId = address.getId();
            }
            if (defaultAddressId.equals(address.getId())) {
                sortedAddressList.set(0, address);
            } else {
                sortedAddressList.add(address);
            }
        }
        mav.addObject("addressList", sortedAddressList);
        return mav;
    }

    @RequestMapping(value = "/ajaxAddress")
    @ResponseBody
    public ResponseEntity ajaxListAllAddress(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Map<String, Object> result = new HashMap<String, Object>();
        if (currentUser == null) {
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NEED_LOGIN);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        List<AddressInfo> addressList = getAddressInfoService().queryAddressByUserId(currentUser.getId().intValue());
        if (CollectionUtils.isEmpty(addressList)) {
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_SUCCESS);
            result.put(WebServiceConstants.NAME_ADDRESS_LIST, Collections.emptyList());
            return new ResponseEntity(result, HttpStatus.OK);
        }
        List<AddressInfo> sortedAddressList = new ArrayList<AddressInfo>();
        sortedAddressList.add(null);
        Integer defaultAddressId = currentUser.getDefaultAddressId();
        for (AddressInfo address : addressList) {
            if (ObjectUtils.isEmpty(defaultAddressId)) {
                defaultAddressId = address.getId();
            }
            if (defaultAddressId.equals(address.getId())) {
                sortedAddressList.set(0, address);
            } else {
                sortedAddressList.add(address);
            }
        }
        result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_SUCCESS);
        result.put(WebServiceConstants.NAME_ADDRESS_LIST, sortedAddressList);

        return new ResponseEntity(result, HttpStatus.OK);
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
