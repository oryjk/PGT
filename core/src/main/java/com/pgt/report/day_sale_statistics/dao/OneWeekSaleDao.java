package com.pgt.report.day_sale_statistics.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.report.day_sale_statistics.bean.OneWeekSale;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liqiang on 16-2-26.
 */
@Component
public interface OneWeekSaleDao extends SqlMapper{
    List<OneWeekSale> reportOneWeekSales();
    OneWeekSale todaySales();
}
