package com.pgt.data;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.JsonUtil;
import org.elasticsearch.action.bulk.BulkResponse;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by carlwang on 12/8/15.
 */


public class MigrateData implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private Configuration configuration;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateData.class);

    public ComboPooledDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private ComboPooledDataSource dataSource;

    @Autowired
    private JsonUtil jsonUtil;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            try{
                migrate();
                createIndex();
                jsonUtil.categoriesCreate(configuration.getInitialDataPath());
            }catch(Exception e){
                LOGGER.error("Some error occured when migration data.The error message is {}.",e.getMessage());
            }
        }
    }

    public void migrate() {
        try {
            Flyway flyway = new Flyway();
            flyway.setDataSource(dataSource);

            flyway.setSchemas("mp"); // 设置接受flyway进行版本管理的多个数据库
            flyway.setTable("schema_version"); // 设置存放flyway metadata数据的表名
            flyway.setLocations("/db/migration"); // 设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径
            flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码

            flyway.migrate();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
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
                BulkResponse hotResponse = esSearchService.hotProductIndex();
                if (hotResponse.hasFailures()) {
                    LOGGER.error("Hot product index error.");
                }
                BulkResponse responses = esSearchService.productIndex();
                if (responses.hasFailures()) {
                    LOGGER.error("Product index error.");
                }

            } catch (Exception e) {
                LOGGER.error("Can not index the data in ES.");
            }
        }

    }


}
