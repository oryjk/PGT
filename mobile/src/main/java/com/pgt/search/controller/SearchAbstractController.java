package com.pgt.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.base.constans.MobileConstants;
import com.pgt.search.bean.ESAggregation;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-1-18.
 */
public abstract class SearchAbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchAbstractController.class);
    @Autowired
    private ESSearchService esSearchService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ESConfiguration esConfiguration;

    public Map<String,Object> essearchService(EssearchBean essearchBean){

        Map<String,Object> responseMap= new HashMap<String,Object>();
        CommPaginationBean paginationBean = new CommPaginationBean();
        if (StringUtils.isEmpty(essearchBean.getCurrentIndex())) {
            paginationBean.setCurrentIndex(0);
            essearchBean.setCurrentIndex("0");
        }

        if(!StringUtils.isEmpty(essearchBean.getMobileCapacity())){
            paginationBean.setCapacity(Integer.parseInt(essearchBean.getMobileCapacity()));
        }else {
            paginationBean.setCapacity(configuration.getPlpCapacity());
        }

        ESSort esSort = null;
        ESRange esRange = null;
        ESTerm esterm = null;
        List<ESTerm> esMatches = new ArrayList<>();
        List<ESSort> sortList = new ArrayList<>();

        try {
            ESAggregation esAggregation = new ESAggregation();
            esAggregation.setAggregationName("aggr");
            esAggregation.setPropertyName("parentCategoryId");
            paginationBean.setCurrentIndex(0);

            // 设置查询条件
            if (!ObjectUtils.isEmpty(essearchBean.getCurrentIndex())) {
                paginationBean.setCurrentIndex(Long.valueOf(essearchBean.getCurrentIndex()));
            }
            essearchBean.setTerm( buildESMatch(essearchBean.getTerm(), responseMap,esMatches));
            esSort= buildESSort(essearchBean.getSortKey(), essearchBean.getSortOrder(),responseMap);
            if(!ObjectUtils.isEmpty(esSort)){
                sortList.add(esSort);
            }


            if (!StringUtils.isEmpty(essearchBean.getCategoryId())) {
                essearchBean.setCategoryId(essearchBean.getCategoryId().trim());
                LOGGER.debug("add CategoryId to responseMap{}", essearchBean.getCategoryId());
                responseMap.put("categoryId",essearchBean.getCategoryId());
            }
            esRange = buildEsRange(essearchBean, responseMap, esRange);

            SearchHits hits = null;
            SearchResponse searchResponse = null;

            // 如果分类不为空，则调用分类的查询方法
            if (!StringUtils.isEmpty(essearchBean.getCategoryId())) {
                searchResponse = esSearchService.findProductsByCategoryId(essearchBean.getCategoryId(), esMatches, esRange,
                        paginationBean, esAggregation,sortList);
            } else {
                // 查找出所有的商品普通方法
                searchResponse = esSearchService.findProducts(esterm, esMatches, esRange,sortList, paginationBean,
                        esAggregation, null);
            }

            SearchResponse categoryResponse= getCategoryHists(searchResponse);

            if(!ObjectUtils.isEmpty(categoryResponse)) {
                List categoryHists = searchConvertToList(categoryResponse);
                responseMap.put("categoryHists", categoryHists);
            }

            hits = searchResponse.getHits();
            Long total = hits.getTotalHits();
            CommPaginationBean commPaginationBean = new CommPaginationBean(configuration.getPlpCapacity(),
                    Long.parseLong(essearchBean.getCurrentIndex()), total);
            responseMap.put("commPaginationBean",commPaginationBean);
            LOGGER.debug("commPaginationBean is{}", commPaginationBean);

            SearchHit[] searchHists = hits.getHits();

            ESSort parentSort = new ESSort();
            parentSort.setPropertyName("id");
            parentSort.setSortOrder(SortOrder.ASC);
            SearchResponse parentCategoryResponse = esSearchService.findCategories(null, parentSort);

            if(!ObjectUtils.isEmpty(parentCategoryResponse)) {
                List parentCategoryList = searchConvertToList(parentCategoryResponse);
                responseMap.put("parentCategoryList", parentCategoryList);

            }

            //查找category
            SearchResponse rootSearchResponse = esSearchService.findRootCategory();
            if (!ObjectUtils.isEmpty(rootSearchResponse)) {
                SearchHits searchHits = rootSearchResponse.getHits();
                List categoryList = searchConvertToList(rootSearchResponse);
                responseMap.put("categoryList", categoryList);
            }

            // 根据搜索是否有结果，设置页面显示内容
            if (ArrayUtils.isEmpty(searchHists)) {
                responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_FAIL);
                responseMap.put(MobileConstants.MOBILE_MESSAGE,"ESSsearch.empty");
            } else {
                responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
                if(!ObjectUtils.isEmpty(searchResponse)) {
                    List products = searchConvertToList(searchResponse);
                    responseMap.put("products", products);
                }
            }
        } catch (Exception e) {
            LOGGER.debug("ESSsearch has some exception{}", e.getMessage());
            responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstants.MOBILE_MESSAGE,"ESSsearch.exception");
        }
        return responseMap;
    }

    private  SearchResponse getCategoryHists(SearchResponse searchResponse) {
        // 获取categoryId的聚合信息,出现的次数，以及id
        Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
        StringTerms gradeTerms = (StringTerms) aggMap.get("aggr");
        List<Terms.Bucket> categories = gradeTerms.getBuckets();
        SearchResponse categoryResponse=null;

        if (CollectionUtils.isEmpty(categories)) {
            List<ESTerm> esTermList = new ArrayList<>();
            for (Terms.Bucket bucket : categories) {
                ESTerm categoryTerm = new ESTerm();
                categoryTerm.setPropertyName("id");
                categoryTerm.setTermValue(bucket.getKey().toString());
                esTermList.add(categoryTerm);
            }
            // 根据分类id获取相关分类
            categoryResponse = esSearchService.findCategories(esTermList, null);
        }
        return categoryResponse;
    }

    private ESRange buildEsRange(EssearchBean essearchBean, Map<String, Object> responseMap, ESRange esRange) {
        if (!StringUtils.isEmpty(essearchBean.getPriceStart()) || !StringUtils.isEmpty(essearchBean.getPriceEnd())) {
            if (StringUtils.isEmpty(essearchBean.getPriceStart())) {
                essearchBean.setPriceStart("0");
            }
            if (StringUtils.isEmpty(essearchBean.getPriceEnd())) {
                essearchBean.setPriceEnd("100000000");
            }
            esRange = new ESRange();
            esRange.setPropertyName("salePrice");
            esRange.setFrom(Integer.parseInt(essearchBean.getPriceStart()));
            esRange.setTo(Integer.parseInt(essearchBean.getPriceEnd()));
            responseMap.put("priceStart",essearchBean.getPriceStart());
            responseMap.put("priceEnd",essearchBean.getPriceEnd());
            LOGGER.debug("add priceStart priceEnd to responseMap{}:{}", essearchBean.getPriceStart() + ":" + essearchBean.getPriceEnd());
        }
        return esRange;
    }

    private ESSort buildESSort(String sortKey, String sortOrder, Map<String,Object> responseMap) {
        ESSort esSort=null;
        if (!StringUtils.isEmpty(sortKey)) {
            esSort = new ESSort();
            esSort.setPropertyName(sortKey);
            // set default value first.
            esSort.setSortOrder(SortOrder.DESC);
            if (sortOrder != null) {
                if (sortOrder.endsWith(SortOrder.ASC.toString())) {
                    esSort.setSortOrder(SortOrder.ASC);
                    responseMap.put("sortOrder","desc");
                } else {
                    responseMap.put("sortOrder","asc");
                }
            } else {
                responseMap.put("sortOrder","desc");
            }
            responseMap.put("sortKey",sortKey);
            LOGGER.debug("add sortKey to responseMap", sortKey);
        }
        return esSort;
    }

    private String buildESMatch(String term,Map<String,Object> responseMap,
                                List<ESTerm> esMatches) {
        if (!StringUtils.isEmpty(term)) {
            term = term.trim();
            List<String> useToSearch = esConfiguration.getUseToSearch();
            if (!ObjectUtils.isEmpty(useToSearch)) {
                final String finalTerm = term;
                useToSearch.stream().forEach(s -> {
                    ESTerm esMatch = new ESTerm();
                    esMatch.setPropertyName(s);
                    esMatch.setTermValue(finalTerm);
                    esMatches.add(esMatch);
                });
            }
            responseMap.put("term",term);
            LOGGER.debug("add term to responseMap", term);
        }
        return term;
    }

    protected List  searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List message = new ArrayList();
        for(SearchHit searchit:searchHits){
            message.add(searchit.getSource());
        }
        return message;
    }

    //查找分类
    public List findCategories(){
        ESSort parentSort = new ESSort();
        parentSort.setPropertyName("id");
        parentSort.setSortOrder(SortOrder.ASC);
        SearchResponse parentCategoryResponse = esSearchService.findCategories(null, parentSort);
        if(!ObjectUtils.isEmpty(parentCategoryResponse)) {
            List parentCategoryList = searchConvertToList(parentCategoryResponse);
            return parentCategoryList;
        }
        return null;
    }
}
