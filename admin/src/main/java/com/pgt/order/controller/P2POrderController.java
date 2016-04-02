package com.pgt.order.controller;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.order.P2POrderService;
import com.pgt.order.service.B2COrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Yove on 16/2/7.
 */
@RequestMapping("/order")
@RestController
public class P2POrderController extends InternalTransactionBaseController implements OrderStatus {


    @Resource(name = "B2COrderService")
    private B2COrderService mB2COrderService;

    @Resource(name = "p2pOrderService")
    private P2POrderService p2pOrderService;


    @RequestMapping(value = "/order-detail", method = RequestMethod.GET)
    public ModelAndView loadOrderHistoryDetails (HttpServletRequest pRequest, HttpServletResponse pResponse,
                                                 @RequestParam(value = "id") String orderId) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        int orderIdInt = RepositoryUtils.safeParseId(orderId);
        Order order = getB2COrderService().loadOrder(orderIdInt);
        P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderIdInt);
        ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
        mav.addObject(ResponseConstant.P2P_ORDER, order);
        mav.addObject(ResponseConstant.P2P_INFO, info);
        return mav;
    }

    @RequestMapping(value = "/complete-item", method = RequestMethod.GET)
    public ModelAndView completeItem(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        String orderIdStr = pRequest.getParameter("orderId");
        String occupyStr = pRequest.getParameter("occupy");
        int orderId =  Integer.valueOf(orderIdStr);
        Order order = getB2COrderService().loadOrder(orderId);
        boolean occupy = Boolean.valueOf(occupyStr);
        getP2pOrderService().completeOrder(order, occupy, new Date());
        order = getB2COrderService().loadOrder(orderId);
        P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderId);
        ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
        mav.addObject(ResponseConstant.P2P_ORDER, order);
        mav.addObject(ResponseConstant.P2P_INFO, info);
        return mav;
    }


    @RequestMapping(value = "/change-order-status", method = RequestMethod.GET)
    public ModelAndView changeOrderStatus(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        String orderIdStr = pRequest.getParameter("orderId");
        String statusStr = pRequest.getParameter("status");
        int orderId =  Integer.valueOf(orderIdStr);
        Order order = getB2COrderService().loadOrder(orderId);
        int status = Integer.valueOf(statusStr);
        getB2COrderService().updateOrderStatus(orderId, status);
        P2PInfo info = getP2pOrderService().queryP2PInfoByOrderId(orderId);
        ModelAndView mav = new ModelAndView("/p2p-order/order-detail");
        mav.addObject(ResponseConstant.P2P_ORDER, order);
        mav.addObject(ResponseConstant.P2P_INFO, info);
        return mav;
    }


    public B2COrderService getB2COrderService() {
        return mB2COrderService;
    }

    public void setB2COrderService(B2COrderService mB2COrderService) {
        this.mB2COrderService = mB2COrderService;
    }

    public P2POrderService getP2pOrderService() {
        return p2pOrderService;
    }

    public void setP2pOrderService(P2POrderService p2pOrderService) {
        this.p2pOrderService = p2pOrderService;
    }
}
