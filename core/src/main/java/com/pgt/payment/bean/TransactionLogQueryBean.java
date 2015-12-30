package com.pgt.payment.bean;

import com.pgt.utils.PaginationBean;

import java.util.Date;

/**
 * Created by Administrator on 2015/12/28.
 */
public class TransactionLogQueryBean {

    private Integer orderId;

    private Integer userId;

    private Date startTime;

    private Date endTime;

    private Integer paymentGroupId;

    private Integer transactionId;

    private String type;

    private String serviceName;

    private PaginationBean paginationBean;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPaymentGroupId() {
        return paymentGroupId;
    }

    public void setPaymentGroupId(Integer paymentGroupId) {
        this.paymentGroupId = paymentGroupId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public PaginationBean getPaginationBean() {
        return paginationBean;
    }

    public void setPaginationBean(PaginationBean paginationBean) {
        this.paginationBean = paginationBean;
    }
}
