package com.pgt.pawn.controller;


import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.pawn.bean.OnlinePawn;
import com.pgt.pawn.bean.OnlinePawnCustom;
import com.pgt.pawn.constant.OnlinePawnConstant;
import com.pgt.pawn.service.OnlinePawnService;
import com.pgt.sms.service.SmsService;
import com.pgt.utils.ErrorMsgUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by hd on 16-1-28.
 */
@Controller
@RequestMapping("/onlinePawn")
public class OnlinePawnController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinePawnController.class);
    @Autowired
    private OnlinePawnService onlinePawnService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private SmsService smsService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/addOnlinePawn",method = RequestMethod.GET)
    public ModelAndView addOnlinePawn(ModelAndView modelAndView,HttpServletRequest request,HttpSession session){
        LOGGER.debug("invoking addOnlinePawn method");

        List<Category> categoryList = categoryService.queryOnlinePawnCategories();
        if (categoryList!=null){
            LOGGER.debug("the categoryList is null");
        }else {
            LOGGER.debug("the categoryList.size:",categoryList.size());
        }


        modelAndView.addObject("categoryList",categoryList);
        modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
        return modelAndView;
    }
    @RequestMapping(value = "/addOnlinePawn",method = RequestMethod.POST)
    public ModelAndView addOnlinePawnSumbit(OnlinePawnCustom onlinePawnCustom,ModelAndView modelAndView,
                                            HttpServletRequest request,BindingResult bindingResult){

        if (onlinePawnCustom.getCategoryId()==null){
            LOGGER.debug("category can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.categoryId", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        if (onlinePawnCustom.getDurationDate()==null){
            LOGGER.debug("durationDate can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.durationDate", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        if (onlinePawnCustom.getPawnTotal()==null){
            LOGGER.debug("pawnTotal can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.pawnTotal", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        if (StringUtils.isBlank(onlinePawnCustom.getPhoneNumber())){
            LOGGER.debug("phoneNumber can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.phoneNumber", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        if (onlinePawnCustom.getPhoneNumber().length()!= OnlinePawnConstant.PHONENUMBER_LENGTH){
            LOGGER.debug("phoneNumber can't error");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("Error.onlinePawn.phoneNumber", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }

        if (!configuration.isSmsMock()) {
            String smsCode = onlinePawnCustom.getSmsCode();
            if (!StringUtils.isBlank(smsCode)) {
                LOGGER.debug("smsCode is :{}",smsCode);
                String phoneCode = (String) request.getSession().getAttribute(Constants.ONLINEPAWN_SESSION_PHONE_CODE);
                if (!smsCode.equals(phoneCode)) {
                    bindingResult.addError(
                            new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("Error.user.smsCode", null, null)));
                    modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
                    modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
                    return modelAndView;
                }
            }
        }
        int id = onlinePawnService.add(onlinePawnCustom.getOnlinePawn());
        if(id>0){
            LOGGER.debug("add olinePawn success");
            OnlinePawn onlinePawn= onlinePawnService.select(id);
            String content = smsContentDisplay(onlinePawn);
            smsService.sendOnlinePawnSmsToMe(OnlinePawnConstant.MY_PHONENUMBER,content);
            LOGGER.debug("send message to {},content is:{}",OnlinePawnConstant.MY_PHONENUMBER,content);
        }else {
            LOGGER.debug("add olinePawn false");
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }

        modelAndView.setViewName(Constants.ONLINE_PAWN_FINDALL);
        return modelAndView;
    }


    @RequestMapping(value = "/findAll")
    public ModelAndView findOnlinePawn(ModelAndView modelAndView,String phoneNumber,BindingResult bindingResult){

        if (StringUtils.isBlank(phoneNumber)){
            LOGGER.debug("the phoneNumber is null");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.phoneNumber", null, null)));
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        List<OnlinePawn> onlinePawnList= onlinePawnService.findByPhoneNumber(phoneNumber);
        modelAndView.addObject("onlinePawnList",onlinePawnList);
        modelAndView.setViewName(Constants.ONLINE_PAWN_FINDALL);
        return modelAndView;
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET)
    public ModelAndView updateOnlinePawn(ModelAndView modelAndView,Integer id,BindingResult bindingResult){

        if (id ==null){
            LOGGER.debug("the id is null");
            modelAndView.setViewName(Constants.ONLINE_PAWN_ADD);
            return modelAndView;
        }
        OnlinePawn onlinePawn= onlinePawnService.select(id);
        modelAndView.addObject("onlinePawn",onlinePawn);
        modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
        return modelAndView;
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ModelAndView updateSubmitOnlinePawn(ModelAndView modelAndView,OnlinePawnCustom onlinePawnCustom,BindingResult bindingResult){

        if (onlinePawnCustom.getCategoryId()==null){
            LOGGER.debug("category can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.categoryId", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
            return modelAndView;
        }
        if (onlinePawnCustom.getDurationDate()==null){
            LOGGER.debug("durationDate can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.durationDate", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
            return modelAndView;
        }
        if (onlinePawnCustom.getPawnTotal()==null){
            LOGGER.debug("pawnTotal can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.pawnTotal", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
            return modelAndView;
        }
        if (StringUtils.isBlank(onlinePawnCustom.getPhoneNumber())){
            LOGGER.debug("phoneNumber can't empty");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("NotEmpty.onlinePawn.phoneNumber", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
            return modelAndView;
        }
        if (onlinePawnCustom.getPhoneNumber().length()!= OnlinePawnConstant.PHONENUMBER_LENGTH){
            LOGGER.debug("phoneNumber can't error");
            bindingResult.addError(
                    new FieldError("OnlinePawnCustom", "error", ErrorMsgUtil.getMsg("Error.onlinePawn.phoneNumber", null, null)));
            modelAndView.addObject("onlinePawnCustom", onlinePawnCustom);
            modelAndView.setViewName(Constants.ONLINE_PAWN_UPDATE);
            return modelAndView;
        }

        onlinePawnService.update(onlinePawnCustom.getOnlinePawn());

        modelAndView.setViewName(Constants.ONLINE_PAWN_FINDALL);
        return modelAndView;
    }


    private String smsContentDisplay(OnlinePawn onlinePawn){
        String content =null;
        LOGGER.debug("show sms content method,");
        Category category = categoryService.queryCategory(onlinePawn.getCategoryId());

        Date date =new Date(onlinePawn.getDurationDate());
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String time=sdf.format(date);

        content = "用户："+onlinePawn.getPhoneNumber()+",需要进行:"+category.getName()
                +"业务，融资金额："+onlinePawn.getPawnTotal()+",借贷期限至："+time+"。";

        LOGGER.debug("show sms content:{}",content);
        return content;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }



}
