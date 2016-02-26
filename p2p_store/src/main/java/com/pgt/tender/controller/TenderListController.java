package com.pgt.tender.controller;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTenderListFilter;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.utils.PaginationBean;
import com.pgt.utils.SearchConverToList;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 2/26/16.
 */

@RequestMapping("/tender")
@RestController
public class TenderListController {

    @Autowired
    private ResponseBuilderFactory responseBuilderFactory;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;

    @RequestMapping(value = "/tenderList", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView modelAndView, @RequestParam(value = "sorts", required = false) List<ESSort> esSorts,
                            @RequestParam(value = "term", required = false) ESTerm esTerm,
                            @RequestParam(value = "esTenderListFilter", required = false) ESTenderListFilter esTenderListFilter,
                            @RequestParam(value = "paginationBean", required = false) PaginationBean paginationBean
    ) {
        SearchResponse response = tenderSearchEngineService.findTenders(esTerm, esTenderListFilter, paginationBean, esSorts);
        List list = SearchConverToList.searchConvertToList(response);
        modelAndView.addObject("tenderListResult", list);
        modelAndView.setViewName("/tenderList/tenderList");
        return modelAndView;

    }


    @RequestMapping("/ajaxtenderList")
    public ResponseEntity ajaxGet(@RequestParam(value = "sorts", required = false) List<ESSort> esSorts,
                                  @RequestParam(value = "term", required = false) ESTerm esTerm,
                                  @RequestParam(value = "esTenderListFilter", required = false) ESTenderListFilter esTenderListFilter,
                                  @RequestParam(value = "paginationBean", required = false) PaginationBean paginationBean
    ) {
        ResponseBuilder rb = responseBuilderFactory.buildResponseBean().setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        rb.setData(data);
        SearchResponse response = tenderSearchEngineService.findTenders(esTerm, esTenderListFilter, paginationBean, esSorts);
        List list = SearchConverToList.searchConvertToList(response);
        data.put("tenderListResult", list);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);

    }


}
