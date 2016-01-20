package com.pgt.order;

import com.pgt.cart.OrderType;
import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.service.OrderService;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.product.bean.Product;
import com.pgt.tender.bean.Tender;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Samli on 2016/1/18.
 */
public class P2POrderService extends OrderService {

    public static final long MILLISECOND_ONE_DAY = 1000 * 60 * 60 * 24;
    public static final long DAYS_ONE_YEAR = 365;

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
        info.setUnitPrice(tender.getUnitPrice());
        info.setInterestRate(tender.getInterestRate());
        info.setHandlingFeeRate(tender.getHandlingFeeRate());
        calculateP2PInfo(info);
        persistenceP2PInfo(info);

        Order order = new Order();
        order.setType(OrderType.P2P_ORDER);
        order.setUserId(user.getId().intValue());
        order.setStatus(OrderStatus.INITIAL);
        order.setP2pInfoId(info.getId());
        // create commerceItem
        maintainCommerceItem(order, info, relatedProducts, productIds);
//        calculateCommerceItem(order, info);
        calculateOrder(order, info);


        return Pair.of(order, info);
    }

    private void maintainCommerceItem(Order order, P2PInfo info, List<Product> relatedProducts, String[] productIds) {
        double investTotal = info.getUnitPrice() * info.getPlaceQuantity();
        // TODO ROUND;
        double productTotal = 0D;

        List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();
        for (String productId : productIds) {
            int id = Integer.valueOf(productId);
            for (Product relatedProduct : relatedProducts) {
                if (id != relatedProduct.getProductId()) {
                    continue;
                }
            }
        }

    }

    private void calculateOrder(Order order, P2PInfo info) {
    }

    private void persistenceP2PInfo(P2PInfo info) {

    }

    private void calculateP2PInfo(P2PInfo info) {
        double incoming = calculateIncoming(info.getPublishDate(), info.getExpectDueDate(), info.getPrePeriod(), info.getUnitPrice(), info.getPlaceQuantity(), info.getInterestRate());
        info.setExpectIncoming(incoming);
        double handlingFee = calculateHandlingFee(info);
        info.setHandlingFee(handlingFee);
    }

    private double calculateHandlingFee(P2PInfo info) {
        if (null == info.getUnitPrice()) {
            // TODO LOG ERROR
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        if (null == info.getHandlingFeeRate()) {
            // TODO LOG ERROR
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        double total = info.getUnitPrice() * info.getPlaceQuantity();
        double result = total * info.getHandlingFeeRate();
        // TODO ROUND
        return result;
    }

    private double calculateIncoming(Date publishDate, Date expectDueDate, Integer prePeriod, Double unitPrice, int placeQuantity, Double interestRate) {
        boolean hasIllegalArgument = false;
        if (null == publishDate) {
            // TODO LOG ERROR
            hasIllegalArgument= true;
        }
        if (null == expectDueDate) {
            // TODO LOG ERROR
            hasIllegalArgument= true;
        }

        if (null == prePeriod) {
            // TODO LOG ERROR
            hasIllegalArgument= true;
        }

        if (null == unitPrice) {
            // TODO LOG ERROR
            hasIllegalArgument= true;
        }

        if (null == interestRate) {
            // TODO LOG ERROR
            hasIllegalArgument= true;
        }
        if ( hasIllegalArgument) {
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        if (expectDueDate.before(publishDate)) {
            // TODO LOG ERROR
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        // TODO date start time
        int dateGap = (int) ((expectDueDate.getTime() - publishDate.getTime()) / MILLISECOND_ONE_DAY);
        if (dateGap < prePeriod) {
            // TODO LOG ERROR
            throw new IllegalArgumentException("INVALID.TENDER");
        }

        // TODO DAYS OF YEAR
        double total = unitPrice * placeQuantity * ( 1 + interestRate);
        int effectDates = dateGap - prePeriod;
        double result = total * effectDates / DAYS_ONE_YEAR;
        // TODO ROUND
        return result;
    }
}
