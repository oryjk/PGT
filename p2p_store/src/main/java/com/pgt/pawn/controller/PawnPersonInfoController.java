package com.pgt.pawn.controller;

import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.service.PawnPersonInfoService;
import com.pgt.pawn.validation.group.PawnGroup;
import com.pgt.sms.service.SmsService;
import com.pgt.utils.ErrorMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaodong on 16-2-17
 */
@RestController
@RequestMapping("/pawnPersonInfo")
public class PawnPersonInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger( PawnPersonInfoController.class);

    @Autowired
    private PawnPersonInfoService pawnPersonInfoService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private CityService cityService;

    @Autowired
    private URLConfiguration urlConfiguration;

    @Autowired
    private SmsService smsService;

    @RequestMapping(value = "/createPawnPersonInfo",method = RequestMethod.GET)
    public ModelAndView createPawnPersonInfo(ModelAndView modelAndView,@RequestParam String url,@RequestParam String type){
        modelAndView.addObject("url",url);
        modelAndView.addObject("type",type);
        LOGGER.debug("The method create PawnPersonInfo UI");
        Map pawnTypes=configuration.getPawnType();
        if(!CollectionUtils.isEmpty(pawnTypes)){
            LOGGER.debug(" add pawnTypes to model");
            modelAndView.addObject("pawnTypes",pawnTypes);
        }
        List<Province> provinceList =cityService.getAllProvince();
        modelAndView.addObject("provinceList", provinceList);
        modelAndView.setViewName(urlConfiguration.getPawnPersonInfoPage());
        return modelAndView;
    }


    @RequestMapping(value = "/createPawnPersonInfo",method = RequestMethod.POST)
    public ModelAndView createPawnPersonInfo(ModelAndView modelAndView, @Validated(value = PawnGroup.class) PawnPersonInfo pawnPersonInfo, BindingResult bindingResult, HttpServletRequest request){
        LOGGER.debug("The method to create pawnPersonInfo");

        if (!configuration.isSmsMock()) {
            if (!StringUtils.isEmpty(pawnPersonInfo.getSmsCode())) {
                String smsCode = pawnPersonInfo.getSmsCode();
                String phoneCode = (String) request.getSession().getAttribute(Constants.ONLINEPAWN_SESSION_PHONE_CODE);
                if (!smsCode.equals(phoneCode)) {
                    bindingResult.addError(
                            new FieldError("pawnPersonInfo", "smsCode", ErrorMsgUtil.getMsg("Error.user.smsCode", null, null)));
                }
            }
        }
        List<Province> provinceList =cityService.getAllProvince();
        modelAndView.addObject("provinceList", provinceList);
        Map pawnTypes=configuration.getPawnType();
        if(!CollectionUtils.isEmpty(pawnTypes)){
            LOGGER.debug(" add pawnTypes to model");
            modelAndView.addObject("pawnTypes",pawnTypes);
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : fieldErrors) {
                errors.put(err.getField(), err.getDefaultMessage());
                LOGGER.debug("File name{} ,message{}",err.getField(), err.getDefaultMessage());
            }
            modelAndView.addObject("errors",errors);
            modelAndView.addObject("pawnPersonInfo", pawnPersonInfo);
            LOGGER.debug("The validate is fail");
            modelAndView.setViewName(urlConfiguration.getPawnPersonInfoPage());
            return modelAndView;
        }

        if(ObjectUtils.isEmpty(pawnPersonInfo)){
            LOGGER.debug("The pawnPersonInfo is empty");
            modelAndView.setViewName(urlConfiguration.getPawnPersonInfoPage());
            return modelAndView;
        }

        pawnPersonInfo.setAddress(pawnPersonInfo.getProvince()+pawnPersonInfo.getCity()+pawnPersonInfo.getDistrict());
        pawnPersonInfoService.createPawnPersonInfo(pawnPersonInfo);
        LOGGER.debug("The create PawnPersonInfo id is {}",pawnPersonInfo.getId());


        if(Configuration.ADMIN_PHONENUMBER!=null && !Configuration.ADMIN_PHONENUMBER.trim().equals("")){
            if (!ObjectUtils.isEmpty(pawnPersonInfo.getId())) {
                LOGGER.debug("add pawnPersonInfo success");
                String content = smsContentDisplay(pawnPersonInfo);
                smsService.sendOnlinePawnSmsToMe(Configuration.ADMIN_PHONENUMBER, content);
                LOGGER.debug("send message to {},content is:{}", Configuration.ADMIN_PHONENUMBER, content);
                LOGGER.debug("send sms successful");
            } else {
                LOGGER.debug("send sms false");
            }
        }

        modelAndView.setViewName("/invest/invest-end");
        return modelAndView;
    }



    private String smsContentDisplay (PawnPersonInfo pawnPersonInfo) {
        LOGGER.debug("show sms content method");
        Map map=configuration.getPawnType();
        String type=(String)map.get(pawnPersonInfo.getPawnType());
        String content = "用户"+pawnPersonInfo.getName()+"提交了典当信息。"+ "电话:"+pawnPersonInfo.getPhoneNumber()
        +"。 典当类型为:"+type+"。 典当金额："+pawnPersonInfo.getPrice()+"详细地址为："+pawnPersonInfo.getAddress()+pawnPersonInfo.getDetailAddress();
        LOGGER.debug("show sms content:{}", content);
        return content;
    }



}
