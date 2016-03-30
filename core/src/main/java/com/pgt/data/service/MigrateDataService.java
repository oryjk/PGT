package com.pgt.data.service;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by carlwang on 1/18/16.
 */

@Service
public class MigrateDataService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateDataService.class);
    @Autowired
    private ComboPooledDataSource dataSource;

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
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }

    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
