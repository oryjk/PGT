package com.pgt.tender.bean;

import com.pgt.category.bean.Category;

import java.io.Serializable;

/**
 * Created by carlwang on 1/20/16.
 */
public class ESTender implements Serializable {
    private Tender tender;
    private Category rootCategory;

    public ESTender(Tender tender, Category rootCategory){
        this.tender=tender;
        this.rootCategory= rootCategory;
    }

    public Category getRootCategory() {
        return rootCategory;
    }

    public void setRootCategory(Category rootCategory) {
        this.rootCategory = rootCategory;
    }

    public Tender getTender() {
        return tender;
    }

    public void setTender(Tender tender) {
        this.tender = tender;
    }
}
