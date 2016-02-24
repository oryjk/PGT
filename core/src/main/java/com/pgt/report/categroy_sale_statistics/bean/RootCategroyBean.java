package com.pgt.report.categroy_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-24.
 */
public class RootCategroyBean {
    private Integer id;
    private String name;

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

    @Override
    public String toString() {
        return "RootCategroyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
