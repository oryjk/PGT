package com.pgt.data;

import com.pgt.configuration.ESConfiguration;
import com.pgt.data.service.MigrateDataService;
import com.pgt.search.service.ESSearchService;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by carlwang on 12/8/15.
 */


public class MigrateDataListener implements ApplicationListener<ContextRefreshedEvent> {


    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateDataListener.class);

    @Autowired
    private MigrateDataService migrateDateService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            try {

                migrateDateService.migrate();
                createIndex();
            } catch (Exception e) {
                LOGGER.error("Some error occured when migration data.The error message is {}.", e.getMessage());
            }
        }
    }


    public void createIndex() {
        esSearchService.initialIndex(esConfiguration.isClearIndex());
        if (esConfiguration.isNeedIndex()) {
            try {
                BulkResponse categoryResponse = esSearchService.categoryIndex();
                if (categoryResponse.hasFailures()) {
                    LOGGER.error("Category index error.");
                }
                BulkResponse hotResponse = esSearchService.hotSaleIndex();
                if (hotResponse.hasFailures()) {
                    LOGGER.error("Hot product index error.");
                }
                BulkResponse responses = esSearchService.productsIndex();
                if (responses.hasFailures()) {
                    LOGGER.error("Product index error.");
                }

            } catch (Exception e) {
                LOGGER.error("Can not index the data in ES.");
            }
        }

    }

    public ESConfiguration getEsConfiguration() {
        return esConfiguration;
    }

    public void setEsConfiguration(ESConfiguration esConfiguration) {
        this.esConfiguration = esConfiguration;
    }

    private ESConfiguration esConfiguration;

    public ESSearchService getEsSearchService() {
        return esSearchService;
    }

    public void setEsSearchService(ESSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }

    private ESSearchService esSearchService;

}
