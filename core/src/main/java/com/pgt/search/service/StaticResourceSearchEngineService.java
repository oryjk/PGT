package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.bean.HelpCenterSites;
import com.pgt.help.service.HelpCenterService;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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
            LOGGER.debug("Begin to tender index.");
            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();

            HelpCenter helpCenter = new HelpCenter();
            helpCenter.setSite(HelpCenterSites.B2C_STORE.toString());
            LOGGER.debug("The query helpCenter site is {}", HelpCenterSites.B2C_STORE.toString());
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
                LOGGER.error("The tender index is failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Integer id) {

    }

    @Override
    public void initialIndex() {
        createIndex(Constants.STATIC_RESOURCE_INDEX_NAME, esConfiguration.isNeedIndex());
    }

    protected SearchResponse findAll() {
        return super.findAll(Constants.STATIC_RESOURCE_INDEX_NAME, Constants.HELP_CENTER_INDEX_TYPE);
    }
}
