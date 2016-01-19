package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.constant.Constants;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

/**
 * Created by carlwang on 1/19/16.
 */

@Service
public class TenderSearchEngineService extends AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TenderSearchEngineService.class);

    @Autowired
    private TenderService tenderService;

    @Override
    public void index() {

        try {
            BulkResponse bulkResponse;
            LOGGER.debug("Begin to tender index.");
            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Tender> tenders = tenderService.queryAllTender();
            if (!ObjectUtils.isEmpty(tenders)) {
                tenders.stream().forEach(tender -> {
                    ObjectMapper mapper = new ObjectMapper();
                    byte[] bytes = new byte[0];
                    try {
                        bytes = mapper.writeValueAsBytes(tender);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants
                                    .TENDER_INDEX_TYPE,
                            tender.getTenderId() + "")
                            .setSource(bytes);
                    bulkRequest.add(indexRequestBuilder);
                });
            }
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
        super.initialIndex();
        createTenderMapping();
    }


    private void createTenderMapping() {
        LOGGER.debug("Begin to create tender mapping.");
        createMapping(Constants.SITE_INDEX_NAME, Constants.TENDER_INDEX_TYPE, getEsConfiguration().getTenderAnalyzerFields());
        LOGGER.debug("End to create tender mapping.");
    }
}
