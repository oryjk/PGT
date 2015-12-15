package com.pgt.search.bean;

import org.elasticsearch.search.sort.SortOrder;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESSort extends ESBase {
    private SortOrder sortOrder;


    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
