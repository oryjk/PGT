import java.util.Date;
import java.util.List;

import com.pgt.token.service.TokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class TokenTest {

	@Autowired
	TokenService tokenService;

	@Test
	public void testeAdd() {



	}


}
