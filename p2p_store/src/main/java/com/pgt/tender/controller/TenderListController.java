package com.pgt.tender.controller;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.category.bean.CategoryType;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.ESConstants;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTenderListFilter;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.CategorySearchEngineService;
import com.pgt.search.service.ESSearchService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.utils.PaginationBean;
import com.pgt.utils.SearchConverToList;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderListController.class);

    @Autowired
    private ResponseBuilderFactory responseBuilderFactory;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;
    @Autowired
    private CategorySearchEngineService categorySearchEngineService;
    @Autowired
    private ESConfiguration esConfiguration;

    @RequestMapping(value = "/tenderList", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView modelAndView, @RequestParam(value = "sorts", required = false) List<ESSort> esSorts,
                            @RequestParam(value = "term", required = false) ESTerm esTerm,
                            @RequestParam(value = "esTenderListFilter", required = false) ESTenderListFilter esTenderListFilter,
                            @RequestParam(value = "paginationBean", required = false) PaginationBean paginationBean
    ) {

        if (ObjectUtils.isEmpty(esTenderListFilter)) {

        }
        LOGGER.debug("Begin to build tenderList.");
        List<Map<String, Object>> tenderList = buildTenderList(esTerm, esTenderListFilter, paginationBean, esSorts);
        modelAndView.addObject(ESConstants.TENDER_LIST_RESULT, tenderList);
        LOGGER.debug("Complete to begin tenderList.");

        LOGGER.debug("Begin to build categoryList.");
        List<Map<String, Object>> categoryList = buildCategoryList();
        modelAndView.addObject(ESConstants.ROOT_CATEGORY_LIST, categoryList);
        LOGGER.debug("Complete to begin categoryList.");

        LOGGER.debug("Begin to build paginationBean.");
        paginationBean = buildPagination(paginationBean, modelAndView);
        modelAndView.addObject(ESConstants.PAGINATION, paginationBean);
        LOGGER.debug("Complete to begin paginationBean.");
        modelAndView.setViewName("/tenderList/tenderList");
        return modelAndView;

    }

    private PaginationBean buildPagination(PaginationBean paginationBean, ModelAndView modelAndView) {
        if (ObjectUtils.isEmpty(paginationBean)) {
            paginationBean = new PaginationBean();
            paginationBean.setCurrentIndex(0);
        }
        List<Map<String, Object>> productList = (List<Map<String, Object>>) modelAndView.getModelMap().get(ESConstants.TENDER_LIST_RESULT);
        int totalAmount = productList.size();
        paginationBean.setTotalAmount(totalAmount);
        paginationBean.setCapacity(esConfiguration.getTenderListCapacity());
        return paginationBean;
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
        data.put(ESConstants.TENDER_LIST_RESULT, list);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);

    }

    private List<Map<String, Object>> buildTenderList(ESTerm esTerm, ESTenderListFilter esTenderListFilter, PaginationBean paginationBean,
                                                      List<ESSort> esSorts) {
        SearchResponse response = tenderSearchEngineService.findTenders(esTerm, esTenderListFilter, paginationBean, esSorts);
        List<Map<String, Object>> list = SearchConverToList.searchConvertToList(response);
        return list;
    }

    private List<Map<String, Object>> buildCategoryList() {
        SearchResponse searchResponse = categorySearchEngineService.findRootCategory(CategoryType.TENDER_ROOT);
        List<Map<String, Object>> list = SearchConverToList.searchConvertToList(searchResponse);
        return list;

    }


}
