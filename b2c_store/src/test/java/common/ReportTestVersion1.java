package common;

import com.alibaba.fastjson.JSONObject;
import com.pgt.report.categroy_sale_statistics.bean.RootCategroyBean;
import com.pgt.report.categroy_sale_statistics.bean.SaleStatisticsBean;
import com.pgt.report.categroy_sale_statistics.bean.Sales;
import com.pgt.report.categroy_sale_statistics.dao.SaleStatisticsMapper;
import com.pgt.report.categroy_sale_statistics.service.SaleInfoService;
import com.pgt.report.day_sale_statistics.bean.OneWeekSale;
import com.pgt.report.day_sale_statistics.dao.OneWeekSaleDao;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ReportTestVersion1 {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestVersion1.class);
	@Autowired
	private SaleInfoService saleInfoService;

	@Autowired
	private OneWeekSaleDao dao;

	@Test
	public void TestDao() {
		List<Sales> list = saleInfoService.reportSalesInfo();
		LOGGER.debug(":end");
		List<OneWeekSale> list1 = dao.reportOneWeekSales();
		LOGGER.debug(":end");
		OneWeekSale entity = dao.todaySales();
		LOGGER.debug(":end");
	}

}