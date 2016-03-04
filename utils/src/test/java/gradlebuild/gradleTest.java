package gradlebuild;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

/**
 * Created by liqiang on 16-3-1.
 */
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class gradleTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(gradleTest.class);

    private static final String SHELLSCRIPTURL = "/home/liqiang/Projects/PGT/utils/src/test/java/gradlebuild/grovvyTest.grovvy";

    @Test
    public void gradleRun() {
        //获取当前文件路径-编译后的
        GroovyShell shell = new GroovyShell();
        Object value = null;
        try {
            /**
             *  方法1 配置脚本路径
             */
            value = shell.evaluate(new File(SHELLSCRIPTURL));
            LOGGER.debug(value.toString());

            /**
             *  方法2 自己写入脚本路径
             *  GroovyScriptEngine
             */
            GroovyScriptEngine engine = new GroovyScriptEngine("");
            Object obj1 = engine.run("src/test/java/gradlebuild/grovvyTest.grovvy", "1");
            LOGGER.debug(obj1.toString());

            /**
             *  方法3
             *  GroovyClassLoader - 面向对象调用
             */
            ClassLoader parent = ClassLoader.getSystemClassLoader();
            GroovyClassLoader loader = new GroovyClassLoader(parent);
            Class gclass = loader.parseClass(new File("src/test/java/gradlebuild/grovvyTest.grovvy"));
            GroovyObject groovyObject = (GroovyObject) gclass.newInstance();
            Object obj2 = groovyObject.invokeMethod("add", new Object[] {new Integer(2), new Integer(1) });
            LOGGER.debug(obj2.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        http://ifeve.com/embedding-groovy/
     */

}
