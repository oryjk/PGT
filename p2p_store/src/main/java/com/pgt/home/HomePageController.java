package com.pgt.home;

import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.tender.service.TenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by carlwang on 1/26/16.
 */

@RestController
public class HomePageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomePageController.class);
    @Autowired
    private TenderService tenderService;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHomePage(HttpServletRequest request, ModelAndView modelAndView) {

        return modelAndView;
    }
}
