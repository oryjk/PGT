package com.pgt.web.user.controller;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.controller.AddressInfoController;
import com.pgt.address.service.AddressInfoService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.enterprise.inject.Model;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hd on 16-1-19.
 */
@Controller
@RequestMapping("/web")
public class WebUserAddressController {
    private static final Logger LOGGER	= LoggerFactory.getLogger(AddressInfoController.class);
    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    private CityService cityService;
    @Autowired
    private URLConfiguration urlConfiguration;


    @RequestMapping(value = "/waddress")
    public ModelAndView webListAllAddress(HttpSession session) {
        User currentUser = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (currentUser == null) {
            LOGGER.debug("User should login firstly when accessing address book.");
            String redirectUrl = "redirect:" + "/web/wlogin" + "?redirect="
                    + "/web/waddress";
            return new ModelAndView(redirectUrl);
        }
        ModelAndView mav = new ModelAndView("my-account/person-info/address");
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
            if (defaultAddressId.equals(address.getId())) {
                sortedAddressList.set(0, address);
            } else {
                sortedAddressList.add(address);
            }
        }
        mav.addObject("addressList", sortedAddressList);
        return mav;
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
