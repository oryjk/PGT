package com.pgt.home.bean;

import com.pgt.category.bean.Category;
import com.pgt.tender.bean.Tender;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 1/27/16.
 */
public class HomeTender implements Serializable {
    private Category category;
    private List<Tender> tenderList;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Tender> getTenderList() {
        if (ObjectUtils.isEmpty(tenderList)) {
            tenderList = new ArrayList<>();
        }
        return tenderList;
    }

    public void setTenderList(List<Tender> tenderList) {
        this.tenderList = tenderList;
    }
}
