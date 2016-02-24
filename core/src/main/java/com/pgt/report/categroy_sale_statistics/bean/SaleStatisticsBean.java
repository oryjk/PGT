package com.pgt.report.categroy_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-24.
 */
public class SaleStatisticsBean {
    private String name;
    private Integer id;
    private Integer stocks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStocks() {
        return stocks;
    }

    public void setStocks(Integer stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "SaleStatisticsBean{" +
                "id=" + id +
                ", stocks=" + stocks +
                '}';
    }
}
