package com.pgt.tender.bean;

import com.pgt.category.bean.Category;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 1/20/16.
 */
public class ESTender implements Serializable {
    private Tender tender;
    private Category parentCategory;
    private Category rootCategory;
    private List<Map<String,String>> buyerMap;
    private long newest;
    private long cycle;
    private long end;

    public long getNewest() {
        if (ObjectUtils.isEmpty(tender)) {
            return 9223372036854775806L;
        }
        if (new Date().getTime() - tender.getPublishDate().getTime() < 0) {
            return 9223372036854775806L;
        }
        return new Date().getTime() - tender.getPublishDate().getTime();
    }

    public void setNewest(long newest) {
        this.newest = newest;
    }

    public long getCycle() {
        if (ObjectUtils.isEmpty(tender)) {
            return 9223372036854775806L;
        }
        return tender.getDueDate().getTime() - tender.getPublishDate().getTime();
    }

    public void setCycle(long cycle) {
        this.cycle = cycle;
    }

    public long getEnd() {
        if (ObjectUtils.isEmpty(tender)) {
            return 9223372036854775806L;
        }

        return tender.getDueDate().getTime() - new Date().getTime();
    }

    public void setEnd(long end) {
        this.end = end;
    }


    public ESTender(Tender tender, Category parentCategory, Category rootCategory, List<Map<String, String>> buyerMap) {
        this.tender = tender;
        this.rootCategory = rootCategory;
        this.parentCategory = parentCategory;
        this.buyerMap = buyerMap;
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

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Map<String, String>> getBuyerMap() {
        return buyerMap;
    }

    public void setBuyerMap(List<Map<String, String>> buyerMap) {
        this.buyerMap = buyerMap;
    }
}
