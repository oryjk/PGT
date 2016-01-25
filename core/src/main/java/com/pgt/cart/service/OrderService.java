package com.pgt.cart.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.dao.OrderMapper;
import com.pgt.user.bean.User;

@Service
public class OrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderMapper orderMapper;

    public Order loadOrder(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        return getOrderMapper().loadOrder(orderId);
    }

    public Order loadEasyBuyOrderWithoutItem(String userId) {
        Order order = loadEasyBuyOrderByUserId(userId);
        if (order == null) {
            return null;
        }
        order.setCommerceItems(new ArrayList<CommerceItem>());
        deleteAllCommerceItems(order.getId());
        return order;
    }

    public Order loadEasyBuyOrderByUserId(String userId) {
        return getOrderMapper().loadEasyBuyOrderByUserId(userId);
    }

    public boolean updateOrder(Order order) {
        if (order == null) {
            return false;
        }
        getOrderMapper().updateOrder(order);
        return true;
    }

    public Order getSessionOrder(HttpServletRequest request) {
        String orderId = request.getParameter(CartConstant.ORDER_ID);
        if (StringUtils.isBlank(orderId)) {
            return null;
        }
        Order cartOrder = (Order) request.getSession().getAttribute(CartConstant.CURRENT_ORDER);
        if (cartOrder != null && orderId.equals(String.valueOf(cartOrder.getId()))) {
            return cartOrder;
        }

        String orderKey = CartConstant.ORDER_KEY_PREFIX + orderId;
        Order order = (Order) request.getSession().getAttribute(orderKey);
        if (null == order) {
            order = loadOrder(Integer.valueOf(orderId));
        }
        return order;
    }

    public void removeOrderFromSession(String orderId, HttpServletRequest request) {
        if (StringUtils.isBlank(orderId)) {
            LOGGER.error("Cannot remove order from session caused by paramerter orderId is empty.");
            return;
        }
        HttpSession session = request.getSession();
        Order cartOrder = (Order) session.getAttribute(CartConstant.CURRENT_ORDER);
        if (cartOrder != null && orderId.equals(String.valueOf(cartOrder.getId()))) {
            session.removeAttribute(CartConstant.CURRENT_ORDER);
            return;
        }
        session.setAttribute(CartConstant.ORDER_KEY_PREFIX + orderId, null);
    }

    public void deleteAllCommerceItems(Integer orderId) {
        getOrderMapper().deleteAllCommerceItems(orderId);
    }

    public boolean isInvalidOrder(User user, Order order) {
        if (user == null || order == null) {
            return true;
        }
        if (CollectionUtils.isEmpty(order.getCommerceItems())) {
            return true;
        }
        return user.getId().intValue() != order.getUserId();
    }


    public boolean hasUncompleteOrder(int userId, int type) {
        int amount = getOrderMapper().getUncompleteOrderAmount(userId, type);
        return amount > 0;
    }

    public OrderMapper getOrderMapper() {
        return orderMapper;
    }

    public void setOrderMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

}
