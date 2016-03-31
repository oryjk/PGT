package com.pgt.social.controller;

import com.pgt.constant.UserConstant;
import com.pgt.sso.service.SSOService;
import com.pgt.user.bean.ThirdLogin;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.ThirdLoginService;
import com.pgt.user.service.UserInformationService;
import com.pgt.user.service.UserService;
import com.pgt.utils.TenderDateUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangxiaodong on 16-3-18.
 */
@RequestMapping("/weChatLogin")
@RestController
public class WeChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private ThirdLoginService thirdLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private SSOService ssoService;

    private String appId = "wxc0ba761c7a33d4d5";

    private String redirect_uri = "http%3A%2F%2Fdev.p2p.dianjinzi.com%2FweChatLogin%2FafterLogin";

    private String appSecret="dc2dbef683fcc208e7710889fe2dc920";

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {

            WeChatAutho weChatAutho = new WeChatAutho();
            String sessionId = request.getSession().getId();
            String m = weChatAutho.GetWeiXinCode(appId, DigestUtils.md5Hex(sessionId), redirect_uri);

            LOGGER.debug("The return is:{}", m);
            response.sendRedirect(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/afterLogin", method = RequestMethod.GET)
    public ModelAndView afterComplete(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response,String code,String state) throws IOException {
        LOGGER.debug("The method query afterLogin");

        if(StringUtils.isEmpty(code)){
            LOGGER.debug("The code is empty");
            return modelAndView;
        }
        String requestUrl="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        Map<String,String> createMap = new HashMap<>();

        //createMap.put("appid",appId);
        //createMap.put("secret",appSecret);
        //createMap.put("code",code);
        //createMap.put("grant_type","authorization_code");
        try {
            String responseJson = doGet(requestUrl, createMap);
            JSONObject jo = new JSONObject(responseJson);
            Object access_token = (Object) jo.get("access_token");
            Object expires_in = (Object) jo.get("expires_in");
            Object refresh_token = (Object) jo.get("refresh_token");
            Object openid = (Object) jo.get("openid");
            Object scope = (Object) jo.get("scope");

            ThirdLogin thirdLogin = thirdLoginService.queryThirdLoginByOpenId(openid.toString());
            Date expireIn = TenderDateUtils.convert(String.valueOf(expires_in));
            LOGGER.debug("The expireIn time is {}", expireIn);


            String requestInfo= "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid;
            String responseInfo = doGet(requestInfo,createMap);
            JSONObject info = new JSONObject(responseInfo);
            Object nickname = (Object) info.get("nickname");
            Object sex = (Object) info.get("sex");
            Object headimgurl = (Object) info.get("headimgurl");


            if (ObjectUtils.isEmpty(thirdLogin)) {

                LOGGER.debug("create thirdLogin .");
                UserInformation userInformation = new UserInformation();
                LOGGER.debug("create user");
                User user = new User();
                user.setUsername(nickname.toString());
                LOGGER.debug("The nickname is {}","");
                user.setUpdateDate(new Date());
                user.setCreateDate(new Date());
                user.setPhoneNumber("000000");
                userService.saveUser(user);

                if (!ObjectUtils.isEmpty(user.getId())) {
                    LOGGER.debug("create userInformation and user  id is {}", user.getId());
                    userInformation.setNickname(nickname.toString());
                    userInformation.setUser(user);
                    userInformation.setPath(headimgurl.toString());
                    userInformation.setGender(sex.toString()=="1"?"男":"女");
                    userInformationService.createInformation(userInformation);
                    LOGGER.debug("create userInformation");
                } else {
                    LOGGER.debug("The userId is empty ,create user fail");
                    return modelAndView;
                }

                thirdLogin = new ThirdLogin();
                thirdLogin.setOpenId(openid.toString());
                thirdLogin.setToken(access_token.toString());
                thirdLogin.setType(SocialType.WeChat.toString());
                thirdLogin.setExpire(expireIn);
                thirdLogin.setUser(user);
                thirdLogin.setRefreshToken(refresh_token.toString());
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
                    LOGGER.debug("set  User to  session and id is {}", user.getId());
                } else {
                    LOGGER.debug("not find User");
                    return modelAndView;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


    public String doGet(String url,Map<String, String> param) throws Exception {
           String response = null;
            HttpClient client = new HttpClient();
            HttpMethod method = new GetMethod(url);
           if (param != null) {
              HttpMethodParams hp = new HttpMethodParams();
               LOGGER.debug("the httpclient  get request");
            for (Map.Entry<String, String> entry : param.entrySet()) {
                hp.setParameter(entry.getKey(), entry.getValue());
            }
            method.setParams(hp);
        }
        try {
            client.executeMethod(method);
            response = method.getResponseBodyAsString();
        } catch (IOException e) {
            LOGGER.error("deal HTTP Get request {}" + url + "has exception", e.getMessage());
        } finally {
            method.releaseConnection();
        }
        return response;
    }


}
