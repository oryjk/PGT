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

import java.util.Calendar;
import java.util.Date;

/**
 * Created by carlwang on 2/18/16.
 */

@Ignore
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
    public void updateUserCache() {

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
        ssoService.cacheUser(user, b2cOrder, p2pOrder);
        user.setPhoneNumber("10aaaaaaa086");
        user.setEmail("oryaaaaajk@qq.com");
        boolean flag = ssoService.updateCacheUser(user, b2cOrder, p2pOrder);
        Assert.assertTrue(flag);
    }

    @Test
    public void deleteCacheUser() {
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
        ssoService.cacheUser(user, b2cOrder, p2pOrder);
        boolean flag = ssoService.deleteCacheUser(1);
        Assert.assertTrue(flag);
    }

    @Test
    public void isUserExpire() {
        boolean flag = ssoService.isUserExpire(1);
        Assert.assertTrue(flag);
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, Calendar.HOUR - 1);
        cal.set(Calendar.MONTH, Calendar.MONTH - 1);
        b2cOrder.setUpdateDate(cal.getTime());
        Order p2pOrder = new Order();
        ssoService.cacheUser(user, b2cOrder, p2pOrder);
        flag = ssoService.isUserExpire(1);
        Assert.assertTrue(flag);


        user.setId(1L);
        user.setCount(1000);
        user.setPhoneNumber("10086");
        user.setCreateDate(new Date());
        user.setEmail("oryjk@qq.com");
        b2cOrder.setStatus(1);
        b2cOrder.setUserId(1);
        b2cOrder.setCreationDate(new Date());
        b2cOrder.setUpdateDate(new Date());
        ssoService.cacheUser(user, b2cOrder, p2pOrder);
        flag = ssoService.isUserExpire(1);
        System.out.print("flag:"+flag);
        Assert.assertFalse(flag);
    }

}
