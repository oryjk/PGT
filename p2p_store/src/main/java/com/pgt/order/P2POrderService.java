package com.pgt.order;

import com.pgt.cart.OrderType;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.service.OrderService;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.product.bean.Product;
import com.pgt.tender.bean.Tender;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
 * Created by Administrator on 2016/1/18.
 */
public class P2POrderService extends OrderService {

    public Pair<Order, P2PInfo> createP2POrder(User user, Tender tender, List<Product> relatedProducts, String[] productIds, int placeQuantity) {
        P2PInfo info = new P2PInfo();
        info.setTenderId(tender.getId());
        info.setPawnShopOwnerId(tender.getPawnShopOwnerId());
        info.setPawnShopId(tender.getPawnShopId());
        info.setPostPeriod(tender.getPostPeriod());
        info.setPrePeriod(tender.getPrePeriod());
        info.setPublishDate(tender.getPublishDate());
        info.setExpectDueDate(tender.getDueDate());
        info.setPlaceQuantity(placeQuantity);
        calculateP2PInfo(info, tender);
        persistenceP2PInfo(info);

        Order order = new Order();
        order.setType(OrderType.P2P_ORDER);
        order.setUserId(user.getId().intValue());
        order.setStatus(OrderStatus.INITIAL);
        order.setP2pInfoId(info.getId());
        // create commerceItem



        return Pair.of(order, info);
    }

    private void persistenceP2PInfo(P2PInfo info) {

    }

    private void calculateP2PInfo(P2PInfo info, Tender tender) {

    }
}
