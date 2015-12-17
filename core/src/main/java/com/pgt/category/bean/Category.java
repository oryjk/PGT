package com.pgt.category.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pgt.base.bean.BaseBean;
import com.pgt.category.validation.CreateGroup;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;

import javax.validation.constraints.NotNull;
import java.util.List;

public class Category extends BaseBean {

    private Integer id;
    @NotNull(groups = CreateGroup.class, message = "{Category.name.null}")
    private String name;
    private String code;
    @JsonBackReference
    private Category parent;
    @JsonManagedReference
    private List<Category> children;
    private Integer status;
    private ProductMedia frontMedia;// 新加分类显示图片
    private String color;
    private Integer sort = 1;
    private String description;

    public ProductMedia getFrontMedia() {
        return frontMedia;
    }

    public void setFrontMedia(ProductMedia frontMedia) {
        this.frontMedia = frontMedia;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private CategoryType type;
    private List<Product> products;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
        this.children = children;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CategoryType getType() {
        return type;
    }

    public void setType(CategoryType type) {
        this.type = type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
