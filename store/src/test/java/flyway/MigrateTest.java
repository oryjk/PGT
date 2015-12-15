package flyway;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by carlwang on 12/2/15.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class MigrateTest {

    @Autowired
    private ComboPooledDataSource dataSource;

    @Test
    public void migrate() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);

        flyway.setSchemas("mp"); // 设置接受flyway进行版本管理的多个数据库
        flyway.setTable("schema_version"); // 设置存放flyway metadata数据的表名
        flyway.setLocations("/db/migration"); // 设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径
        flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码

        flyway.migrate();
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
