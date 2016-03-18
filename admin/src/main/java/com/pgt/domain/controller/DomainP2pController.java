package com.pgt.domain.controller;

import com.pgt.domain.bean.Domain;
import com.pgt.domain.constant.DomainConstant;
import com.pgt.domain.service.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-17.
 */
@Controller
@RequestMapping("domain")
public class DomainP2pController {
    @Autowired
    DomainService domainService;

    public ModelAndView select(ModelAndView modelAndView){
        modelAndView.setViewName("domain/p2p/p2p-list");
        Domain domain = new Domain();
        domain.setType(DomainConstant.ZAIDANGTAO);
        modelAndView.addObject("domainList", domainService.selectDomain(domain));
        return modelAndView;
    }

    @RequestMapping("p2p")
    public ModelAndView p2pMain(ModelAndView modelAndView){
        modelAndView = this.select(modelAndView);
        return modelAndView;
    }
}
