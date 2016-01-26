package com.pgt.address.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import com.pgt.utils.WebServiceConstants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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


    @RequestMapping(value = "/ajaxAddress")
    @ResponseBody
    public ResponseEntity ajaxListAllAddress(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Map<String, Object> result = new HashMap<String, Object>();
        if (currentUser == null) {
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NEED_LOGIN)
            return new ResponseEntity(result, HttpStatus.OK);
        }
        List<AddressInfo> addressList = getAddressInfoService().queryAddressByUserId(currentUser.getId().intValue());
        if (CollectionUtils.isEmpty(addressList) ) {
            result.put(WebServiceConstants.NAME_ADDRESS_LIST, Collections.emptyList());
            return  new ResponseEntity(result, HttpStatus.OK);
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
        result.put(WebServiceConstants.NAME_ADDRESS_LIST, sortedAddressList);

        return new ResponseEntity(result, HttpStatus.OK);
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
