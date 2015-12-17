package util;

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

	@Test
	public void categoriesCreate(){
		try{
			jsonUtil.categoriesCreate("src/main/resources/json/category1.json");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
