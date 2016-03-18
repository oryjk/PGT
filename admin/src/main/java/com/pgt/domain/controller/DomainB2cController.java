package com.pgt.domain.controller;

import com.pgt.domain.bean.Domain;
import com.pgt.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-17.
 */
@Controller
@RequestMapping("domain")
public class DomainB2cController {
    @Autowired
    DomainService domainService;

    public ModelAndView select(ModelAndView modelAndView){
        modelAndView.setViewName("domain/b2c/b2c-list");
        Domain domain = new Domain();
        domain.setType("1");
        modelAndView.addObject("domainList", domainService.selectDomain(domain));
        return modelAndView;
    }

    @RequestMapping("b2c")
    public ModelAndView b2cMain(ModelAndView modelAndView){
        modelAndView = this.select(modelAndView);
        return modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView createDoGet(ModelAndView modelAndView){
        modelAndView.setViewName("domain/b2c/b2c-add-and-modify");
        return  modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView createDoPost(ModelAndView modelAndView,Domain domain){
        if(!ObjectUtils.isEmpty(domain)){
            domainService.create(domain);
        }
        modelAndView = this.select(modelAndView);
        return modelAndView;
    }
}
