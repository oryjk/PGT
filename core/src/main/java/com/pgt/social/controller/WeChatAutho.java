package com.pgt.social.controller;

/**
 * Created by zhangxiaodong on 16-3-18.
 */
public class WeChatAutho {

    /// <summary>
    /// 获取微信Code
    /// </summary>
    /// <param name="appId"></param>
    /// <param name="appSecret"></param>
    /// <param name="redirectUrl"></param>
    public String GetWeiXinCode(String appId, String appSecret, String redirectUrl) {
        //微信登录授权
        //string url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + appId + "&redirect_uri=" + redirectUrl
        // +"&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        //微信OpenId授权
        //string url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + appId + "&redirect_uri=" + redirectUrl
        // +"&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect";
        //微信用户信息授权
        String url = "https://open.weixin.qq.com/connect/qrconnect?appid=" + appId + "&redirect_uri=" + redirectUrl +
                "&response_type=code&scope=snsapi_login#wechat_redirect";
        return url;
    }


}
