package com.pgt.report.categroy_sale_statistics.impl;

import com.pgt.report.categroy_sale_statistics.bean.Sales;
import com.pgt.report.categroy_sale_statistics.dao.SaleStatisticsMapper;
import com.pgt.report.categroy_sale_statistics.service.SaleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liqiang on 16-2-26.
 */
@Component
public class SaleInfoImpl implements SaleInfoService{
    @Autowired
    private SaleStatisticsMapper saleStatisticsMapper;

    public List<Sales> reportSalesInfo(){
        return  saleStatisticsMapper.reportSalesInfo();
    }
}
