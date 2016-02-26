package com.pgt.report.day_sale_statistics.service;

import com.pgt.report.day_sale_statistics.bean.OneWeekSale;

import java.util.List;

/**
 * Created by liqiang on 16-2-26.
 */
public interface OneWeekService {
    List<OneWeekSale> reportOneWeekSales();
    OneWeekSale todaySales();
}
