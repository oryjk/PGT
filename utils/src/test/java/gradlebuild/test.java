package gradlebuild;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liqiang on 16-3-1.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class test {
    private static final Logger LOGGER = LoggerFactory.getLogger(test.class);

    @Test
    public void gradleRun() {
        LOGGER.debug("123");
    }

}
