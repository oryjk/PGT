package com.pgt.report.day_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-26.
 */
public class OneWeekSale {
    private Long saleStocks;
    private Long salePrices;
    private Integer todaySalesNum;
    private float averge;

    public Long getSaleStocks() {
        return saleStocks;
    }

    public void setSaleStocks(Long saleStocks) {
        this.saleStocks = saleStocks;
    }

    public Long getSalePrices() {
        return salePrices;
    }

    public void setSalePrices(Long salePrices) {
        this.salePrices = salePrices;
    }

    public Integer getTodaySalesNum() {
        return todaySalesNum;
    }

    public void setTodaySalesNum(Integer todaySalesNum) {
        this.todaySalesNum = todaySalesNum;
    }

    public float getAverge() {
        return averge;
    }

    public void setAverge(float averge) {
        this.averge = averge;
    }
}
