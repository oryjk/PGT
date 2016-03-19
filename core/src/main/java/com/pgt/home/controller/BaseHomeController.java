package com.pgt.home.controller;

import com.pgt.common.bean.Banner;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

/**
 * Created by carlwang on 3/19/16.
 */
public class BaseHomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseHomeController.class);
    @Autowired
    private BannerService bannerService;

    @Autowired
    private Configuration configuration;

    protected Banner queryBanner(String bannerType) {
        BannerWebSite bannerWebSite = null;
        switch (configuration.getCurrentSite()) {
            case P2P:
                bannerWebSite = BannerWebSite.P2P_STORE;
                break;
            case B2C:
                bannerWebSite = BannerWebSite.B2C_STORE;
                break;
        }

        if (ObjectUtils.isEmpty(bannerWebSite)) {
            LOGGER.debug("No need render banner.");
            return null;
        }
        if (StringUtils.isBlank(bannerType)) {
            LOGGER.debug("The banner type is empty,please pass one banner type.");
            return null;
        }
        LOGGER.debug("render {} banner.", bannerWebSite.toString());
        Banner banner = bannerService.queryBannerByTypeAndWebSite(bannerType, bannerWebSite.toString());
        LOGGER.debug("The home banner is {}.", banner);
        return banner;
    }
}
