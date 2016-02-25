package common;

import com.alibaba.fastjson.JSONObject;
import com.pgt.common.bean.Image;
import com.pgt.common.bean.ImageCustom;
import com.pgt.common.service.ImageService;
import com.pgt.report.categroy_sale_statistics.bean.RootCategroyBean;
import com.pgt.report.categroy_sale_statistics.bean.SaleStatisticsBean;
import com.pgt.report.categroy_sale_statistics.dao.SaleStatisticsMapper;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;

import javax.json.Json;
import java.util.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class reportTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(reportTest.class);

	@Autowired
	private SaleStatisticsMapper dao;


	@Test
	public void TestDao() {
		//获取子节点销售信息
	    List<SaleStatisticsBean> reportSubCategroyStocks_list = dao.reportSubCategroyStocks();
		for(SaleStatisticsBean obj : reportSubCategroyStocks_list){
			Map<String,Object> map = findRoot(obj.getId());
			obj.setName(map.get("name").toString());
			LOGGER.debug("********************" + JSONObject.toJSONString(obj));
		}

	}


	//查找父节点
	public Map<String , Object> findRoot(int sub_categroy_id){
		RootCategroyBean root =  dao.reportSelectRootCategroy(sub_categroy_id);
		Map<String,Object> map = new HashMap<>();
		map.put("name", root.getName());
		if(!ObjectUtils.isEmpty(root.getId())){
			int id = root.getId();
			if(id > 0){
				map = findRoot(id);
			}
		}
		return  map;
	}

}
