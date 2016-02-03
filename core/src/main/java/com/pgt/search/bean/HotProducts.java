package com.pgt.search.bean;

import org.elasticsearch.search.SearchHit;

/**
 * Created by xiaodong on 16-2-1.
 */
public class HotProducts {

    private SearchHit category;

    private  SearchHit[] hotProducts;

    public SearchHit getCategory() {
        return category;
    }

    public void setCategory(SearchHit category) {
        this.category = category;
    }

    public SearchHit[] getHotProducts() {
        return hotProducts;
    }

    public void setHotProducts(SearchHit[] hotProducts) {
        this.hotProducts = hotProducts;
    }
}
