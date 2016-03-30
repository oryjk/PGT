package com.pgt.payment.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgt.cart.bean.OrderStatus;
import com.pgt.inventory.LockInventoryException;
import com.pgt.inventory.service.InventoryService;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionQueryBean;
import com.pgt.utils.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.cart.bean.Order;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.UserOrderService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.service.PaymentService;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.user.bean.User;

import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Resource(name = "paymentService")
    private PaymentService paymentService;

    @RequestMapping(value = "/queryTrans")
    public ModelAndView queryTransaction(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        String orderIdStr = pRequest.getParameter("orderId");
        String typeStr  = pRequest.getParameter("type");
        String stateStr = pRequest.getParameter("state");
        String trackNo = pRequest.getParameter("trackNo");
        String startTimeStr = pRequest.getParameter("startTime");
        String endTimeStr = pRequest.getParameter("endTime");
        String currentIndexStr = pRequest.getParameter("currentIndex");
        String capacityStr = pRequest.getParameter("capacity");
        Integer orderId = null;
        if (StringUtils.isNotBlank(orderIdStr) && StringUtils.isNumeric(orderIdStr)) {
            orderId = Integer.valueOf(orderIdStr);
        }
        if(StringUtils.isBlank(trackNo)) {
            trackNo = null;
        }
        Integer type = null;
        if (StringUtils.isNotBlank(typeStr) && StringUtils.isNumeric(typeStr)) {
            type = Integer.valueOf(typeStr);
        }



        Integer state = null;
        if (StringUtils.isNotBlank(stateStr)) {
            if (PaymentConstants.PAYMENT_STATUS_SUCCESS == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_SUCCESS;
            }
            if (PaymentConstants.PAYMENT_STATUS_PROCCESSING == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_PROCCESSING;
            }
            if (PaymentConstants.PAYMENT_STATUS_FAILED == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_FAILED;
            }
        }

        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotBlank(startTimeStr)) {
            try {
                startTime = DateUtils.parseDate(startTimeStr, "MM/dd/yyyy HH:mm:ss");
            } catch (ParseException e) {
                throw new IllegalArgumentException("Start time format is not correct (MM/dd/yyyy HH:mm:ss)");
            }
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            try {
                endTime = DateUtils.parseDate(endTimeStr, "MM/dd/yyyy HH:mm:ss");
            } catch (ParseException e) {
                throw new IllegalArgumentException("Start time format is not correct (MM/dd/yyyy HH:mm:ss)");
            }
        }
        int currentIndex = 0;
        if (StringUtils.isNotBlank(currentIndexStr)) {
            if (StringUtils.isNumeric(currentIndexStr)) {
                currentIndex = Integer.valueOf(currentIndexStr);
            }
        }
        int capacity = PaginationBean.DEFAULT_CAPACITY;
        if (StringUtils.isNotBlank(capacityStr)) {
            if (StringUtils.isNumeric(capacityStr)) {
                capacity = Integer.valueOf(capacityStr);
            }
        }
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(capacity);

        TransactionQueryBean queryBean = new TransactionQueryBean();
        queryBean.setOrderId(orderId);
        queryBean.setPaymentType(type);
        queryBean.setState(state);
        queryBean.setTrackNo(trackNo);
        queryBean.setStartTime(startTime);
        queryBean.setEndTime(endTime);
        List<Transaction> result = getPaymentService().queryTransaction(queryBean, paginationBean);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/payment/queryTransaction");
        modelAndView.addObject("result", result);
        modelAndView.addObject("paginationBean", paginationBean);
        modelAndView.addObject("queryBean",queryBean);
        return modelAndView;
    }


    @RequestMapping(value = "/generateReport")
    public void generateReport(HttpServletRequest pRequest, HttpServletResponse pResponse) throws IOException {
        String orderIdStr = pRequest.getParameter("orderId");
        String typeStr  = pRequest.getParameter("type");
        String stateStr = pRequest.getParameter("state");
        String trackNo = pRequest.getParameter("trackNo");
        String startTimeStr = pRequest.getParameter("startTime");
        String endTimeStr = pRequest.getParameter("endTime");
        Integer orderId = null;
        if (StringUtils.isNotBlank(orderIdStr) && StringUtils.isNumeric(orderIdStr)) {
            orderId = Integer.valueOf(orderIdStr);
        }
        if(StringUtils.isBlank(trackNo)) {
            trackNo = null;
        }
        Integer type = null;
        if (StringUtils.isNotBlank(typeStr) && StringUtils.isNumeric(typeStr)) {
            type = Integer.valueOf(typeStr);
        }

        Integer state = null;
        if (StringUtils.isNotBlank(stateStr)) {
            if (PaymentConstants.PAYMENT_STATUS_SUCCESS == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_SUCCESS;
            }
            if (PaymentConstants.PAYMENT_STATUS_PROCCESSING == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_PROCCESSING;
            }
            if (PaymentConstants.PAYMENT_STATUS_FAILED == Integer.valueOf(stateStr)) {
                state = PaymentConstants.PAYMENT_STATUS_FAILED;
            }
        }

        Date startTime = null;
        Date endTime = null;
        if (StringUtils.isNotBlank(startTimeStr)) {
            try {
                startTime = DateUtils.parseDate(startTimeStr, "MM/dd/yyyy HH:mm:ss");
            } catch (ParseException e) {
                throw new IllegalArgumentException("Start time format is not correct (MM/dd/yyyy HH:mm:ss)");
            }
        }
        if (StringUtils.isNotBlank(endTimeStr)) {
            try {
                endTime = DateUtils.parseDate(endTimeStr, "MM/dd/yyyy HH:mm:ss");
            } catch (ParseException e) {
                throw new IllegalArgumentException("Start time format is not correct (MM/dd/yyyy HH:mm:ss)");
            }
        }
        OutputStream os = pResponse.getOutputStream();
        try {
            pResponse.reset();
            pResponse.setHeader("Content-Disposition", "attachment; filename=transactionReport.xlsx");
            pResponse.setContentType("application/octet-stream; charset=utf-8");
            getPaymentService().generateReport(orderId, type, state, trackNo, startTime, endTime, pResponse.getOutputStream());
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }



    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

}
