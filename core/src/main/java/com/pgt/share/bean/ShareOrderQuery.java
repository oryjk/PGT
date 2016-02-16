package com.pgt.share.bean;

import com.pgt.base.bean.OrderField;
import com.pgt.utils.PaginationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-14.
 */
public class ShareOrderQuery extends ShareOrder {

    private PaginationBean paginationBean;

    private Boolean titleLike;

    /**
     * 封装排序条件
     */
    private List<OrderField> orderFields = new ArrayList<OrderField>();

    /**
     * 任意排序条件
     *
     * @param isAsc 是否升序
     * @return
     */
    public ShareOrderQuery orderbyCondition (boolean isAsc, String condition) {
        orderFields.add(new OrderField(condition, isAsc ? "ASC" : "DESC"));
        return this;
    }

    public PaginationBean getPaginationBean() {
        return paginationBean;
    }

    public void setPaginationBean(PaginationBean paginationBean) {
        this.paginationBean = paginationBean;
    }

    public List<OrderField> getOrderFields() {
        return orderFields;
    }

    public void setOrderFields(List<OrderField> orderFields) {
        this.orderFields = orderFields;
    }

    public Boolean getTitleLike() {
        return titleLike;
    }

    public void setTitleLike(Boolean titleLike) {
        this.titleLike = titleLike;
    }
}
