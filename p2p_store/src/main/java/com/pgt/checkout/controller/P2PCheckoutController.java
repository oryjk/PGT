package com.pgt.checkout.controller;

import com.pgt.cart.OrderType;
import com.pgt.cart.service.OrderService;
import com.pgt.order.P2POrderService;
import com.pgt.product.bean.Product;
import com.pgt.session.SessionHelper;
import com.pgt.tender.bean.Tender;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Administrator on 2016/1/16.
 */
public class P2PCheckoutController {

    private P2POrderService orderService;


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
        boolean isValid = isProductIdsValid(productIds, relatedProducts);
        if(!isValid) {
            // TODO redirect to tendId
        }
        // check inventory

        // create order
        getOrderService().createP2POrder(user, tender, relatedProducts, productIds, placeQuantity);


        return null;
    }

    private boolean isProductIdsValid(String [] productIds, List<Product> relatedProducts) {
        if (null != productIds) {
            if (null == relatedProducts || relatedProducts.isEmpty()) {
                return false;
            }
            for (String productId : productIds) {
                if (null == productId) {
                    continue;
                }
                int id = 0;
                try {
                    id = Integer.valueOf(productId);
                } catch (Exception e) {
                    // TODO log
                    return false;
                }

                for (Product relatedProduct : relatedProducts) {
                    if (null == relatedProduct || null == relatedProduct.getProductId()) {
                        continue;
                    }
                    if (id != relatedProduct.getProductId()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public P2POrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(P2POrderService orderService) {
        this.orderService = orderService;
    }
}
