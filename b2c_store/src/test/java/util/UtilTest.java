package util;

import com.pgt.utils.ErrorMsgUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by carlwang on 11/19/15.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-core-config.xml", "classpath:spring-mvc-config.xml"}) // 加载配置
public class UtilTest {

    @Test
    public void getMsgTest() {
        System.out.println(ErrorMsgUtil.getMsg("Error.login", null, null));
    }
}
