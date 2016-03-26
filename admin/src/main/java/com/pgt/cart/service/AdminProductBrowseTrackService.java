package com.pgt.cart.service;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by carlwang on 3/25/16.
 */
@Service(value = "productBrowseTrackService")
public class AdminProductBrowseTrackService extends ProductBrowseTrackService {
    @Override
    public boolean track(HttpServletRequest pRequest) {
        return false;
    }

    @Override
    public String getRequestedProductId(HttpServletRequest pRequest) {
        return null;
    }
}
