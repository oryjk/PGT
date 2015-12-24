package com.pgt.search.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.common.BreadBuilder;
import com.pgt.common.bean.BreadCrumb;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.search.bean.ESAggregation;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ddjunshi 2015年12月1日
 */
@RestController
@RequestMapping("/essearch")
public class ESSearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ESSearchController.class);

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BreadBuilder breadBuilder;

    @Autowired
    private ESConfiguration esConfiguration;


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(value = "term", required = false) String term,
                            @RequestParam(value = "rootCategoryId", required = false) String rootCategoryId,
                            @RequestParam(value = "parentCategoryId", required = false) String parentCategoryId,
                            @RequestParam(value = "sortKey", required = false) String sortKey,
                            @RequestParam(value = "sortOrder", required = false) String sortOrder,
                            @RequestParam(value = "priceStart", required = false) String priceStart,
                            @RequestParam(value = "priceEnd", required = false) String priceEnd,
                            @RequestParam(value = "currentIndex", required = false) String currentIndex, ModelAndView modelAndView) {

        PaginationBean paginationBean = new PaginationBean();

        StringBuilder message = new StringBuilder();

        if (StringUtils.isEmpty(currentIndex)) {
            paginationBean.setCurrentIndex(0);
            currentIndex = "0";
        }
        paginationBean.setCapacity(configuration.getPlpCapacity());

        ESSort esSort = null;
        ESRange esRange = null;
        ESTerm esterm = null;
        List<ESTerm> esMatches = new ArrayList<>();
        String result[] = new String[2]; // 是否搜索到结果

        try {
            ESAggregation esAggregation = new ESAggregation();
            modelAndView.setViewName("/searchPage/searchPage");
            esAggregation.setAggregationName("aggr");
            esAggregation.setPropertyName("parentCategoryId");
            paginationBean.setCurrentIndex(0);

            // 设置查询条件
            if (!ObjectUtils.isEmpty(currentIndex)) {
                paginationBean.setCurrentIndex(Long.valueOf(currentIndex));
            }

            term = buildESMatch(term, modelAndView, message, esMatches);


            buildESSort(sortKey, sortOrder, modelAndView);

            if (!StringUtils.isEmpty(rootCategoryId)) {
                rootCategoryId = rootCategoryId.trim();
                esterm = new ESTerm();
                esterm.setPropertyName("rootCategoryId");
                esterm.setTermValue(rootCategoryId);
                Category category = categoryService.queryCategory(Integer.valueOf(rootCategoryId));
                modelAndView.addObject("rootCategory", category);
                LOGGER.debug("add rootCategoryId to modelAndView", rootCategoryId);
                message.append("分类:" + category.getName());
            }

            if (!StringUtils.isEmpty(parentCategoryId)) {
                parentCategoryId = parentCategoryId.trim();
                esterm = new ESTerm();
                esterm.setPropertyName("parentCategoryId");
                esterm.setTermValue(parentCategoryId);
                Category category = categoryService.queryCategory(Integer.valueOf(parentCategoryId));
                modelAndView.addObject("parentCategory", category);
                LOGGER.debug("add parentCategoryId to modelAndView", rootCategoryId);
                message.append("分类" + category.getName() + ":");
            }


            if (!StringUtils.isEmpty(priceStart) || !StringUtils.isEmpty(priceEnd)) {
                if (StringUtils.isEmpty(priceStart)) {
                    priceStart = "0";
                }
                if (StringUtils.isEmpty(priceEnd)) {
                    priceEnd = "100000000";
                }

                priceStart = priceStart.trim();
                priceEnd = priceEnd.trim();
                esRange = new ESRange();
                esRange.setPropertyName("salePrice");
                esRange.setFrom(Integer.parseInt(priceStart));
                esRange.setTo(Integer.parseInt(priceEnd));
                modelAndView.addObject("priceStart", priceStart);
                modelAndView.addObject("priceEnd", priceEnd);
                LOGGER.debug("add priceStart priceEnd to modelAndView", priceStart + ":" + priceEnd);
                message.append("指定价格");
            }


            SearchHits hits = null;

            SearchResponse searchResponse = null;

            // 如果分类不为空，则调用分类的查询方法
            if (!StringUtils.isEmpty(parentCategoryId)) {
                searchResponse = esSearchService.findProductsByCategoryId(parentCategoryId, esMatches, esRange,
                        paginationBean, esAggregation);

            } else if (!StringUtils.isEmpty(rootCategoryId)) {
                searchResponse = esSearchService.findProductsByCategoryId(rootCategoryId, esMatches, esRange,
                        paginationBean, esAggregation);

            } else {

                // 查找出所有的商品普通方法
                searchResponse = esSearchService.findProducts(esterm, esMatches, esRange, null, paginationBean,
                        esAggregation, null);

            }
            // 获取categoryId的聚合信息,出现的次数，以及id
            Map<String, Aggregation> aggMap = searchResponse.getAggregations().asMap();
            StringTerms gradeTerms = (StringTerms) aggMap.get("aggr");
            List<Bucket> categories = gradeTerms.getBuckets();

            if (CollectionUtils.isEmpty(categories)) {

                List<ESTerm> esTermList = new ArrayList<>();

                for (Bucket bucket : categories) {
                    ESTerm categoryTerm = new ESTerm();
                    categoryTerm.setPropertyName("id");
                    categoryTerm.setTermValue(bucket.getKey().toString());
                    esTermList.add(categoryTerm);
                }
                // 根据分类id获取相关分类
                SearchResponse categoryResponse = esSearchService.findCategories(esTermList, null);
                SearchHits cateHits = null;
                cateHits = categoryResponse.getHits();
                SearchHit[] categoryHists = cateHits.getHits();
                modelAndView.addObject("categoryHists", categoryHists);

            }

            ESSort parentSort = new ESSort();
            parentSort.setPropertyName("id");
            parentSort.setSortOrder(SortOrder.ASC);

            SearchResponse parentCategoryResponse = esSearchService.findCategories(null, parentSort);
            modelAndView.addObject("parentCategoryList", parentCategoryResponse.getHits().getHits());

            hits = searchResponse.getHits();

            Long total = hits.getTotalHits();
            CommPaginationBean commPaginationBean = new CommPaginationBean(configuration.getPlpCapacity(),
                    Long.parseLong(currentIndex), total);

            modelAndView.addObject("commPaginationBean", commPaginationBean);

            SearchHit[] searchHists = hits.getHits();

            //查找category
            SearchResponse rootSearchResponse = esSearchService.findRootCategory();
            if (!ObjectUtils.isEmpty(rootSearchResponse)) {
                SearchHits searchHits = rootSearchResponse.getHits();
                if (!ObjectUtils.isEmpty(searchHits)) {
                    SearchHit[] categoryArray = searchHits.getHits();
                    modelAndView.addObject("categoryArray", categoryArray);
                }
            }

            // 根据搜索是否有结果，设置页面显示内容
            if (searchHists.length == 0) {
                result[0] = "none";
                result[1] = "block";
            } else {

                result[0] = "block";
                result[1] = "none";

                Category rootCategory = (Category) modelAndView.getModel().get("rootCategory");
                Category category = (Category) modelAndView.getModel().get("parentCategory");
                List<BreadCrumb> breadCrumb = breadBuilder.buildSearchPageBreadCrumb(
                        rootCategory == null ? null : rootCategory.getName(),
                        category == null ? null : category.getName(), term);

                modelAndView.addObject("breadCrumb", breadCrumb);
                modelAndView.addObject("searchHists", searchHists);
            }

        } catch (Exception e) {
            LOGGER.debug("ESSsarch has some exception", e.getMessage());
            result[0] = "none";
            result[1] = "block";

        } finally {
            modelAndView.addObject("result", result);
            modelAndView.addObject("message", message.toString());
        }
        return modelAndView;
    }

    private void buildESSort(@RequestParam(value = "sortKey", required = false) String sortKey,
                             @RequestParam(value = "sortOrder", required = false) String sortOrder, ModelAndView modelAndView) {
        ESSort esSort;
        if (!StringUtils.isEmpty(sortKey)) {
            esSort = new ESSort();
            esSort.setPropertyName(sortKey);
            // set default value first.
            esSort.setSortOrder(SortOrder.DESC);

            if (sortOrder != null) {

                if (sortOrder.endsWith(SortOrder.ASC.toString())) {
                    esSort.setSortOrder(SortOrder.ASC);
                    modelAndView.addObject("sortOrder", "desc");
                } else {
                    modelAndView.addObject("sortOrder", "asc");
                }

            } else {
                modelAndView.addObject("sortOrder", "asc");
            }


            modelAndView.addObject("sortKey", sortKey);
            LOGGER.debug("add sortKey to modelAndView", sortKey);


        }
    }

    private String buildESMatch(@RequestParam(value = "term", required = false) String term, ModelAndView modelAndView, StringBuilder message,
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

            modelAndView.addObject("term", term);
            LOGGER.debug("add term to modelAndView", term);
            message.append(term + ":");
        }
        return term;
    }


    public BreadBuilder getBreadBuilder() {
        return breadBuilder;
    }

    public void setBreadBuilder(BreadBuilder breadBuilder) {
        this.breadBuilder = breadBuilder;
    }
}
