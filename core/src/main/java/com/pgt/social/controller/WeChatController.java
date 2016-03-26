package com.pgt.social.controller;

import com.pgt.user.service.ThirdLoginService;
import com.pgt.user.service.UserInformationService;
import com.pgt.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

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

    private String appId="wxc0ba761c7a33d4d5";

    private String redirect_uri="dev.p2p.dianjinzi.com%2fweChatLogin%2fafterLogin";

    private String requestCode="https://open.weixin.qq.com/connect/qrconnect?appid="+appId+"&response_type=code&scope=snsapi_login";


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(HttpServletRequest request, HttpServletResponse response){
        try {

            WeChatAutho weChatAutho = new WeChatAutho();
            String sessionId=request.getSession().getId();
            String m=weChatAutho.GetWeiXinCode(appId, DigestUtils.md5Hex(sessionId),redirect_uri);

            LOGGER.debug("The return is:{}",m);
            response.sendRedirect(m);
            //response.sendRedirect(requestCode+"&state="+ DigestUtils.md5Hex(sessionId)+"&redirect_uri="+redirect_uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/afterLogin", method = RequestMethod.GET)
    public ModelAndView afterComplete(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) throws IOException {

        LOGGER.debug("The method query afterLogin");
        return modelAndView;
    }


}
