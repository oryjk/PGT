package com.pgt.share.bean;

import com.pgt.common.bean.Media;
import com.pgt.communication.bean.Discuss;
import com.pgt.product.bean.Product;
import com.pgt.user.bean.User;


import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-14.
 */
public class ShareOrder {

    private Integer shareOrderId;
    private String title;//标题
    private List<Media> shareMedias;//晒单图片
    private Integer start;//评价等级
    private String evaluate;//评价
    private User user;
    private Product product;
    private Integer readingQuantity;//阅读量
    private Integer clickHigh;//点赞
    private Integer clickLow;//点差
    private List<Discuss> discusses;
    private Date createDate;
    private Boolean isShow;//审核

    public Boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(Boolean isShow) {
        this.isShow = isShow;
    }

    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Integer getShareOrderId() {
        return shareOrderId;
    }
    public void setShareOrderId(Integer shareOrderId) {
        this.shareOrderId = shareOrderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Media> getShareMedias() {
        return shareMedias;
    }

    public void setShareMedias(List<Media> shareMedias) {
        this.shareMedias = shareMedias;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Discuss> getDiscusses() {
        return discusses;
    }

    public void setDiscusses(List<Discuss> discusses) {
        this.discusses = discusses;
    }

    public Integer getClickLow() {
        return clickLow;
    }

    public void setClickLow(Integer clickLow) {
        this.clickLow = clickLow;
    }

    public Integer getClickHigh() {
        return clickHigh;
    }

    public void setClickHigh(Integer clickHigh) {
        this.clickHigh = clickHigh;
    }

    public Integer getReadingQuantity() {
        return readingQuantity;
    }

    public void setReadingQuantity(Integer readingQuantity) {
        this.readingQuantity = readingQuantity;
    }
}
