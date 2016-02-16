package com.pgt.pawn.bean;

import com.pgt.base.bean.OrderField;
import com.pgt.utils.PaginationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
public class PawnPersonInfoQuery extends PawnPersonInfo{

    private PaginationBean paginationBean;

    private List<OrderField> orderFields = new ArrayList<>();

    public PawnPersonInfoQuery orderbyCondition (boolean isAsc, String condition) {
        orderFields.add(new OrderField(condition, isAsc ? "ASC" : "DESC"));
        return this;
    }

}
