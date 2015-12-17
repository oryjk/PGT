package util;

import com.pgt.configuration.Configuration;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pgt.utils.JsonUtil;

//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class JsonUtilTest {
	
	@Autowired
	private JsonUtil jsonUtil;

	@Autowired
	private Configuration configuration;

	@Test
	public void categoriesCreate(){
		try{
			//jsonUtil.categoriesCreate("src/main/resources/json/initialData .json");
			jsonUtil.categoriesCreate(configuration.getInitialDataPath());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
