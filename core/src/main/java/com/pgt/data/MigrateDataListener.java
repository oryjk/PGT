package com.pgt.data;

import com.pgt.configuration.ESConfiguration;
import com.pgt.data.index.service.AllIndexService;
import com.pgt.data.service.MigrateDataService;
import com.pgt.search.service.AbstractSearchEngineService;
import com.pgt.search.service.ESSearchService;
import com.pgt.search.service.StaticResourceSearchEngineService;
import com.pgt.search.service.TenderSearchEngineService;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.bulk.BulkResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 12/8/15.
 */

@Service
@Scope("singleton")
public class MigrateDataListener implements ApplicationListener<ContextRefreshedEvent> {


    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateDataListener.class);

    @Autowired
    private MigrateDataService migrateDateService;

    @Autowired
    private AllIndexService allIndexService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            try {

                migrateDateService.migrate();
                createIndex();
            } catch (Exception e) {
                LOGGER.error("Some error occured when migration data.The error message is {}.", e.getMessage());
                throw e;
            }
        }
    }


    public void createIndex() {

        allIndexService.createIndex();
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
