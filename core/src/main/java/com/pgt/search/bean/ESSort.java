package com.pgt.search.bean;

import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESSort extends ESBase {
    public ESSort() {

    }

    public ESSort(String propertyName, SortOrder sortOrder) {
        setPropertyName(propertyName);
        setSortOrder(sortOrder);
    }


    private SortOrder sortOrder;


    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
