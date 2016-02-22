package com.pgt.tender.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by carlwang on 2/22/16.
 */

@RequestMapping("/tender")
@RestController
public class TenderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

    @RequestMapping(value = "/{tenderId}", method = RequestMethod.GET)
    public ModelAndView getTenderDetailPage(@PathVariable("tenderId") Integer tenderId, ModelAndView modelAndView) {
        //TODO
        return modelAndView;
    }
}
