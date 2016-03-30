package com.pgt.report.day_sale_statistics.impl;

import com.pgt.report.day_sale_statistics.bean.OneWeekSale;
import com.pgt.report.day_sale_statistics.dao.OneWeekSaleDao;
import com.pgt.report.day_sale_statistics.service.OneWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by liqiang on 16-2-26.
 */
@Component
public class OneWeekImpl implements OneWeekService{
    @Autowired
    private OneWeekSaleDao oneWeekSaleDao;

    public List<OneWeekSale> reportOneWeekSales(){
        return oneWeekSaleDao.reportOneWeekSales();
    }
    public OneWeekSale todaySales(){
        return  oneWeekSaleDao.todaySales();
    }
}
