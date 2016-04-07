package com.pgt.service;

import com.pgt.cart.service.ProductBrowseTrackService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by carlwang on 4/6/16.
 */
@Service(value = "productBrowseTrackService")
public class ProductBrowseTrackServiceImp extends ProductBrowseTrackService {
    @Override
    public boolean track(HttpServletRequest pRequest) {
        return false;
    }

    @Override
    public String getRequestedProductId(HttpServletRequest pRequest) {
        return null;
    }
}
