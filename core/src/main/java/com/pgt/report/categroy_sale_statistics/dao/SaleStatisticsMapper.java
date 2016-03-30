package com.pgt.report.categroy_sale_statistics.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.report.categroy_sale_statistics.bean.SaleStatisticsBean;
import com.pgt.report.categroy_sale_statistics.bean.RootCategroyBean;
import com.pgt.report.categroy_sale_statistics.bean.Sales;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liqiang on 02/24/16.
 */
@Component
public interface SaleStatisticsMapper extends SqlMapper {
    List<SaleStatisticsBean> reportSubCategroyStocks();
    RootCategroyBean reportSelectRootCategroy(int i);
    List<Sales> reportSalesInfo();
}