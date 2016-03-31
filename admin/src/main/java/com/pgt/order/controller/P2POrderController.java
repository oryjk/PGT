package com.pgt.order.controller;

import com.pgt.cart.bean.OrderStatus;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.order.P2POrderService;
import com.pgt.order.service.B2COrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

    


    public B2COrderService getmB2COrderService() {
        return mB2COrderService;
    }

    public void setmB2COrderService(B2COrderService mB2COrderService) {
        this.mB2COrderService = mB2COrderService;
    }

    public P2POrderService getP2pOrderService() {
        return p2pOrderService;
    }

    public void setP2pOrderService(P2POrderService p2pOrderService) {
        this.p2pOrderService = p2pOrderService;
    }
}
