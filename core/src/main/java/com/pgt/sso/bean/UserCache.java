package com.pgt.sso.bean;

import com.pgt.cart.bean.Order;
import com.pgt.integration.alipay.MD5;
import com.pgt.user.bean.User;
import liquibase.util.MD5Util;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 2/18/16.
 */
public class UserCache implements Serializable {
    private static final long serialVersionUID = 1L;
    private String token;
    private Date updateTime;
    private User user;
    private Order b2cOrder;
    private Order p2pOrder;

    public UserCache(Date date, User user, Order b2cOrder, Order p2pOrder) {
        this.updateTime = date;
        this.user = user;
        this.b2cOrder = b2cOrder;
        this.p2pOrder = p2pOrder;
        if (!ObjectUtils.isEmpty(user)) {
            String source = date.getTime() + user.getId() + "";
            this.token = MD5Util.computeMD5(source);
        }
    }

    public UserCache() {
        super();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getB2cOrder() {
        return b2cOrder;
    }

    public void setB2cOrder(Order b2cOrder) {
        this.b2cOrder = b2cOrder;
    }

    public Order getP2pOrder() {
        return p2pOrder;
    }

    public void setP2pOrder(Order p2pOrder) {
        this.p2pOrder = p2pOrder;
    }
}
