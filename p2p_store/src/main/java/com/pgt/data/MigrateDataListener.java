package com.pgt.data;

import com.pgt.configuration.ESConfiguration;
import com.pgt.data.service.MigrateDataService;
import com.pgt.search.service.ESSearchService;
import com.pgt.search.service.TenderSearchEngineService;
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
    private ESConfiguration esConfiguration;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;
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
        tenderSearchEngineService.initialIndex();
        if (esConfiguration.isNeedIndex()) {
            try {
                tenderSearchEngineService.index();


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

    public TenderSearchEngineService getTenderSearchEngineService() {
        return tenderSearchEngineService;
    }

    public void setTenderSearchEngineService(TenderSearchEngineService tenderSearchEngineService) {
        this.tenderSearchEngineService = tenderSearchEngineService;
    }
}
