package com.pgt.common.bean;

import com.pgt.utils.PaginationBean;

/**
 * Created by xiaodong on 16-1-7.
 */
public class BannerQuery extends Banner{

    private String fields;

    private PaginationBean paginationBean;

    public PaginationBean getPaginationBean() {
        return paginationBean;
    }

    public void setPaginationBean(PaginationBean paginationBean) {
        this.paginationBean = paginationBean;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
