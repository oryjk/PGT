package flyway;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by carlwang on 12/2/15.
 */

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class MigrateTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateTest.class);

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
        //根据文件/db/migration/*.sql生成表和数据
        flyway.migrate();
    }

    /**
     * 备份数据库
     */
    @Test
    public void backUpDatabase(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("mysqldump -u root -proot mp");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line=bufferedReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            process.waitFor();
            FileWriter fw = new FileWriter("/home/liqiang/Desktop/1.sql");
            fw.write(sb.toString());
            fw.close();
            process.destroy();
            LOGGER.debug("backup database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 还原数据库
     */
    @Test
    public void recoverDatabase(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("mysqldump -s root -proot mp < /home/liqiang/Desktop/1.sql");
            process.waitFor();
            LOGGER.debug("recover database");
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * 清空数据库
     */
    @Test
    public void cleanDatabase(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setSchemas("mp"); // 设置接受flyway进行版本管理的多个数据库
        flyway.setTable("schema_version"); // 设置存放flyway metadata数据的表名
        flyway.setLocations("/db/migration"); // 设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径
        flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码
        flyway.clean();
        LOGGER.debug("clean database");
    }


    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
