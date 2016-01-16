package com.pgt.mobile.banner.controller;

import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.mobile.base.controller.BaseMobileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 15-12-23.
 */
@RequestMapping("/mBanner")
@RestController
public class BannerMobileController extends BaseMobileController{

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value="/query",method = RequestMethod.POST)
    public Map<String,Object> queryBanner(String type){

        Map<String,Object> responseMap = new HashMap<String,Object>();
        if(StringUtils.isEmpty(type)){
            return responseMobileFail(responseMap, "type.empty");
        }
        Banner banner = bannerService.queryBannerByType(type);

        if(ObjectUtils.isEmpty(banner)){
            return responseMobileFail(responseMap, "BannerList.empty");
        }
        responseMap.put("banner",banner);
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }




}
