package com.pgt.style.bean;

import com.pgt.common.bean.Media;
import com.pgt.utils.PaginationBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 1/13/16.
 */
public class PageBackgroundQuery extends PageBackground implements Serializable {

    private Date nowDate;
    private PaginationBean  paginationBean;


    public PaginationBean getPaginationBean() {
        return paginationBean;
    }

    public void setPaginationBean(PaginationBean paginationBean) {
        this.paginationBean = paginationBean;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }
}
