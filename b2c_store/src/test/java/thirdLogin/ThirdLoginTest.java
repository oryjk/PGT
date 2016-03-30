package thirdLogin;

import com.pgt.user.bean.ThirdLogin;
import com.pgt.user.bean.User;
import com.pgt.user.service.ThirdLoginService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by zhangxiaodong on 16-3-7.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ThirdLoginTest {

    @Autowired
    private ThirdLoginService thirdLoginService;


    @Test
    public void TestAdd(){

        ThirdLogin thirdLogin= new ThirdLogin();
        thirdLogin.setExpire(new Date());
        thirdLogin.setOpenId("2321321323");
        thirdLogin.setRefreshToken("sdsadsd");
        thirdLogin.setToken("sdsdsd");
        thirdLogin.setType("2323");
        User user = new User();
        user.setId(23L);
        user.setUsername("sdssd");
        thirdLogin.setUser(user);
        thirdLoginService.createThirdLogin(thirdLogin);

    }

    @Test
    public void TestUpdate(){

        ThirdLogin thirdLogin= new ThirdLogin();
        thirdLogin.setExpire(new Date());
        thirdLogin.setOpenId("sdad");
        thirdLogin.setRefreshToken("s2323");
        thirdLogin.setToken("aaaa");
        thirdLogin.setType("bbbbb");
        thirdLogin.setId(1);

        thirdLoginService.updateThirdLogin(thirdLogin);

    }


    @Test
    public void query(){

        ThirdLogin thirdLogin=thirdLoginService.queryThirdLoginById(1);

        thirdLogin.getToken();
    }


    @Test
    public void testDelete(){

        thirdLoginService.deleteThirdLoginById(1);

    }


}
