package com.pgt.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.service.AddressInfoService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;

@Controller
@RequestMapping("/my-account/person-info")
public class UserAddressController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddressInfoController.class);
    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    private CityService cityService;
    @Autowired
    private URLConfiguration urlConfiguration;

    @Resource(name = "responseBuilderFactory")
    private ResponseBuilderFactory responseBuilderFactory;

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
        List<Province> provinceList = getCityService().getAllProvince();
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

    @RequestMapping(value = "/ajaxAddress", method = RequestMethod.GET)
    public ResponseEntity listAllAddress(HttpServletRequest request) {
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        rb.setData(data);
        User currentUser = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (currentUser == null) {
            LOGGER.debug("User should login firstly when accessing address book.");
            data.put("errorMsg", "current user is empty.");
            data.put("error", true);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        List<Province> provinceList = getCityService().getAllProvince();
        data.put("provinceList", provinceList);
        List<AddressInfo> addressList = getAddressInfoService().queryAddressByUserId(currentUser.getId().intValue());
        if (CollectionUtils.isEmpty(addressList) || addressList.size() == 1) {
            data.put("addressList", addressList);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
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
        data.put("addressList", sortedAddressList);

        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
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


    public ResponseBuilderFactory getResponseBuilderFactory() {
        return responseBuilderFactory;
    }

    public void setResponseBuilderFactory(ResponseBuilderFactory responseBuilderFactory) {
        this.responseBuilderFactory = responseBuilderFactory;
    }
}
