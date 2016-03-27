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
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
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
import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by carlwang on 3/7/16.
 */
@RequestMapping("qqLogin")
@RestController
public class QQController {


    private static final Logger LOGGER = LoggerFactory.getLogger(QQController.class);

    @Autowired
    private ThirdLoginService thirdLoginService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private SSOService ssoService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            response.sendRedirect(new com.qq.connect.oauth.Oauth().getAuthorizeURL(request));
        } catch (com.qq.connect.QQConnectException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "afterLogin", method = RequestMethod.GET)
    public ModelAndView afterComplete(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        PrintWriter out = response.getWriter();

        try {
            AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);

            String accessToken = null,
                    openID = null;
            long tokenExpireIn = 0L;


            if (accessTokenObj.getAccessToken().equals("")) {
                LOGGER.debug("no find request param");
            } else {
                LOGGER.debug("The method find assessToken,");
                accessToken = accessTokenObj.getAccessToken();
                tokenExpireIn = accessTokenObj.getExpireIn();
                LOGGER.debug("The accessToken is {},",accessToken);

                OpenID openIDObj = new OpenID(accessToken);
                openID = openIDObj.getUserOpenID();
                LOGGER.debug("The openId is {},",openID);
                ThirdLogin thirdLogin = thirdLoginService.queryThirdLoginByOpenId(openID);
                Date expireIn = TenderDateUtils.convert(String.valueOf(tokenExpireIn));
                LOGGER.debug("The expireIn time is {}",expireIn);
                if (ObjectUtils.isEmpty(thirdLogin)) {
                    //first login
                    //create user bean
                    User user = new User();
                    UserInformation userInformation = new UserInformation();
                    UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
                    UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();

                    if (userInfoBean.getRet() == 0) {
                        LOGGER.debug("create user");
                        user.setUsername(userInfoBean.getNickname());
                        LOGGER.debug("The nickname is {}",userInfoBean.getNickname());
                        user.setUpdateDate(new Date());
                        user.setCreateDate(new Date());
                        user.setPhoneNumber("000000");
                        userService.saveUser(user);

                        if(!ObjectUtils.isEmpty(user.getId())) {
                            LOGGER.debug("create userInformation and user id is {}",user.getId());
                            userInformation.setNickname(userInfoBean.getNickname());
                            userInformation.setGender(userInfoBean.getGender());
                            userInformation.setPath(userInfoBean.getAvatar().getAvatarURL100());
                            userInformation.setUser(user);
                            userInformationService.createInformation(userInformation);
                            LOGGER.debug("create userInformation");
                        }else{
                            LOGGER.debug("The userId is empty ,create user fail");
                            return modelAndView;
                        }
                    } else {
                        LOGGER.debug("The method not find your information {}", userInfoBean.getMsg());
                        return modelAndView;
                    }

                    thirdLogin = new ThirdLogin();
                    thirdLogin.setOpenId(openID);
                    thirdLogin.setToken(accessToken);
                    thirdLogin.setType(SocialType.QQ.toString());
                    thirdLogin.setExpire(expireIn);
                    thirdLogin.setUser(user);
                    thirdLoginService.createThirdLogin(thirdLogin);
                    request.getSession().setAttribute(UserConstant.CURRENT_USER,user);
                    LOGGER.debug("set User to session and id is {}",user.getId());
                } else {
                    //update expireIn
                    thirdLogin.setExpire(expireIn);
                    thirdLoginService.updateThirdLogin(thirdLogin);
                    User user =thirdLogin.getUser();
                    if(!ObjectUtils.isEmpty(user)){
                        request.getSession().setAttribute(UserConstant.CURRENT_USER,user);
                        ssoService.cacheUser(user, null, null);
                        LOGGER.debug("set User to session and id is {}",user.getId());
                    }else {
                        LOGGER.debug("not find User");
                        return modelAndView;
                    }
                }
            }
        } catch (QQConnectException e) {
        }
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
