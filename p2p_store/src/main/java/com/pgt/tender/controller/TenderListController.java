package com.pgt.tender.controller;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.category.bean.CategoryType;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.ESConstants;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTenderListFilter;
import com.pgt.search.service.CategorySearchEngineService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.util.TenderListUtil;
import com.pgt.utils.PaginationBean;
import com.pgt.utils.PaginationUtils;
import com.pgt.utils.SearchConverToList;
import org.elasticsearch.action.search.SearchResponse;
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
    @Autowired
    private PaginationUtils paginationUtils;

    @RequestMapping(value = "/tenderList", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView modelAndView, @RequestParam(value = "sort", required = false) Integer sort,
                            @RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "tenderFilter", required = false) Integer tenderFilter,
                            @RequestParam(value = "cid", required = false) String categoryId,
                            @RequestParam(value = "page", required = false) Integer page
    ) {

        ESTenderListFilter esTenderListFilter = new ESTenderListFilter(categoryId);
        TenderListUtil.buildESTenderListFilter(tenderFilter, esTenderListFilter);
        if (ObjectUtils.isEmpty(page)) {
            page = 1;
        }
        Integer currentIndex = page - 1;
        CommPaginationBean paginationBean = paginationUtils.createPagination(currentIndex);
        LOGGER.debug("Begin to build tenderList.");
        ESSort esSort = TenderListUtil.getESSort(sort);
        List<ESSort> esSorts = null;
        if (!ObjectUtils.isEmpty(esSort)) {
            esSorts = new ArrayList<>();
            esSorts.add(esSort);
        }
        List<Map<String, Object>> tenderList = buildTenderList(keyword, esTenderListFilter, paginationBean, esSorts);
        modelAndView.addObject(ESConstants.TENDER_LIST_RESULT, tenderList);
        LOGGER.debug("Complete to begin tenderList.");

        LOGGER.debug("Begin to build categoryList.");
        List<Map<String, Object>> categoryList = buildCategoryList();
        modelAndView.addObject(ESConstants.ROOT_CATEGORY_LIST, categoryList);
        LOGGER.debug("Complete to begin categoryList.");

        LOGGER.debug("Begin to build paginationBean.");
        paginationBean = buildResultPagination(paginationBean);
        modelAndView.addObject(ESConstants.PAGINATION, paginationBean);
        LOGGER.debug("Complete to begin paginationBean.");
        modelAndView.setViewName("/tenderList/tenderList");
        return modelAndView;

    }


    @RequestMapping("/ajaxtenderList")
    public ResponseEntity ajaxGet(@RequestParam(value = "sort", required = false) Integer sort,
                                  @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "tenderFilter", required = false) Integer tenderFilter,
                                  @RequestParam(value = "cid", required = false) String categoryId,
                                  @RequestParam(value = "page", required = false) Integer page
    ) {
        ResponseBuilder rb = responseBuilderFactory.buildResponseBean().setSuccess(true);
        Map<String, Object> data = new HashMap<>();
        rb.setData(data);
        ESTenderListFilter esTenderListFilter = new ESTenderListFilter(categoryId);
        TenderListUtil.buildESTenderListFilter(tenderFilter, esTenderListFilter);
        if (ObjectUtils.isEmpty(page)) {
            page = 1;
        }
        Integer currentIndex = page - 1;
        CommPaginationBean paginationBean = paginationUtils.createPagination(currentIndex);
        LOGGER.debug("Begin to build tenderList.");
        ESSort esSort = TenderListUtil.getESSort(sort);
        List<ESSort> esSorts = null;
        if (!ObjectUtils.isEmpty(esSort)) {
            esSorts = new ArrayList<>();
            esSorts.add(esSort);
        }
        List<Map<String, Object>> tenderList = buildTenderList(keyword, esTenderListFilter, paginationBean, esSorts);
        data.put(ESConstants.TENDER_LIST_RESULT, tenderList);
        LOGGER.debug("Complete to begin tenderList.");

        LOGGER.debug("Begin to build categoryList.");
        List<Map<String, Object>> categoryList = buildCategoryList();
        data.put(ESConstants.ROOT_CATEGORY_LIST, categoryList);
        LOGGER.debug("Complete to begin categoryList.");

        LOGGER.debug("Begin to build paginationBean.");
        paginationBean = buildResultPagination(paginationBean);
        data.put(ESConstants.PAGINATION, paginationBean);
        LOGGER.debug("Complete to begin paginationBean.");
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);

    }

    private List<Map<String, Object>> buildTenderList(String keyword, ESTenderListFilter esTenderListFilter, PaginationBean paginationBean,
                                                      List<ESSort> esSorts) {
        SearchResponse response = tenderSearchEngineService.findTenders(keyword, esTenderListFilter, paginationBean, esSorts);
        List<Map<String, Object>> list = SearchConverToList.searchConvertToList(response);
        paginationBean.setTotalAmount(response.getHits().getTotalHits());
        return list;
    }

    private List<Map<String, Object>> buildCategoryList() {
        SearchResponse searchResponse = categorySearchEngineService.findRootCategory(CategoryType.TENDER_ROOT);
        List<Map<String, Object>> list = SearchConverToList.searchConvertToList(searchResponse);
        return list;

    }

    private CommPaginationBean buildResultPagination(CommPaginationBean paginationBean) {
        CommPaginationBean commPaginationBean = new CommPaginationBean(esConfiguration.getTenderListCapacity(), paginationBean.getCurrentIndex(),
                paginationBean.getTotalAmount());
        return commPaginationBean;
    }


}
