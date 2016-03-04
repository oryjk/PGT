package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.constant.ESConstants;
import com.pgt.home.bean.HomeTender;
import com.pgt.search.bean.*;
import com.pgt.tender.bean.ESTender;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by carlwang on 1/19/16.
 */

@Service
public class TenderSearchEngineService extends AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderSearchEngineService.class);

    @Autowired
    private TenderService tenderService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ESConfiguration esConfiguration;

    @Override
    public void index() {

        tenderIndex();
        homeTenderIndex();
    }

    private void homeTenderIndex() {
        BulkResponse bulkResponse;
        LOGGER.debug("Begin to home tender index.");

        BulkRequestBuilder bulkRequest = null;
        try {
            bulkRequest = getIndexClient().prepareBulk();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Category> categories = categoryService.queryRootTenderCategories();
        if (CollectionUtils.isEmpty(categories)) {
            LOGGER.debug("The root tender category is empty.");
            return;
        }

        final BulkRequestBuilder finalBulkRequest = bulkRequest;
        categories.stream().forEach(category1 -> {
            List<Category> categoryList = category1.getChildren();
            HomeTender homeTender = new HomeTender();
            homeTender.setCategory(category1);
            indexHomeTenders(homeTender, categoryList, finalBulkRequest);
        });
        bulkResponse = bulkRequest.execute().actionGet(100000);
        if (bulkResponse.hasFailures()) {
            LOGGER.error("The tender index is failed.");
        }

    }

    private void indexHomeTenders(HomeTender homeTender, List<Category> categoryList, BulkRequestBuilder bulkRequest) {
        if (CollectionUtils.isEmpty(categoryList)) {
            LOGGER.debug("The category list is empty.");
            return;
        }
        try {

            categoryList.stream().forEach(category -> {
                Integer categoryId = category.getId();
                Tender tender = new Tender();
                tender.setCategoryId(categoryId);
                tender.setCategoryHot(true);
                List<Tender> tenders = tenderService.queryTenders(tender);
                homeTender.getTenderList().addAll(tenders);
            });
            try {
                Client client = getIndexClient();
                ObjectMapper mapper = new ObjectMapper();
                String data = mapper.writeValueAsString(homeTender);
                IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.P2P_INDEX_NAME, Constants
                                .HOME_TENDER_INDEX_TYPE,
                        homeTender.getCategory().getId() + "")
                        .setSource(data);
                bulkRequest.add(indexRequestBuilder);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void tenderIndex() {
        try {
            BulkResponse bulkResponse;
            LOGGER.debug("Begin to tender index.");
            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Tender> tenders = tenderService.queryAllTender();
            if (!ObjectUtils.isEmpty(tenders)) {
                tenders.stream().forEach(tender -> {
                    Category category = tender.getCategory();
                    Category rootCategory = null;
                    if (!ObjectUtils.isEmpty(category)) {
                        rootCategory = category.getParent();
                    }
                    ESTender esTender = new ESTender(tender, category, rootCategory);
                    ObjectMapper mapper = new ObjectMapper();
                    String data = null;
                    try {
                        data = mapper.writeValueAsString(esTender);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.P2P_INDEX_NAME, Constants
                                    .TENDER_INDEX_TYPE,
                            tender.getTenderId() + "")
                            .setSource(data);
                    bulkRequest.add(indexRequestBuilder);
                });
            }
            if (bulkRequest.numberOfActions() > 0) {
                bulkResponse = bulkRequest.execute().actionGet(100000);
                if (bulkResponse.hasFailures()) {
                    LOGGER.error("The tender index is failed.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SearchResponse findTenderById(Integer tenderId) {
        SearchResponse response = null;
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("the tender id is empty.");
            return response;
        }
        ESTerm esTerm = new ESTerm();
        esTerm.setTermValue(String.valueOf(tenderId));
        esTerm.setPropertyName(Constants.TENDER_ID);
        response = findTender(esTerm, null, null, null, null, null, null);
        return response;
    }


    public SearchResponse findTender(ESTerm esTerm, List<ESTerm> esMatches, ESRange esRange, List<ESSort> esSortList,
                                     PaginationBean paginationBean,
                                     ESAggregation categoryIdAggregation, String indexType) {
        SearchResponse response = null;
        try {
            SearchRequestBuilder searchRequestBuilder = null;
            if (ObjectUtils.isEmpty(indexType)) {
                searchRequestBuilder = initialSearchRequestBuilder(Constants.P2P_INDEX_NAME, Constants.TENDER_INDEX_TYPE);
            } else {
                searchRequestBuilder = initialSearchRequestBuilder(Constants.P2P_INDEX_NAME, indexType);
            }
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            buildQueryBuilder(esTerm, esMatches, esRange, esSortList, paginationBean, categoryIdAggregation, searchRequestBuilder, qb);
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public SearchResponse findCategoryHotTender() {

        return findTender(null, null, null, null, null, null, Constants.HOME_TENDER_INDEX_TYPE);
    }

    public SearchResponse findSiteHotTender() {
        ESTerm esTerm = new ESTerm();
        List<ESTerm> matches = Lists.newArrayList(esTerm);
        esTerm.setPropertyName(Constants.SITE_HOT_PROPERTY);
        esTerm.setTermValue(String.valueOf(true));

        return findTender(null, matches, null, null, null, null, Constants.TENDER_INDEX_TYPE);
    }

    public void updateTender(Tender tender) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            LOGGER.debug("Tender id is {}.", tender.getTenderId());
            Category category = tender.getCategory();
            Category rootCategory = null;
            if (!ObjectUtils.isEmpty(category)) {
                rootCategory = category.getParent();
            }
            ESTender esTender = new ESTender(tender, category, rootCategory);
            String data = null;
            try {
                data = mapper.writeValueAsString(esTender);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            byte[] bytes = mapper.writeValueAsBytes(tender);

            UpdateRequestBuilder updateRequestBuilder =
                    getIndexClient().prepareUpdate(Constants.P2P_INDEX_NAME, Constants.TENDER_INDEX_TYPE, tender.getTenderId() + "")
                            .setDoc(data);
            UpdateResponse updateResponse = updateRequestBuilder.execute().actionGet(10000);
            if (updateResponse.isCreated()) {
                LOGGER.debug("Success to update tender.");
                return;
            }
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        if (tender.getCategoryHot()) {
            //TODO
        }
    }

    public IndexResponse createTenderIndex(Tender tender) {
        ObjectMapper mapper = new ObjectMapper();
        IndexResponse response = null;
        try {
            LOGGER.debug("Tender id is {}.", tender.getTenderId());
            Category category = tender.getCategory();
            Category rootCategory = null;
            if (!ObjectUtils.isEmpty(category)) {
                rootCategory = category.getParent();
            }
            ESTender esTender = new ESTender(tender, category, rootCategory);
            String data = null;
            try {
                data = mapper.writeValueAsString(esTender);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            LOGGER.debug("Tender id is {}.", tender.getTenderId());
            IndexRequestBuilder indexRequestBuilder =
                    getIndexClient().prepareIndex(Constants.P2P_INDEX_NAME, Constants.TENDER_INDEX_TYPE, tender.getTenderId() + "")
                            .setSource(data);
            response = indexRequestBuilder.execute().actionGet(100000);
            if (response.isCreated()) {
                LOGGER.debug("success to create tender.");
                return response;
            }

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    public void update(Integer id) {

    }

    @Override
    public void initialIndex() {
        createIndex(esConfiguration.getIndexName(), esConfiguration.isClearIndex());
        createMapping(esConfiguration.getIndexName(), Constants.TENDER_INDEX_TYPE, getEsConfiguration().getTenderAnalyzerFields());
    }

    @Override
    protected void createMapping(String indexName, String indexType, List<String> analyseFields) {
        LOGGER.debug("Begin to create tender mapping.");
        super.createMapping(indexName, indexType, analyseFields);
        LOGGER.debug("End to create tender mapping.");
    }

    @Override
    protected void buildSort(SearchRequestBuilder searchRequestBuilder, List<ESSort> esSorts) {
        if (CollectionUtils.isEmpty(esSorts)) {
            LOGGER.debug("sorts is empty.");
            return;
        }
        esSorts.stream().forEach(esSort -> {
            FieldSortBuilder sortBuilder = new FieldSortBuilder(esSort.getPropertyName());
            sortBuilder.order(esSort.getSortOrder());
            sortBuilder.unmappedType("long");
            searchRequestBuilder.addSort(sortBuilder);
        });
    }

    @Override
    protected void buildFilter(BoolQueryBuilder boolQueryBuilder, ESFilter esFilter) {
        if (ObjectUtils.isEmpty(esFilter)) {
            LOGGER.debug("The esFiter is empty.");
            return;
        }
        ESTenderListFilter esTenderListFilter = (ESTenderListFilter) esFilter;
        if (!StringUtils.isBlank(esTenderListFilter.getCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESConstants.PARENT_CATEGORY_ID, esTenderListFilter.getCategoryId()));
        }
        if (!StringUtils.isBlank(esTenderListFilter.getRootCategoryId())) {
            boolQueryBuilder.filter(QueryBuilders.termQuery(ESConstants.ROOT_CATEGORY_ID, esTenderListFilter.getCategoryId()));
        }
        if (esTenderListFilter.isAll()) {
            LOGGER.debug("The filter is all.");
            return;
        }
        if (esTenderListFilter.isBeginInMinute()) {
            LOGGER.debug("The filter is isBeginInMinute.");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ESConstants.PUBLISH_DATE).gt(new Date().getTime()));
            return;
        }
        if (esTenderListFilter.isUnderway()) {
            LOGGER.debug("The filter is isUnderway.");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ESConstants.PUBLISH_DATE).lt(new Date().getTime()));
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ESConstants.DUE_DATE).gt(new Date().getTime()));
            return;
        }
        if (esTenderListFilter.isEnded()) {
            LOGGER.debug("The filter is isEnded.");
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ESConstants.DUE_DATE).lt(new Date().getTime()));
            return;
        }

    }

    public SearchResponse findTenders(String keyword, ESTenderListFilter esTenderListFilter, PaginationBean paginationBean, List<ESSort> esSorts) {

        SearchResponse response = null;
        if (ObjectUtils.isEmpty(paginationBean)) {
            paginationBean = new PaginationBean();
            paginationBean.setCapacity(esConfiguration.getTenderListCapacity());
            paginationBean.setAsc(true);
            paginationBean.setCurrentIndex(0);
        }
        try {
            SearchRequestBuilder searchRequestBuilder;
            searchRequestBuilder = initialSearchRequestBuilder(Constants.P2P_INDEX_NAME, Constants.TENDER_INDEX_TYPE);
            List<ESTerm> esTerms = buildESTerms(keyword, esConfiguration.getTenderSearchProperties());
            buildSearchRequestBuilder(esTerms, esTenderListFilter, paginationBean, esSorts, searchRequestBuilder);
            LOGGER.debug("The query is {}.", searchRequestBuilder.toString());
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
