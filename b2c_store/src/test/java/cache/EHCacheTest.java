package cache;

import net.sf.ehcache.CacheManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by carlwang on 12/16/15.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class EHCacheTest {

    @Test
    public void writeCacheTest() {

        CacheManager cacheManager = CacheManager.create();
        Assert.notNull(cacheManager);

    }
}
