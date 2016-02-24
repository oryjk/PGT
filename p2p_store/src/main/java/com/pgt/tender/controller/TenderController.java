package com.pgt.tender.controller;

import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.TenderSearchEngineService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by carlwang on 2/22/16.
 */

@RequestMapping("/tender")
@RestController
public class TenderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;



    @RequestMapping(value = "/{tenderId}", method = RequestMethod.GET)
    public ModelAndView getTenderDetailPage(@PathVariable("tenderId") Integer tenderId, ModelAndView modelAndView) {

        LOGGER.debug("The method query tenderDetail");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("the tenderId is empty");
            return modelAndView;
        }
        SearchResponse tenderResponse = tenderSearchEngineService.findTenderById(tenderId);
        SearchHit[] tenders = tenderResponse.getHits().getHits();
        if (ObjectUtils.isEmpty(tenders)) {
            LOGGER.debug("The tender is exist");
            return modelAndView;
        }
        Map tender =tenders[0].getSource();
        LOGGER.debug("The query tender id is {}.",tenderId);

        if(ObjectUtils.isEmpty(tender.get("products"))){
            ArrayList products=(ArrayList) tender.get("products");

        }




        modelAndView.addObject("tender",tender);
        modelAndView.setViewName("/detail/detail");
        return modelAndView;
    }
}
