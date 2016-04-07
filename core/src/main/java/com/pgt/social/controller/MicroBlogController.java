package com.pgt.social.controller;


import com.pgt.configuration.Configuration;
import com.pgt.constant.UserConstant;
import com.pgt.sso.service.SSOService;
import com.pgt.user.bean.ThirdLogin;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.ThirdLoginService;
import com.pgt.user.service.UserInformationService;
import com.pgt.user.service.UserService;
import com.pgt.utils.TenderDateUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
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
 * Created by zhangxiaodong on 3/14/16.
 */
@RequestMapping("/microBlogLogin")
@RestController
public class MicroBlogController {


    private static final Logger LOGGER = LoggerFactory.getLogger(MicroBlogController.class);

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

    private String client_id="3367852669";

    private String  client_secret="28369d211db53c05862eea23c795a078";

    private String redirect_uri="";

    private String redirect_uri_end ="/microBlogLogin/afterLogin";



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response){
        redirect_uri = configuration.getHost()+redirect_uri_end;
        String  requestCode="https://api.weibo.com/oauth2/authorize"+"?client_id="+client_id+"&redirect_uri="+redirect_uri+"&response_type=code"+"&forcelogin=true";
        try {
            response.sendRedirect(requestCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/afterLogin", method = RequestMethod.GET)
    public ModelAndView afterComplete(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,String code) throws IOException {
        LOGGER.debug("The method deal afterLogin");

        if(StringUtils.isEmpty(code)){
            LOGGER.debug("The code is empty");
            return modelAndView;
        }
        String requestUrl="https://api.weibo.com/oauth2/access_token?client_id="+client_id+"&client_secret="+client_secret+"&grant_type=authorization_code"+"&code="+code+"&redirect_uri="+redirect_uri;
        Map<String,String> createMap = new HashMap<>();

        try {
            String responseJson = doPost(requestUrl, createMap);
            JSONObject jo = new JSONObject(responseJson);
            Object access_token = (Object) jo.get("access_token");
            Object expires_in = (Object) jo.get("expires_in");
            Object uid = (Object) jo.get("uid");

            ThirdLogin thirdLogin = thirdLoginService.queryThirdLoginByOpenId(uid.toString());
            Date expireIn = TenderDateUtils.convert(String.valueOf(expires_in));
            LOGGER.debug("The expireIn time is {}", expireIn);

            if (ObjectUtils.isEmpty(thirdLogin)) {

                LOGGER.debug("create thirdLogin");
                UserInformation userInformation = new UserInformation();
                LOGGER.debug("create user");
                User user = new User();
                user.setUsername(uid.toString());
                LOGGER.debug("The nickname is {}", uid);
                user.setUpdateDate(new Date());
                user.setCreateDate(new Date());
                user.setPhoneNumber("000000");
                userService.saveUser(user);

                if (!ObjectUtils.isEmpty(user.getId())) {
                    LOGGER.debug("create userInformation and user id is {}", user.getId());
                    userInformation.setNickname(uid.toString());
                    userInformation.setUser(user);
                    userInformationService.createInformation(userInformation);
                    LOGGER.debug("create userInformation");
                } else {
                    LOGGER.debug("The userId is empty ,create user fail");
                    return modelAndView;
                }

                thirdLogin = new ThirdLogin();
                thirdLogin.setOpenId(uid.toString());
                thirdLogin.setToken(access_token.toString());
                thirdLogin.setType(SocialType.MicroBlog.toString());
                thirdLogin.setExpire(expireIn);
                thirdLogin.setUser(user);
                thirdLoginService.createThirdLogin(thirdLogin);
                request.getSession().setAttribute(UserConstant.CURRENT_USER, user);
                ssoService.cacheUser(user, null, null);
                LOGGER.debug("set User to session and id is {}", user.getId());

            } else {
                //update expireIn
                thirdLogin.setExpire(expireIn);
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

        }catch (Exception e){
            LOGGER.debug("has exception {}",e.getMessage());
            return modelAndView;
        }
        modelAndView.setViewName("/user/successLogin");
        return modelAndView;
    }

    public static String doPost(String url, Map<String, String> params) {
        String response = null;
        HttpClient client = new HttpClient();
        HttpMethod method = new PostMethod(url);
        if (params != null) {
            HttpMethodParams p = new HttpMethodParams();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                p.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(p);
        }
        try {
            client.executeMethod(method);
            response = method.getResponseBodyAsString();
        } catch (IOException e) {
            LOGGER.error("deal HTTP Post request {}" + url + "has exception", e.getMessage());
        } finally {
            method.releaseConnection();
        }

        return response;
    }


}
