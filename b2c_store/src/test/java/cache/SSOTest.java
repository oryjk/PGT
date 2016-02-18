package cache;

import com.pgt.cart.bean.Order;
import com.pgt.sso.service.SSOService;
import com.pgt.user.bean.User;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by carlwang on 2/18/16.
 */

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class SSOTest {

    @Autowired
    private SSOService ssoService;

    @Test
    public void createUserCache() {
        User user = new User();
        user.setId(1L);
        user.setCount(1000);
        user.setPhoneNumber("10086");
        user.setCreateDate(new Date());
        user.setEmail("oryjk@qq.com");
        Order b2cOrder = new Order();
        b2cOrder.setStatus(1);
        b2cOrder.setUserId(1);
        b2cOrder.setCreationDate(new Date());
        Order p2pOrder = new Order();
        p2pOrder.setCreationDate(new Date());

        boolean flag = ssoService.cacheUser(user, b2cOrder, p2pOrder);
        Assert.assertTrue(flag);
    }

    @Test
    public void updateUserCache(){

        User user = new User();
        user.setId(1L);
        user.setCount(1000);
        user.setPhoneNumber("10aaaaaaa086");
        user.setCreateDate(new Date());
        user.setEmail("oryaaaaajk@qq.com");
        Order b2cOrder = new Order();
        b2cOrder.setStatus(1);
        b2cOrder.setUserId(1);
        b2cOrder.setCreationDate(new Date());
        Order p2pOrder = new Order();
        p2pOrder.setCreationDate(new Date());

        boolean flag = ssoService.updateCacheUser(user, b2cOrder, p2pOrder);
        Assert.assertTrue(flag);
    }


}
