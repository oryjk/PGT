package com.pgt.checkout.controller;

import com.pgt.cart.OrderType;
import com.pgt.cart.exception.OrderPersistentException;
import com.pgt.cart.service.OrderService;
import com.pgt.order.P2POrderService;
import com.pgt.product.bean.Product;
import com.pgt.session.SessionHelper;
import com.pgt.tender.bean.Tender;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Samli on 2016/1/16.
 */
public class P2PCheckoutController {
    private static final int NO_ERROR = 0;
    private static final int PRODUCT_NOT_EXIST = 1;
    private static final int INVEST_TOTAL_NOT_ENOUGH = 2;

    private P2POrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(P2PCheckoutController.class);

    public ModelAndView createOrder(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        User user = SessionHelper.getUser(pRequest, pResponse);
        if (null == user) {
            //  TODO REDIRECT TO LOGIN
        }
        String tenderIdStr = pRequest.getParameter("tenderId");
        String placeQuantityStr = pRequest.getParameter("placeQuantity");
        String [] productIds = pRequest.getParameterValues("productIds");
        if (StringUtils.isBlank(tenderIdStr) || !StringUtils.isNumeric(tenderIdStr)) {
            // TODO
        }
        if (StringUtils.isBlank(placeQuantityStr) || !StringUtils.isNumeric(placeQuantityStr)) {
            // TODO
        }
        int tenderId = Integer.valueOf(tenderIdStr);
        int placeQuantity = Integer.valueOf(placeQuantityStr);

        // check if has not pay order
        if(getOrderService().hasUncompleteOrder(user.getId().intValue(), OrderType.P2P_ORDER)) {
            // TODO redirect to tendId
        }
        // TODO QUERY TENDER BY ID
        Tender tender = null;
        // TODO QUERY related product
        List<Product> relatedProducts = null;

        // check productIds is valid
        int errorCode = isProductIdsValid(productIds, relatedProducts, tender, placeQuantity);
        if(NO_ERROR != errorCode) {
            // TODO redirect to tendId
        }
        // check inventory

        // create order
        // TODO EXCEIPTOIN
        try {
            getOrderService().createP2POrder(user, tender, relatedProducts, productIds, placeQuantity);
        } catch (OrderPersistentException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     *
     * @param productIds
     * @param relatedProducts
     * @param tender
     * @param placeQuantity
     * @return error code
     */
    private int isProductIdsValid(String[] productIds, List<Product> relatedProducts, Tender tender, int placeQuantity) {
        if (null != productIds) {
            // check relatedProducts need contains productIds
            if (null == relatedProducts || relatedProducts.isEmpty()) {
                // TODO LOG
                return PRODUCT_NOT_EXIST;
            }
            double total = 0D;
            for (String productId : productIds) {
                if (null == productId) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.valueOf(productId);
                } catch (Exception e) {
                    // TODO log
                    return PRODUCT_NOT_EXIST;
                }
                boolean match = false;
                for (Product relatedProduct : relatedProducts) {
                    if (null == relatedProduct || null == relatedProduct.getProductId()) {
                        continue;
                    }
                    if (id == relatedProduct.getProductId()) {
                        match = true;
                        total += relatedProduct.getSalePrice();
                        // TODO ROUND
                        break;
                    }
                }
                if (!match) {
                    // TODO log
                    return PRODUCT_NOT_EXIST;
                }
            }
            double investTotal = tender.getUnitPrice() * placeQuantity;
            // TODO ROUND;
            if (total > investTotal) {
                // TODO log
                return INVEST_TOTAL_NOT_ENOUGH;
            }
         }
        return NO_ERROR;
    }

    public P2POrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(P2POrderService orderService) {
        this.orderService = orderService;
    }
}
