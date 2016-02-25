package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import com.pgt.search.bean.ESAggregation;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

/**
 * Created by carlwang on 2/24/16.
 */

@Service
public class StaticResourceSearchEngineService extends AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceSearchEngineService.class);
    @Autowired
    private ESConfiguration esConfiguration;
    @Autowired
    private HelpCenterService helpCenterService;

    @Override
    public void index() {
        try {
            BulkResponse bulkResponse;
            LOGGER.debug("Begin to helpCenter index.");
            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            HelpCenter helpCenter = new HelpCenter();
            helpCenter.setSite(HelpCenterSites.ALL.toString());
            LOGGER.debug("The query helpCenter site is {}", HelpCenterSites.ALL.toString());
            List<HelpCategoryVo> helpCategoryVoList = helpCenterService.findAllHelpByQuery(helpCenter);
            if (CollectionUtils.isEmpty(helpCategoryVoList)) {
                LOGGER.debug("Can not find the help center");
                return;
            }

            helpCategoryVoList.stream().forEach(helpCategoryVo -> {

                ObjectMapper mapper = new ObjectMapper();
                String data = null;
                try {
                    data = mapper.writeValueAsString(helpCategoryVo);
                    LOGGER.debug("The help center vo is {}.", data);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.STATIC_RESOURCE_INDEX_NAME, Constants
                                .HELP_CENTER_INDEX_TYPE,
                        String.valueOf(helpCategoryVo.getCategory().getId()))
                        .setSource(data);
                bulkRequest.add(indexRequestBuilder);
            });
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                LOGGER.error("The Help Center index is failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void updateHelpCenter(List<HelpCategoryVo> helpCategoryVoList){
            helpCategoryVoList.stream().forEach(helpCategoryVo -> {
                ObjectMapper mapper = new ObjectMapper();
                        try {
                            LOGGER.debug("The helpCenter catgeory id is {}.",helpCategoryVo.getCategory().getId());
                            byte[] bytes = mapper.writeValueAsBytes(helpCategoryVo);
                            UpdateRequestBuilder updateRequestBuilder = null;
                                updateRequestBuilder = getIndexClient().prepareUpdate(Constants.STATIC_RESOURCE_INDEX_NAME, Constants
                                        .HELP_CENTER_INDEX_TYPE,String.valueOf(helpCategoryVo.getCategory().getId()))
                                        .setDoc(bytes);
                            UpdateResponse updateResponse = updateRequestBuilder.
                                    execute().actionGet(10000);

                            if (updateResponse.isCreated()) {
                                LOGGER.debug("Success to update helpCenter and category id is {}.",helpCategoryVo.getCategory().getId());
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
            );
    }


    public SearchResponse findHelpCenter(ESTerm esTerm, List<ESTerm> esMatches, ESRange esRange, List<ESSort> esSortList,
                                     PaginationBean paginationBean,
                                     ESAggregation categoryIdAggregation) {
        SearchResponse response = null;
        try {
            SearchRequestBuilder   searchRequestBuilder = buildSearchRequestBuilder(Constants.STATIC_RESOURCE_INDEX_NAME, Constants
                    .HELP_CENTER_INDEX_TYPE);
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


    @Override
    public void update(Integer id) {

    }

    private void createHotSaleMapping() {
        LOGGER.debug("Begin to create hot sale mapping.");
        createMapping(Constants.HELP_CENTER_INDEX_TYPE, Constants.HOT_PRODUCT_INDEX_TYPE, esConfiguration.getHotSaleAnalyzerFields());
        LOGGER.debug("End to create hot sale mapping.");
    }


    @Override
    public void initialIndex() {
        createIndex(Constants.STATIC_RESOURCE_INDEX_NAME, esConfiguration.isNeedIndex());
    }

    protected SearchResponse findAll() {
        return super.findAll(Constants.STATIC_RESOURCE_INDEX_NAME, Constants.HELP_CENTER_INDEX_TYPE);
    }
}
