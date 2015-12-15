package user;

import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by carlwang on 10/22/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class UserTest {


    @Autowired
    private UserService userService;

    @Test
    public void authorize() {
        String username = "12345678901";
        String password = "123456";
//        User user = userService.authorize(username);
//        Assert.assertNotNull(user);
//        String encryptedPassword = DigestUtils.md5Hex(password + user.getSalt());
//        Assert.assertEquals(user.getPassword(), encryptedPassword);
    }

    @Test
    public void save() {
        User user = new User();
        user.setUsername("carl");
        user.setSalt("carl");
        user.setPhoneNumber("123");
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setPassword("123456");
        userService.saveUser(user);
    }

    @Test
    public void update() {
        User user = userService.authorize("carl");
        user.setUpdateDate(new Date());
        user.setCreateDate(new Date());
        userService.updateUser(user);
    }
}
