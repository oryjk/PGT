package com.pgt.report.categroy_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-24.
 */
public class RootCategroyBean {
    //父分类id
    private Integer cate_id;
    //父分类id
    private Integer id;
    private String name;
    private Integer sale_stocks;
    private Integer sale_prices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSale_stocks() {
        return sale_stocks;
    }

    public void setSale_stocks(Integer sale_stocks) {
        this.sale_stocks = sale_stocks;
    }

    public Integer getSale_prices() {
        return sale_prices;
    }

    public void setSale_prices(Integer sale_prices) {
        this.sale_prices = sale_prices;
    }

    public Integer getCate_id() {
        return cate_id;
    }

    public void setCate_id(Integer cate_id) {
        this.cate_id = cate_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RootCategroyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
