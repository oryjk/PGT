package com.pgt.category.bean;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pgt.common.bean.Banner;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.tender.bean.Tender;

import java.util.List;

/**
 * Created by carlwang on 3/19/16.
 */
public class ESCategory {
    private Category category;

    public ESCategory(Category category) {
        this.category = category;
    }


    private List<Tender> hotTenders;
    @JsonManagedReference
    private List<ESCategory> esChildren;


    public List<Tender> getHotTenders() {
        return hotTenders;
    }

    public void setHotTenders(List<Tender> hotTenders) {
        this.hotTenders = hotTenders;
    }

    public List<ESCategory> getEsChildren() {
        return esChildren;
    }

    public void setEsChildren(List<ESCategory> esChildren) {
        this.esChildren = esChildren;
    }


    public String getImageDesc() {
        return category.getImageDesc();
    }

    public Banner getBanner() {
        return category.getBanner();
    }


    public ProductMedia getFrontMedia() {
        return category.getFrontMedia();
    }


    public String getColor() {
        return category.getColor();
    }


    public ProductMedia getIconMedia() {
        return category.getIconMedia();
    }

    public Integer getId() {
        return category.getId();
    }


    public String getName() {
        return category.getName();
    }


    public String getCode() {
        return category.getCode();
    }


    public Integer getStatus() {
        return category.getStatus();
    }

    public CategoryType getType() {
        return category.getType();
    }


    public List<Product> getProducts() {
        return category.getProducts();
    }


    public Integer getSort() {
        return category.getSort();
    }


    public String getDescription() {
        return category.getDescription();
    }


    public Integer getCategoryIndex() {
        return category.getCategoryIndex();
    }


    public Integer getProductTotal() {
        return category.getProductTotal();
    }

    @JsonBackReference
    public Category getParent() {
        return this.category.getParent();
    }

    @JsonManagedReference
    public List<Category> getChildren() {
        return this.category.getChildren();
    }
}
