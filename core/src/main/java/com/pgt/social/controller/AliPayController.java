package com.pgt.social.controller;

import com.pgt.configuration.Configuration;
import com.pgt.constant.UserConstant;
import com.pgt.integration.alipay.AlipaySubmit;
import com.pgt.integration.alipay.MD5;
import com.pgt.sso.service.SSOService;
import com.pgt.user.bean.ThirdLogin;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.ThirdLoginService;
import com.pgt.user.service.UserInformationService;
import com.pgt.user.service.UserService;

import com.pgt.utils.TenderDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijianzhi on 2016/4/5.
 */

@RequestMapping("/aliPayLogin")
@RestController
public class AliPayController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private ThirdLoginService thirdLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private SSOService ssoService;

    @Autowired
    private Configuration configuration;

    private String partner = "2088021829216373";
    private String _input_charset = "utf-8";
    //private String return_url="http%3A%2F%2Fdev.p2p.dianjinzi.com%2FaliPayLogin%2FafterLogin";
    private String return_url = "";
    private String return_url_end = "/aliPayLogin/afterLogin";
    private String target_service = "user.auth.quick.login";
    private String service = "alipay.auth.authorize";




    @RequestMapping(value = "login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {

        return_url = configuration.getHost()+return_url_end;
       // http://dev.p2p.dianjinzi.com/aliPayLogin/afterLogin?is_success=T&notify_id=RqPnCoPT3K9%252Fvwbh3InUHyGfw4ruXjdd6skyhuzURie4IrguwX2iwY0ac2LwAlo7GaPw&token=20160406514556a6dfc748348f8b36febe896X37&user_id=2088021829216373&sign=e1c82ad81dd6e4e2f5134605fb503a8d&sign_type=MD5
        Map<String, String> sParaTemp = new HashMap<String, String>();
        sParaTemp.put("service", "alipay.auth.authorize");
        sParaTemp.put("partner", partner);
        sParaTemp.put("_input_charset", _input_charset);
        sParaTemp.put("target_service", target_service);
        sParaTemp.put("return_url", return_url);
        String aliLoginUrl = AlipaySubmit.buildRequestPara(sParaTemp);
        try {
            response.sendRedirect(aliLoginUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "afterLogin")
    public ModelAndView afterLogin(HttpServletRequest request, HttpServletResponse response, ModelAndView modelAndView){
        String is_success = (String) request.getParameter("is_success");
        if(is_success.equals("T")){
            LOGGER.debug("ali fast login success");
            String user_id = (String)request.getParameter("user_id");
            String token = (String)request.getParameter("token");
            user_id = "ali"+user_id;
            ThirdLogin thirdLogin = thirdLoginService.queryThirdLoginByOpenId(user_id);
            Date expireIn = TenderDateUtils.convert(String.valueOf(new Date().getTime()));
            if(ObjectUtils.isEmpty(thirdLogin)){
                LOGGER.debug("create thirdLogin");
                UserInformation userInformation = new UserInformation();
                LOGGER.debug("create user");
                User user = new User();
                user.setUsername(user_id);
                user.setUpdateDate(new Date());
                user.setCreateDate(new Date());
                user.setPhoneNumber("00000000000");
                userService.saveUser(user);
                if(!ObjectUtils.isEmpty(user.getId())){
                    LOGGER.debug("create userInformation and user id is {}",user.getId());
                    userInformation.setNickname(user_id);
                    userInformation.setUser(user);
                    userInformationService.createInformation(userInformation);
                    LOGGER.debug("create userInfomation");
                }else {
                    LOGGER.debug("The userId is empty ,create user fail");
                    return modelAndView;
                }
                thirdLogin = new ThirdLogin();
                thirdLogin.setOpenId(user_id.toString());
                thirdLogin.setToken(token.toString());
                thirdLogin.setType(SocialType.Ali.toString());
                thirdLogin.setExpire(expireIn);
                thirdLogin.setUser(user);
                thirdLoginService.createThirdLogin(thirdLogin);
                request.getSession().setAttribute(UserConstant.CURRENT_USER,user);
                ssoService.cacheUser(user,null,null);
                LOGGER.debug("set User to session and id is {}" ,user.getId());
            }else {
                thirdLoginService.updateThirdLogin(thirdLogin);
                User user = thirdLogin.getUser();
                if (!ObjectUtils.isEmpty(user)) {
                    request.getSession().setAttribute(UserConstant.CURRENT_USER, user);
                    ssoService.cacheUser(user, null, null);
                    LOGGER.debug("set  User to session and id is {}", user.getId());
                } else {
                    LOGGER.debug("not find User");
                    return modelAndView;
                }
            }
            modelAndView.setViewName("/user/successLogin");
            return modelAndView;
        }
        LOGGER.debug("ali login fail");
        return  modelAndView;
    }
}
