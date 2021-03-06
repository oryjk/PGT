package com.pgt.integration.alipay;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.controller.TransactionBaseController;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.sms.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.service.PaymentService;

@Controller
@RequestMapping("/alipay")
public class AlipayController extends TransactionBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AlipayController.class);
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Resource(name = "orderService")
    private OrderService orderService;
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private SmsService smsService;

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @Resource(name = "responseBuilderFactory")
    private ResponseBuilderFactory responseBuilderFactory;

    @RequestMapping(value = "/request", method = RequestMethod.GET)
    public ModelAndView requestAlipay(HttpServletRequest request, HttpSession session) {
        ModelAndView model = null;
        String orderId = request.getParameter("orderId");
        Order order = getOrderService().loadOrder(Integer.parseInt(orderId));
//		if (order == null || order.getCommerceItemCount() == 0) {
        if (order == null) {
            LOGGER.error("no order or commerceItem");
            model = new ModelAndView("redirect:/payment/gateway");
            return model;
        }
        try {
            getPaymentService().ensureTransaction();
            PaymentGroup paymentGroup = getPaymentService().maintainPaymentGroup(order, PaymentConstants.METHOD_ALIPAY);
            Transaction transaction = new Transaction();
            transaction.setAmount(order.getTotal());
            transaction.setCreationDate(new Date());
            transaction.setUpdateDate(new Date());
            transaction.setOrderId(Long.valueOf(order.getId()));
            transaction.setPaymentGroupId(paymentGroup.getId());
            transaction.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
            transaction.setPaymentType(PaymentConstants.PAYMENT_TYPE_ALIPAY);
            getPaymentService().createTransaction(transaction);
            TransactionLog transactionLog = getAlipayService().createAlipayTransactionLog(transaction.getId(), session, order, paymentGroup);
            Map<String, String> paramsMap = getAlipayService().buildRequestMap(order, transactionLog.getId());
            ObjectMapper mapper = new ObjectMapper();
            transactionLog.setOutbound(mapper.writeValueAsString(paramsMap));
            getAlipayService().updateAlipayTransactionLog(transactionLog);
            String tradeNo = paramsMap.get(AlipayConstants.OUT_TRADE_NO);
            transaction.setTrackingNo(tradeNo);
            getPaymentService().updateTransaction(transaction);
            LOGGER.debug("Collected all required parameters and submit form to alipay payment gateway.");
            model = new ModelAndView("checkout/alipayForm");
            model.addObject("alipayParams", paramsMap);
            model.addObject("formAction", alipayService.getAlipayConfig().getAlipayUrl());
        } catch (Exception e) {
            LOGGER.error("handle alipay error ", e);
            model = new ModelAndView("/payment/gateway");
            getPaymentService().setAsRollback();
        } finally {
            getPaymentService().commit();
        }
        return model;
    }

    @RequestMapping(value = "/return", method = RequestMethod.GET)
    public ModelAndView handleAlipayReturn(HttpServletRequest request, HttpSession session,
                                           RedirectAttributes redirectAttributes) {
        Integer orderId = getAlipayService().getOrderIdFromNotify(request);
        LOGGER.debug("The order id is {}.", orderId);
        Order order = getOrderService().loadOrder(orderId);
        boolean success = getAlipayService().verifyResult(request);
        LOGGER.debug("{} for Alipay.", success);
        ModelAndView mav = new ModelAndView();
        if (success) {
            handleSuccessfulAlipayNotify(request, order, false);
            mav.setViewName("redirect:/payment/complete?orderId=" + orderId);
            redirectAttributes.addFlashAttribute(PaymentConstants.PAID_SUCCESS_FLAG, Constants.TRUE);
            return mav;
        } else {
            LOGGER.error("Method handleAlipayReturn(): Failed to pay the order-{} by alipay.", orderId);
        }
        mav = new ModelAndView("redirect:/payment/gateway");
//        mav.addObject("order", order);
        mav.addObject(CartConstant.ORDER_ID, order.getId());
        return mav;
    }

    @RequestMapping(value = "/ajaxReturn", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity handleAlipayReturn(HttpServletRequest request,
                                             RedirectAttributes redirectAttributes) {
        Integer orderId = getAlipayService().getOrderIdFromNotify(request);
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        ResponseEntity responseEntity = new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        LOGGER.debug("The order id is {}.", orderId);
        Order order = getOrderService().loadOrder(orderId);
        handleSuccessfulAlipayNotify(request, order, false);
        redirectAttributes.addFlashAttribute(PaymentConstants.PAID_SUCCESS_FLAG, Constants.TRUE);
        rb.setSuccess(true);
        return responseEntity;
    }


    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    public void handleAlipayNotify(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Integer orderId = getAlipayService().getOrderIdFromNotify(request);
        LOGGER.debug("The order id is {}.", orderId);
        Order order = getOrderService().loadOrder(orderId);
        boolean success = getAlipayService().verifyResult(request);
        LOGGER.debug("{} for Alipay.", success);
        if (success) {
            try {
                handleSuccessfulAlipayNotify(request, order, true);
                PrintWriter writer = response.getWriter();
                writer.print("success");
                writer.flush();
            } catch (IOException e) {
                LOGGER.error("Cannot print success to response for alipay's notifycation", e);
            }
        } else {
            LOGGER.error("Method handleAlipayNotify(): Failed to pay the order-{} by alipay.", orderId);
        }

    }

    public void handleSuccessfulAlipayNotify(HttpServletRequest request, Order order, Boolean isNotify) {
        TransactionStatus status = ensureTransaction();
        try {

            Integer orderId = getAlipayService().getOrderIdFromNotify(request);
            LOGGER.debug("Find the paymentGroup with order id is {}.", orderId);
            PaymentGroup paymentGroup = getPaymentService().findPaymentGroupByOrderId(orderId);
            LOGGER.debug("The notify is {}.", isNotify);
//            if (isNotify) {
            Transaction transaction = getPaymentService().findTransactionByTrackingNumber(request.getParameter(AlipayConstants.OUT_TRADE_NO));
            if (ObjectUtils.isEmpty(transaction)) {
                LOGGER.error("Can not find the transaction with trade_no is {}.", request.getParameter(AlipayConstants.OUT_TRADE_NO));
                return;
            }
            transaction.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
//            }
            if (paymentGroup == null) {
                LOGGER.error("Cannot get paymentgroup by order id-{} after successing to pay the order by alipay.",
                        orderId);
                return;
            }
            if (PaymentConstants.PAYMENT_STATUS_SUCCESS == paymentGroup.getStatus()) {
                LOGGER.info("Had saved alipay result for order-{} before.", orderId);
                return;
            }
            getAlipayService().updateAlipayTransactionLog(request);
            getAlipayService().saveAlipayResult(request);
            paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
            getPaymentService().updatePaymentGroup(paymentGroup);
            LOGGER.debug("Begin to set order status is PAID,the order id is {}", order.getId());
            if (order != null) {
                LOGGER.debug("Set the order status PAID,the value is 30.");
                order.setStatus(OrderStatus.PAID);
                getShoppingCartService().updateOrder(order);
                smsService.sendPaidOrderMessage(order);
                LOGGER.info("Successed to pay order-{} by alipay.", orderId);
                return;
            }
            LOGGER.debug("Can not find the order with id is {}.", order.getId());
        } catch (Exception e) {
            LOGGER.error("The error message is {}.", e.getMessage());
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
    }

    public AlipayService getAlipayService() {
        return alipayService;
    }

    public void setAlipayService(AlipayService alipayService) {
        this.alipayService = alipayService;
    }

    public URLConfiguration getUrlConfiguration() {
        return urlConfiguration;
    }

    public void setUrlConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public ResponseBuilderFactory getResponseBuilderFactory() {
        return responseBuilderFactory;
    }

    public void setResponseBuilderFactory(ResponseBuilderFactory responseBuilderFactory) {
        this.responseBuilderFactory = responseBuilderFactory;
    }
}
