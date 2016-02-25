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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ReportTestVersion1 {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportTestVersion1.class);

}