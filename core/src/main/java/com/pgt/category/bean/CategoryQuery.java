package com.pgt.category.bean;

import com.pgt.base.bean.OrderField;
import com.pgt.utils.PaginationBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-3-3.
 */
public class CategoryQuery extends Category implements Serializable {



    private PaginationBean paginationBean;

    /**
     * 封装排序条件
     */
    private List<OrderField> orderFields = new ArrayList<>();




    /**
     * 任意排序条件
     *
     * @param isAsc 是否升序
     * @return
     */
    public CategoryQuery orderbyCondition (boolean isAsc, String condition) {
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
}
