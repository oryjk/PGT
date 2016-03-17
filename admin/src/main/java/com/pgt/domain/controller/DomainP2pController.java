package com.pgt.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-17.
 */
@Controller
@RequestMapping("domain")
public class DomainP2pController {
    @RequestMapping("p2p")
    public ModelAndView p2pMain(ModelAndView modelAndView){
        modelAndView.setViewName("domain/p2p/p2p-list");
        return modelAndView;
    }
}
