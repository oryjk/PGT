package com.pgt.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-17.
 */
@Controller
@RequestMapping("domain")
public class DomainB2cController {
    @RequestMapping("b2c")
    public ModelAndView b2cMain(ModelAndView modelAndView){
        modelAndView.setViewName("domain/b2c/b2c-list");
        return modelAndView;
    }
}
