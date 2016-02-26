package com.pgt.tender.controller;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlwang on 2/26/16.
 */

@RequestMapping("/tender")
@RestController
public class TenderListController {

    @Autowired
    private ResponseBuilderFactory responseBuilderFactory;

    @RequestMapping("/tenderList")
    public ResponseEntity get(@RequestParam(value = "categoryId") Integer categoryId, @RequestParam(value = "rootCategoryId") Integer rootCategoryId,
                              @RequestParam(value = "newest") Boolean newest,
                              @RequestParam(value = "cycle") Boolean cycle,
                              @RequestParam(value = "end") Boolean end,
                              @RequestParam(value = "all") Boolean all,
                              @RequestParam(value = "beginInMinute") Boolean beginInMinute,
                              @RequestParam(value = "underway", required = false) Boolean underway,
                              @RequestParam(value = "ended", required = false) Boolean ended) {
        Map<String, Object> data = buildData();


    }


    private Map<String, Object> buildData() {
        ResponseBuilder rb = responseBuilderFactory.buildResponseBean().setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        rb.setData(data);
        return data;
    }
}
