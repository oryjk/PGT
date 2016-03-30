package com.pgt.report.categroy_sale_statistics.service;

import com.pgt.report.categroy_sale_statistics.bean.Sales;

import java.util.List;

/**
 * Created by liqiang on 16-2-26.
 */
public interface SaleInfoService {
    List<Sales> reportSalesInfo();
}
