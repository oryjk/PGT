package com.pgt.report.categroy_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-26.
 */
public class Sales {
    private Integer id;
    private String name;
    private Long saleStocks;
    private Long salePrices;
    private Stocks stocks;
    private float ratio;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Stocks getStocks() {
        return stocks;
    }

    public void setStocks(Stocks stocks) {
        this.stocks = stocks;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }
}
