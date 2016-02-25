package common;

import com.alibaba.fastjson.JSONObject;
import com.pgt.report.categroy_sale_statistics.bean.RootCategroyBean;
import com.pgt.report.categroy_sale_statistics.bean.SaleStatisticsBean;
import com.pgt.report.categroy_sale_statistics.dao.SaleStatisticsMapper;
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

import java.util.*;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ReportTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTest.class);

	@Autowired
	private SaleStatisticsMapper dao;


	@Test
	public void TestDao() {
		//获取子节点销售信息
		List<SaleStatisticsBean> reportSubCategroyStocks_list = dao.reportSubCategroyStocks();
		for(SaleStatisticsBean obj : reportSubCategroyStocks_list){
			Map<String,Object> map = findRoot(obj.getId());
			obj.setName(map.get("name").toString());
			obj.setId(NumberUtils.toInt(map.get("cate_id").toString()));
			LOGGER.debug("start:" + JSONObject.toJSONString(obj));
		}

		//返回销售信息（每个大分类销售金额，销售件数，库存）
		groupCategroy(reportSubCategroyStocks_list);

		LOGGER.debug(":end");
	}


	//查找父节点
	public Map<String , Object> findRoot(int sub_categroy_id){
		RootCategroyBean root =  dao.reportSelectRootCategroy(sub_categroy_id);
		Map<String,Object> map = new HashMap<>();
		map.put("name", root.getName());
		map.put("cate_id", root.getCateId());
		if(!ObjectUtils.isEmpty(root.getId())){
			int id = root.getId();
			if(id > 0){
				map = findRoot(id);
			}
		}
		return  map;
	}

	//根据父类id调用递归合并方法
	public void groupCategroy(List<SaleStatisticsBean> reportSubCategroyStocks_list){
		for(int i = 0; i < reportSubCategroyStocks_list.size(); i++ ){
			final int id;
			id = reportSubCategroyStocks_list.get(i).getId();
			int count = 0;
			groupCategroyTotal(count, id, i , reportSubCategroyStocks_list);
		}
	}

	//递归合并相同子分类销售结果 -父分类
	public void groupCategroyTotal(int count, int id,int i ,List<SaleStatisticsBean> reportSubCategroyStocks_list){
		boolean flag = false;
		for(int j = 0; j < reportSubCategroyStocks_list.size(); j++){
			if(id == (reportSubCategroyStocks_list.get(j).getId())){
				count++;
				if(count > 1){
					//计算库存
					reportSubCategroyStocks_list.get(i).setStocks(
							reportSubCategroyStocks_list.get(i).getStocks() +
									reportSubCategroyStocks_list.get(j).getStocks()
					);
					if(!ObjectUtils.isEmpty(reportSubCategroyStocks_list.get(i).getSaleCategroyBean())){
						if(!ObjectUtils.isEmpty(reportSubCategroyStocks_list.get(j).getSaleCategroyBean())){
							//计算销售件数
							reportSubCategroyStocks_list.get(i).getSaleCategroyBean().setSaleStocks(
									reportSubCategroyStocks_list.get(i).getSaleCategroyBean().getSaleStocks() +
											reportSubCategroyStocks_list.get(j).getSaleCategroyBean().getSaleStocks()
							);
							//计算分类销售价格
							reportSubCategroyStocks_list.get(i).getSaleCategroyBean().setSalePrices(
									reportSubCategroyStocks_list.get(i).getSaleCategroyBean().getSalePrices() +
											reportSubCategroyStocks_list.get(j).getSaleCategroyBean().getSalePrices()
							);
						}
					}
					reportSubCategroyStocks_list.remove(j);
					flag = true;
					break;
				}
				LOGGER.debug("c:" + count);
			}
		}
		if(flag == true){
			groupCategroy(reportSubCategroyStocks_list);
		}
	}

}