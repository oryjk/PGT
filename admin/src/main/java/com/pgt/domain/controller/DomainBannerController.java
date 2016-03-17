package com.pgt.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-17.
 */
@Controller
@RequestMapping("domain")
public class DomainBannerController {
    @RequestMapping("banner")
    public ModelAndView bannerMain(ModelAndView modelAndView){
        modelAndView.setViewName("domain/banner/banner-list");
        return modelAndView;
    }
}
