package com.pgt.shipping.controller;

import com.pgt.address.bean.AddressInfo;
import com.pgt.address.bean.Store;
import com.pgt.address.service.AddressInfoService;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.controller.CartMessages;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.city.bean.Province;
import com.pgt.city.service.CityService;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.integration.alipay.AlipayConfig;
import com.pgt.integration.alipay.AlipayConstants;
import com.pgt.inventory.LockInventoryException;
import com.pgt.inventory.service.InventoryService;
import com.pgt.mail.MailConstants;
import com.pgt.mail.service.MailService;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.PaymentService;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.shipping.bean.ShippingMethod;
import com.pgt.shipping.bean.ShippingVO;
import com.pgt.shipping.service.ShippingService;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import com.pgt.utils.WebServiceConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author ethanli
 */
@RestController
@RequestMapping("/checkout")
public class ShippingController implements CartMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingController.class);
    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private AddressInfoService addressInfoService;
    @Autowired
    private ShippingService shippingService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CityService cityService;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserInformationService userInformationService;

    @Autowired
    private AlipayConfig alipayConfig;

    @Resource(name = "inventoryService")
    private InventoryService inventoryService;

    @Resource(name = "shoppingCartService")
    private ShoppingCartService mShoppingCartService;

    @Resource(name = "transactionLogService")
    private TransactionLogService transactionLogService;

    @Autowired
    private PaymentService paymentService;


    @RequestMapping(value = "/shipping", method = {RequestMethod.GET})
    public ModelAndView shipping(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            String redirectUrl = "redirect:" + urlConfiguration.getLoginPage() + "?redirect="
                    + urlConfiguration.getShippingPage();
            mav.setViewName(redirectUrl);
            user = new User();
            mav.addObject(user);
            return mav;
        }
        Order order = getOrderService().getSessionOrder(request);
        getShoppingCartService().checkInventory(order);
        if (getOrderService().isInvalidOrder(user, order)) {
            String redirectUrl = "redirect:" + urlConfiguration.getShoppingCartPage();
            mav.setViewName(redirectUrl);
            return mav;
        }
        if (!getShoppingCartService().checkCartItemCount(order)) {
            String redirectUrl = "redirect:" + urlConfiguration.getShoppingCartPage();
            mav.setViewName(redirectUrl);
            return mav;
        }
        if (order.getShippingVO() == null) {
            ShippingVO shipping = getShippingService().findShippingByOrderId(String.valueOf(order.getId()));
            order.setShippingVO(shipping);
        }
        List<AddressInfo> addressInfoList = getAddressInfoService().queryAddressByUserId(user.getId().intValue());
        if (user.getDefaultAddressId() != null) {
            addressInfoList = getAddressInfoService().sortAddress(user.getDefaultAddressId(), addressInfoList);
            AddressInfo defaultAddress = getAddressInfoService().findAddress(user.getDefaultAddressId());
            mav.addObject("defaultAddress", defaultAddress);
            if (order.getShippingVO() == null) {
                getShippingService().addAddress(user.getDefaultAddressId(), order);
            }
        }
        List<Integer> productIds = getShippingService().getProductIdsFromOrder(order);
        if (productIds != null && !productIds.isEmpty()) {
            List<Store> stores = getShippingService().findStoreByProductIds(productIds);
            mav.addObject("storeList", stores);
        }

        List<Province> provinceList = getCityService().getAllProvince();
        mav.addObject("provinceList", provinceList);
        mav.setViewName(urlConfiguration.getShippingPage());
        mav.addObject("addressInfoList", addressInfoList);
        mav.addObject("checkoutOrder", order);
        String error = request.getParameter("error");
        if (StringUtils.isNotBlank(error)) {
            mav.addObject("error", error);
        }
        return mav;
    }


    @RequestMapping(value = "/ajaxShipping", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity ajaxShipping(HttpServletRequest request, HttpSession session) {

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_SUCCESS);
        if (user == null) {
            // CODE NEED_LOGIN
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NEED_LOGIN);
            new ResponseEntity(result, HttpStatus.OK);
        }
        Order order = getOrderService().getSessionOrder(request);
        if (getOrderService().isInvalidOrder(user, order)) {
            // CODE NOT_YOUR_ORDER
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NOT_YOUR_ORDER);
            new ResponseEntity(result, HttpStatus.OK);
        }
        if (!getShoppingCartService().checkCartItemCount(order)) {
            // CODE TO_MANY_ITEM
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_TOO_MANY_ITEM);
            new ResponseEntity(result, HttpStatus.OK);
        }
        if (order.getShippingVO() == null) {
            ShippingVO shipping = getShippingService().findShippingByOrderId(String.valueOf(order.getId()));
            order.setShippingVO(shipping);
        }
        List<AddressInfo> addressInfoList = getAddressInfoService().queryAddressByUserId(user.getId().intValue());
        if (user.getDefaultAddressId() != null) {
            addressInfoList = getAddressInfoService().sortAddress(user.getDefaultAddressId(), addressInfoList);
            AddressInfo defaultAddress = getAddressInfoService().findAddress(user.getDefaultAddressId());
            result.put(WebServiceConstants.NAME_DEFAULT_ADDRESS, defaultAddress);
            if (order.getShippingVO() == null) {
                getShippingService().addAddress(user.getDefaultAddressId(), order);
            }
        }
        List<Integer> productIds = getShippingService().getProductIdsFromOrder(order);
        if (productIds != null && !productIds.isEmpty()) {
            List<Store> stores = getShippingService().findStoreByProductIds(productIds);
            result.put(WebServiceConstants.NAME_STORE_LIST, stores);
        }

        result.put(WebServiceConstants.NAME_ADDRESS_LIST, addressInfoList == null ? Collections.emptyList() : addressInfoList);
        result.put(WebServiceConstants.NAME_ORDER_INFO, order);
        return new ResponseEntity(result, HttpStatus.OK);
    }


    @RequestMapping(value = "/addAddressToOrder", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addAddressToOrder(@RequestParam("addressInfoId") String addressInfoId,
                                                 HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Order order = getOrderService().getSessionOrder(request);
        if (getOrderService().isInvalidOrder(user, order)) {
            LOGGER.error("Falied to add address to order-{}.", request.getParameter(CartConstant.ORDER_ID));
            map.put("redirectUrl", urlConfiguration.getShoppingCartPage());
            map.put("success", "false");
            return map;
        }
        if (StringUtils.isBlank(addressInfoId)) {
            LOGGER.error("Need parameter 'addressInfoId' when adding address to order-{}.", order.getId());
            map.put("success", "false");
            return map;
        }
        getShippingService().addAddress(Integer.parseInt(addressInfoId), order);
        map.put("success", "true");
        // map.put("savedAddress", address.getProvince() + address.getCity() +
        // address.getDistrict() + " "
        // + address.getAddress() + " " + address.getPhone() + " " +
        // address.getName());
        return map;
    }

    @RequestMapping(value = "/addPickup", method = {RequestMethod.POST})
    @ResponseBody
    public Map<String, Object> addPickup(@Validated ShippingMethod shippingMethod, BindingResult bindingResult,
                                         HttpServletRequest request, HttpSession session) {
        LOGGER.debug("Starting to add a pick up shipping.");
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Order order = getOrderService().getSessionOrder(request);
        if (getOrderService().isInvalidOrder(user, order)) {
            LOGGER.error("Falied to add pickup shipping to order-{}.", request.getParameter(CartConstant.ORDER_ID));
            map.put("redirectUrl", urlConfiguration.getShoppingCartPage());
            map.put("success", "false");
            return map;
        }
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Map<String, String> errors = new HashMap<String, String>();
            for (FieldError err : fieldErrors) {
                errors.put(err.getField(), err.getDefaultMessage());
            }
            map.put("success", "false");
            map.put("errors", errors);
            return map;
        }
        getShippingService().addShippingMethod(shippingMethod, order);
        map.put("success", "true");
        return map;
    }

    @RequestMapping(value = "/redirectToPayment", method = RequestMethod.GET)
    public ModelAndView redirectToPayment(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (null == user) {
            mav.setViewName("redirect:" + urlConfiguration.getLoginPage());
            return mav;
        }
        Order order = getOrderService().getSessionOrder(request);
        if (null == order) {
            mav.setViewName("redirect:" + urlConfiguration.getShoppingCartPage());
            return mav;
        }

        if (getOrderService().hasUncompleteOrder(user.getId().intValue(), getShoppingCartService().getShoppingCartConfiguration()
                .getMaxItemCount4Cart())) {
            mav.setViewName("redirect:" + urlConfiguration.getShippingPage());
            mav.addObject(CartConstant.ORDER_ID, order.getId());
            mav.addObject("error", "HAS.UNSUBMIT.ORDER");
            return mav;
        }

        if (getOrderService().isInvalidOrder(user, order)) {
            LOGGER.error("Current order is invalid and will redirect to shopping cart.");
            mav.setViewName("redirect:" + urlConfiguration.getShoppingCartPage());
            return mav;
        }
        if (!getShoppingCartService().checkCartItemCount(order)) {
            String redirectUrl = "redirect:" + urlConfiguration.getShoppingCartPage();
            mav.setViewName(redirectUrl);
            return mav;
        }
        if (!hasShippingOnOrder(order)) {
            mav.setViewName("redirect:" + urlConfiguration.getShippingPage());
            mav.addObject(CartConstant.ORDER_ID, order.getId());
            return mav;
        } else {
            getInventoryService().ensureTransaction();
            try {
                getInventoryService().lockInventory(order);
                order.setStatus(OrderStatus.FILLED_SHIPPING);
                order.setSubmitDate(new Date());
                getOrderService().updateOrder(order);
            } catch (LockInventoryException e) {
                String oosProdId = StringUtils.join(e.getOosProductIds(), "_");
                mav.setViewName("redirect:" + urlConfiguration.getShoppingCartPage() + "?oosProdId=" + oosProdId);

                return mav;
            } catch (Exception e) {
                String message = CartMessages.ERROR_INV_CHECK_FAILED;
                LOGGER.error("lock inventory failed", e);
                mav.setViewName("redirect:" + urlConfiguration.getShoppingCartPage() + "?error=" + message);
                return mav;
            } finally {
                getInventoryService().commit();
            }
        }
        sendEmail(user, order);
        request.removeAttribute(CartConstant.CURRENT_ORDER);
        mav.setViewName("redirect:/payment/gateway");
        request.getSession().setAttribute(CartConstant.ORDER_KEY_PREFIX + order.getId(), order);
        mav.addObject(CartConstant.ORDER_ID, order.getId());
        return mav;
    }

    @RequestMapping(value = "/ajaxRedirectToPayment", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity ajaxRedirectToPayment(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        Map<String, Object> result = new HashMap<String, Object>();
        if (null == user) {
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NEED_LOGIN);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        Order order = getOrderService().getSessionOrder(request);
        if (null == order) {
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_BACK_TO_CART);
            return new ResponseEntity(result, HttpStatus.OK);
        }

        boolean isPayOnly = false;
        String payOnly = request.getParameter(CartConstant.PAY_ONLY);
        if (StringUtils.isNotBlank(payOnly)) {
            isPayOnly = Boolean.valueOf(payOnly);
        }

        if (!isPayOnly && getOrderService().hasUncompleteOrder(user.getId().intValue(), getShoppingCartService().getShoppingCartConfiguration()
                .getDefaultOrderType())) {
            LOGGER.error("Has incomplete order.");
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_HAS_INCOMPLETE_ORDER);
            return new ResponseEntity(result, HttpStatus.OK);
        }

        if (getOrderService().isInvalidOrder(user, order)) {
            LOGGER.error("Current order is invalid and will redirect to shopping cart.");
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NOT_YOUR_ORDER);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        if (!getShoppingCartService().checkCartItemCount(order)) {
            LOGGER.error("TOO MANY ITEM IN ORDER");
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_TOO_MANY_ITEM);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        if (!hasShippingOnOrder(order)) {
            LOGGER.error("No shipping.");
            result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NO_SHIPPING);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            getInventoryService().ensureTransaction();
            try {
                getInventoryService().lockInventory(order);
                order.setStatus(OrderStatus.FILLED_SHIPPING);
                order.setSubmitDate(new Date());
                getOrderService().updateOrder(order);
                PaymentGroup paymentGroup = paymentService.maintainPaymentGroup(order, PaymentConstants.METHOD_ALIPAY);
                //create transaction
                Transaction transaction = new Transaction();
                transaction.setAmount(order.getTotal());
                transaction.setCreationDate(new Date());
                transaction.setUpdateDate(new Date());
                transaction.setOrderId(Long.valueOf(order.getId()));
                transaction.setPaymentGroupId(paymentGroup.getId());
                transaction.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
                transaction.setPaymentType(PaymentConstants.PAYMENT_TYPE_ALIPAY);
                paymentService.createTransaction(transaction);


                //create transactionLog
                TransactionLog transactionLog = new TransactionLog();
                transactionLog.setOrderId(Long.valueOf(order.getId()));
                transactionLog.setUserId(Long.valueOf(order.getUserId()));
                transactionLog.setPaymentGroupId(paymentGroup.getId());
                transactionLog.setTransactionId(transaction.getId());
                getTransactionLogService().createTransactionLog(transactionLog);
                result.put(WebServiceConstants.NAME_ORDER_INFO, order);

                result.put(WebServiceConstants.NAME_TRANSACTION_ID, transactionLog.getId());
                result.put(AlipayConstants.OUT_TRADE_NO, alipayConfig.getOrderIdPrefix() + transactionLog.getId());

            } catch (LockInventoryException e) {
                String oosProdId = StringUtils.join(e.getOosProductIds(), "_");
                result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_NO_INVENTORY);
                return new ResponseEntity(result, HttpStatus.OK);
            } catch (Exception e) {
                String message = CartMessages.ERROR_INV_CHECK_FAILED;
                LOGGER.error("lock inventory failed," + message, e);
                result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_CHECK_INVENTORY_FAILED);
                return new ResponseEntity(result, HttpStatus.OK);
            } finally {
                getInventoryService().commit();
            }
        }
        result.put(WebServiceConstants.NAME_CODE, WebServiceConstants.CODE_SUCCESS);
        sendEmail(user, order);
        request.removeAttribute(CartConstant.CURRENT_ORDER);
        request.getSession().setAttribute(CartConstant.ORDER_KEY_PREFIX + order.getId(), order);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public boolean hasShippingOnOrder(Order order) {
        if (order == null) {
            return false;
        }
        if (order.getShippingVO() == null) {
            return false;
        }
        if (order.getShippingVO().getShippingAddress() != null) {
            return true;
        }
        return order.getShippingVO().getShippingMethod() != null;
    }

    public void sendEmail(User user, Order order) {
        try {
            UserInformation userInformation = userInformationService.queryUserInformation(user);
            if (userInformation == null || userInformation.getPersonEmail() == null) {
                LOGGER.info(
                        "Current user has not add user information so that cannot send place order email to he/she. User id is:{}.",
                        user.getId());
                return;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("user", user);
            map.put("order", order);
            getMailService().sendEmail(MailConstants.SUBJECT_PLACE_ORDER, map, MailConstants.TEMPLATE_PLACE_ORDER,
                    userInformation.getPersonEmail());
        } catch (Exception e) {
            LOGGER.error("Failed to send place order email to user:" + user.getId() + ".", e);
        }
    }

    public AddressInfoService getAddressInfoService() {
        return addressInfoService;
    }

    public void setAddressInfoService(AddressInfoService addressInfoService) {
        this.addressInfoService = addressInfoService;
    }

    public ShippingService getShippingService() {
        return shippingService;
    }

    public void setShippingService(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
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

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public UserInformationService getUserInformationService() {
        return userInformationService;
    }

    public void setUserInformationService(final UserInformationService pUserInformationService) {
        userInformationService = pUserInformationService;
    }

    public TransactionLogService getTransactionLogService() {
        return transactionLogService;
    }

    public void setTransactionLogService(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }
}
