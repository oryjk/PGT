package com.pgt.order.controller;

import com.pgt.cart.bean.*;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.Role;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.mail.service.MailService;
import com.pgt.order.bean.B2COrderSearchVO;
import com.pgt.order.service.B2COrderService;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Yove on 12/21/2015.
 */
@RequestMapping("/order")
@RestController
public class B2COrderController extends InternalTransactionBaseController implements OrderStatus {

    private static final Logger LOGGER = LoggerFactory.getLogger(B2COrderController.class);

    @Resource(name = "B2COrderService")
    private B2COrderService mB2COrderService;

    @Autowired
    private ResponseBuilderFactory mResponseBuilderFactory;

    @Autowired
    private MailService mMailService;

    @RequestMapping(value = "/order-list", method = RequestMethod.GET)
    public ModelAndView listB2COrders(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                      @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
                                      @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
                                      @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
                                      @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc,
                                      @RequestParam(value = "id", required = false) String id,
                                      @RequestParam(value = "userName", required = false) String userName,
                                      @RequestParam(value = "priceBeg", required = false) String priceBeg,
                                      @RequestParam(value = "priceEnd", required = false) String priceEnd,
                                      @RequestParam(value = "submitTimeBeg", required = false) String submitTimeBeg,
                                      @RequestParam(value = "submitTimeEnd", required = false) String submitTimeEnd) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
        long caLong = RepositoryUtils.safeParse2LongId(capacity);
        LOGGER.debug("Query b2c-orders at index: {} with capacity: {} by sort filed: {} and asc: {}", ciLong, caLong, sortFieldName, asc);
        B2COrderSearchVO searchVO = B2COrderSearchVO.getInstance().setOrderId(id).setUserName(userName);
        searchVO.setPriceBeg(priceBeg).setPriceEnd(priceEnd);
        searchVO.setSubmitTimeBeg(submitTimeBeg).setSubmitTimeEnd(submitTimeEnd);
        LOGGER.debug("Query b2c-orders with search conditions: {}", searchVO);
        InternalPaginationBuilder ipb = new InternalPaginationBuilder();
        InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setSortFieldName(sortFieldName).setAsc(asc)
                .createInternalPagination();
        getB2COrderService().queryB2COrderPage(searchVO, pagination);
        ModelAndView mav = new ModelAndView("/b2c-order/b2c-order-list");
        mav.addObject(ResponseConstant.B2C_ORDER_PAGE, pagination);
        return mav;
    }

    @RequestMapping(value = "/order-info", method = RequestMethod.GET)
    public ModelAndView loadOrderHistoryDetails(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                                @RequestParam(value = "id") String orderId) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        int orderIdInt = RepositoryUtils.safeParseId(orderId);
        Order order = getB2COrderService().loadOrder(orderIdInt);
        ModelAndView mav = new ModelAndView("/b2c-order/b2c-order");
        mav.addObject(ResponseConstant.B2C_ORDER, order);
        return mav;
    }

    @RequestMapping(value = "/change-order-status")
    public ModelAndView changeOrderStatus(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                          @RequestParam(value = "id", required = true) String id,
                                          @RequestParam(value = "status", required = true) String status) {
        // permission verify
        boolean pass = verifyPermission(pRequest, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("redirect:/b2c-order/b2c-orders");
        int idInt = RepositoryUtils.safeParseId(id);
        Order order = getB2COrderService().loadOrder(idInt);
        if (order == null) {
            LOGGER.debug("Cannot find order to change status with id: {}", id);
            mav.addObject(ResponseConstant.ERROR_MSG, "Cannot find order with id: " + id);
            return mav;
        }
        int statusInt = RepositoryUtils.safeParseId(status);
        LOGGER.debug("Try to change status from: {} to: {} for order: {}", order.getStatus(), status, id);
        TransactionStatus ts = ensureTransaction();
        try {
            boolean result = false;
            if (statusInt == getB2COrderService().getUnpaidStatus()) {
                result = getB2COrderService().updateOrder2UnpaidStatus(order);
            } else if (statusInt == getB2COrderService().getCompleteStatus()) {
                result = getB2COrderService().updateOrder2CompleteStatus(order);
            } else if (statusInt == getB2COrderService().getCancelStatus()) {
                result = getB2COrderService().updateOrder2CancelStatus(order);
            }
            if (!result) {
                ts.setRollbackOnly();
            }
            getmMailService().sendUpdateOrderEmail(order, result, statusInt);
        } catch (Exception e) {
            LOGGER.error("Cannot change status to: {} for order: {}", status, id, e);
            ts.setRollbackOnly();
        } finally {
            if (ts.isRollbackOnly()) {
                mav.addObject(ResponseConstant.ERROR_MSG, "Change order status failed for order with id: " + id);
            }
            getTransactionManager().commit(ts);
        }
        return mav;
    }


    @RequestMapping(value = "/ajax-change-order-status")
    @ResponseBody
    public ResponseEntity ajaxChangeOrderStatus(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                                @RequestParam(value = "id", required = true) String id,
                                                @RequestParam(value = "status", required = true) String status) {
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        // permission verify
        boolean pass = verifyPermission(pRequest, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR);
        if (!pass) {
            return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
        }
        // main logic
        int idInt = RepositoryUtils.safeParseId(id);
        Order order = getB2COrderService().loadOrder(idInt);
        if (order == null) {
            rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, "Cannot find order with id: " + id);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        int statusInt = RepositoryUtils.safeParseId(status);
        LOGGER.debug("Try to change status from: {} to: {} for order: {}", order.getStatus(), status, id);
        TransactionStatus ts = ensureTransaction();
        try {
            boolean result = false;
            if (statusInt == getB2COrderService().getUnpaidStatus()) {
                result = getB2COrderService().updateOrder2UnpaidStatus(order);
            } else if (statusInt == getB2COrderService().getCompleteStatus()) {
                result = getB2COrderService().updateOrder2CompleteStatus(order);
            } else if (statusInt == getB2COrderService().getCancelStatus()) {
                result = getB2COrderService().updateOrder2CancelStatus(order);
            }
            if (!result) {
                ts.setRollbackOnly();
            }
            getmMailService().sendUpdateOrderEmail(order, result, statusInt);
        } catch (Exception e) {
            LOGGER.error("Cannot change status to: {} for order: {}", status, id, e);
            ts.setRollbackOnly();
        } finally {
            if (ts.isRollbackOnly()) {
                rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, "Change order status failed for order with id: " + id);
            } else {
                String statusText = getB2COrderService().getOrderStatusMapping().get(statusInt);
                Pair<String, String> statusPair = Pair.of(String.valueOf(statusInt), statusText);
                rb.setSuccess(true).setData(statusPair);
            }
            getTransactionManager().commit(ts);
        }
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/delivery")
    public ModelAndView redirect2DeliveryPage(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                              @RequestParam(value = "id", required = true) String id,
                                              @RequestParam(value = "cid", required = true) String cid) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("redirect:/b2c-order/order-info?id=" + id);
        int cidInt = RepositoryUtils.safeParseId(cid);
        CommerceItem ci = getB2COrderService().loadCommerceItem(cidInt);
        if (ci == null || !RepositoryUtils.idIsValid(ci.getId())) {
            LOGGER.debug("Cannot find commerce item with id: {}", cid);
            return mav;
        }
        mav.setViewName("/b2c-order/delivery");
        mav.addObject(ResponseConstant.COMMERCE_ITEM, ci);
        return mav;
    }

    @RequestMapping(value = "/make-delivery")
    public ModelAndView makeDelivery(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                     @RequestParam(value = "id", required = true) String id,
                                     @RequestParam(value = "cid", required = true) String cid,
                                     @RequestParam(value = "deliveryTimeStr", required = false) String deliveryTimeStr,
                                     Delivery delivery) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("redirect:/order/delivery?id=" + id + "&cid=" + cid);
        int cidInt = RepositoryUtils.safeParseId(cid);
        CommerceItem ci = getB2COrderService().loadCommerceItem(cidInt);
        if (ci == null) {
            return mav;
        }
        delivery.setDeliveryTime(deliveryTimeStr);
        TransactionStatus status = ensureTransaction();
        try {
            getB2COrderService().createDelivery(delivery);
            Order order = getB2COrderService().loadOrder(ci.getOrderId());
            //by Carl Wang, cause we just have one shop,so I do it, when next version, should separate Delivery. And now, as long as one of them
            // had delivery,the order status is TRANSIT.
            getB2COrderService().updateOrder2TransitStatus(order);
            getmMailService().sendDeliveryEmail(order, ci, delivery);
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Cannot make delivery for commerce item: {} of order: {}", cid, id, e);
        } finally {
            if (!status.isRollbackOnly()) {
                ci.setDelivery(delivery);
            }
            getTransactionManager().commit(status);
        }
        mav.addObject(ResponseConstant.COMMERCE_ITEM, ci);
        mav.setViewName("/b2c-order/b2c-order-list");
        return mav;
    }

    @RequestMapping(value = "/make-receive")
    public ModelAndView makeReceive(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                    @RequestParam(value = "id", required = true) String id,
                                    @RequestParam(value = "cid", required = true) String cid) {
        // permission verify
        boolean pass = verifyPermission(pRequest);
        if (!pass) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("redirect:/order/order-info?id=" + id);
        int cidInt = RepositoryUtils.safeParseId(cid);
        CommerceItem ci = getB2COrderService().loadCommerceItem(cidInt);
        if (ci == null) {
            return mav;
        }
        TransactionStatus status = ensureTransaction();
        try {
            boolean result = getB2COrderService().markCommerceItemReceived(cidInt);
            if (!result) {
                status.setRollbackOnly();
            }

        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Cannot mark received for commerce item: {} of order: {}", cid, id, e);
        } finally {
            getTransactionManager().commit(status);
        }
        return mav;
    }

    public B2COrderService getB2COrderService() {
        return mB2COrderService;
    }

    public void setB2COrderService(final B2COrderService pB2COrderService) {
        mB2COrderService = pB2COrderService;
    }

    public ResponseBuilderFactory getResponseBuilderFactory() {
        return mResponseBuilderFactory;
    }

    public void setResponseBuilderFactory(final ResponseBuilderFactory pResponseBuilderFactory) {
        mResponseBuilderFactory = pResponseBuilderFactory;
    }

    public MailService getmMailService() {
        return mMailService;
    }

    public void setmMailService(MailService mMailService) {
        this.mMailService = mMailService;
    }

}
