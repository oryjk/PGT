package com.pgt.address.controller;


import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by hd on 16-1-21.
 *
 * add address and update address
 */
@Controller
@RequestMapping("/my-account/person-info")
public class WebUserAddressController {
    private static final Logger LOGGER	= LoggerFactory.getLogger(WebUserAddressController.class);
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private CityService cityService;

    /**
     * add address method
     * @param modelAndView
     * @param session
     * @return
     */
    @RequestMapping(value = "/addAddress", method = RequestMethod.GET)
    public ModelAndView addAddressPage(ModelAndView modelAndView,HttpSession session){
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            LOGGER.debug("User should login firstly when accessing add address.");
            String redirectUrl = "redirect:" + urlConfiguration.getLoginPage() + "?redirect="
                    + urlConfiguration.getAddressBookPage();
            return new ModelAndView(redirectUrl);
        }
        List<Province> provinceList = getCityService().getAllProvince();
        modelAndView.addObject("provinceList", provinceList);

        modelAndView.setViewName("my-account/person-info/addAddress");
        LOGGER.debug("success and go to my-account/person-info/addAddress.jsp");
        return modelAndView;
    }

    /**
     * update address method
     * @param addressId
     * @param modelAndView
     * @param session
     * @return
     */
    @RequestMapping(value = "/updateAddress/{addressId}", method = RequestMethod.GET)
    public ModelAndView updateAddressPage(@PathVariable("addressId") String addressId,ModelAndView modelAndView,HttpSession session){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            LOGGER.debug(" error:User should login firstly when accessing add address.");
            String redirectUrl = "redirect:" + urlConfiguration.getLoginPage() + "?redirect="
                    + urlConfiguration.getAddressBookPage();
            return new ModelAndView(redirectUrl);
        }
        if (StringUtils.isBlank(addressId)){
            LOGGER.debug("error:addressId Can not empty.");
            modelAndView.setViewName("/my-account/person-info/address");
            return modelAndView;
        }
        int addressIdTemp =  Integer.parseInt(addressId);
        List<Province> provinceList = getCityService().getAllProvince();
        modelAndView.addObject("provinceList", provinceList);
        modelAndView.addObject("addressIdTemp", addressIdTemp);

        modelAndView.setViewName("my-account/person-info/updateAddress");
        LOGGER.debug("success and go to my-account/person-info/updateAddress.jsp");
        return modelAndView;
    }


    public CityService getCityService() {
        return cityService;
    }



}
