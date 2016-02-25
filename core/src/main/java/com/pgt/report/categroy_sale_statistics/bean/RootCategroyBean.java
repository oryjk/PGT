package com.pgt.report.categroy_sale_statistics.bean;

/**
 * Created by liqiang on 16-2-24.
 */
public class RootCategroyBean {
    //父分类id
    private Integer cateId;
    //父分类id
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

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }


    @Override
    public String toString() {
        return "RootCategroyBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
