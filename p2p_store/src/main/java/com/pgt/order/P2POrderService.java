package com.pgt.order;

import com.pgt.cart.bean.CommerceItem;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.OrderStatus;
import com.pgt.cart.bean.OrderType;
import com.pgt.cart.dao.P2PMapper;
import com.pgt.cart.dao.UserOrderDao;
import com.pgt.cart.exception.OrderPersistentException;
import com.pgt.cart.service.OrderService;
import com.pgt.cart.service.ShoppingCartService;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayHelper;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.service.PaymentService;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.dao.ProductMapper;
import com.pgt.tender.bean.Tender;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Samli on 2016/1/18.
 */

@Service(value = "p2pOrderService")
public class P2POrderService extends OrderService {

    public static final long MILLISECOND_ONE_DAY = 1000 * 60 * 60 * 24;

    public static final long DAYS_ONE_YEAR = 365;

    private static final Logger LOGGER = LoggerFactory.getLogger(P2POrderService.class);

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private P2PMapper p2PMapper;

    @Resource(name = "completeTransactionYeepay")
    private DirectYeePay directTransactionYeepay;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserOrderDao userOrderDao;

    public Pair<Order, P2PInfo> createP2POrder(User user, Tender tender, List<Product> relatedProducts, String[] productIds,
                                               String[] quantities) throws OrderPersistentException {
        LOGGER.debug("==================== Start method createP2POrder ====================");
        LOGGER.debug("tender : " + tender.toString());
        P2PInfo info = new P2PInfo();
        info.setTenderId(tender.getTenderId());
        info.setPawnShopOwnerId(tender.getPawnShopOwnerId());
        info.setPawnShopId(tender.getPawnShopId());
        info.setPostPeriod(tender.getPostPeriod());
        info.setPrePeriod(tender.getPrePeriod());
        info.setPublishDate(tender.getPublishDate());
        info.setExpectDueDate(tender.getDueDate());
        info.setUnitPrice(tender.getUnitPrice());
        info.setInterestRate(tender.getInterestRate());
        info.setHandlingFeeRate(tender.getHandlingFeeRate());
        persistenceP2PInfo(info);
        LOGGER.debug("persistenceP2PInfo infoId: " + info.getId());

        Order order = new Order();
        order.setStatus(OrderStatus.INITIAL);
        order.setType(OrderType.P2P_ORDER);
        order.setUserId(user.getId().intValue());
        order.setStatus(OrderStatus.INITIAL);
        order.setP2pInfoId(info.getId());
        order.setEstimatedShipDate(tender.getDueDate());

        // create commerceItem
        maintainCommerceItem(order, info, relatedProducts, productIds, quantities);
        calculateOrder(order, info);
        calculateP2PInfo(info, order);
        persistenceP2PInfo(info);

        // persistence order
        getShoppingCartService().persistInitialOrder(order);
        LOGGER.debug("==================== end method createP2POrder ====================");
        return Pair.of(order, info);
    }

    private void maintainCommerceItem(Order order, P2PInfo info, List<Product> relatedProducts, String[] productIds, String[] quantities) {

        List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();
        int count = 0;
        for (String productId : productIds) {
            int id = Integer.valueOf(productId);
            for (Product relatedProduct : relatedProducts) {
                if (id != relatedProduct.getProductId()) {
                    continue;
                }

                CommerceItem ci = new CommerceItem();
                if (null == relatedProduct.getSalePrice() && null == relatedProduct.getListPrice()) {
                    LOGGER.error("No price for product(id= " + relatedProduct.getProductId() + ").");
                    throw new IllegalArgumentException("NO_PRICE_FOR COMMERCE_ITEM");
                }
                ci.setAmount(relatedProduct.getSalePrice() == null ? relatedProduct.getListPrice() : relatedProduct.getSalePrice());
                ci.setIndex(count);
//                ci.setOrderId(order.getId());
                ci.setListPrice(relatedProduct.getListPrice());
                ci.setName(relatedProduct.getName());
                ci.setQuantity(Integer.valueOf(quantities[count]));
                ci.setReferenceId(relatedProduct.getProductId());
                ci.setSalePrice(relatedProduct.getSalePrice());
                // TODO Snapshotid
                // ci.setSnapshotId();
                ci.setType(CommerceItem.TYPE_P2P_NOMAL);
                commerceItems.add(ci);
            }
            count++;
        }
        order.setCommerceItems(commerceItems);

    }


    public Pair<Order, P2PInfo> createP2POrder(User user, Tender tender, List<Product> relatedProducts, String[] productIds,
                                               int placeQuantity) throws OrderPersistentException {
        LOGGER.debug("==================== Start method createP2POrder ====================");
        LOGGER.debug("tender : " + tender.toString());
        P2PInfo info = new P2PInfo();
        info.setTenderId(tender.getTenderId());
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
        persistenceP2PInfo(info);
        LOGGER.debug("persistenceP2PInfo infoId: " + info.getId());

        Order order = new Order();
        order.setStatus(OrderStatus.INITIAL);
        order.setType(OrderType.P2P_ORDER);
        order.setUserId(user.getId().intValue());
        order.setStatus(OrderStatus.INITIAL);
        order.setP2pInfoId(info.getId());

        // create commerceItem
        maintainCommerceItem(order, info, relatedProducts, productIds);
        calculateOrder(order, info);
        calculateP2PInfo(info, order);
        persistenceP2PInfo(info);

        // persistence order
        getShoppingCartService().persistInitialOrder(order);
        LOGGER.debug("==================== end method createP2POrder ====================");
        return Pair.of(order, info);
    }

    private void maintainCommerceItem(Order order, P2PInfo info, List<Product> relatedProducts, String[] productIds) {
        double investTotal = info.getUnitPrice() * info.getPlaceQuantity();
        // TODO ROUND;
        double productTotal = 0D;

        List<CommerceItem> commerceItems = new ArrayList<CommerceItem>();
        int count = 0;
        for (String productId : productIds) {
            int id = Integer.valueOf(productId);
            for (Product relatedProduct : relatedProducts) {
                if (id != relatedProduct.getProductId()) {
                    continue;
                }

                CommerceItem ci = new CommerceItem();
                if (null == relatedProduct.getSalePrice() && null == relatedProduct.getListPrice()) {
                    LOGGER.error("No price for product(id= " + relatedProduct.getProductId() + ").");
                    throw new IllegalArgumentException("NO_PRICE_FOR COMMERCE_ITEM");
                }
                ci.setAmount(relatedProduct.getSalePrice() == null ? relatedProduct.getListPrice() : relatedProduct.getSalePrice());
                ci.setIndex(count);
//                ci.setOrderId(order.getId());
                ci.setListPrice(relatedProduct.getListPrice());
                ci.setName(relatedProduct.getName());
                ci.setQuantity(1);
                ci.setReferenceId(relatedProduct.getProductId());
                ci.setSalePrice(relatedProduct.getSalePrice());
                // TODO Snapshotid
                ProductMedia media = getProductMapper().queryProductThumbnailMedia(relatedProduct.getProductId());
                if (null != media) {
                    ci.setSnapshotId(media.getId());
                }
                ci.setType(CommerceItem.TYPE_P2P_NOMAL);
                productTotal += ci.getAmount();
                //TODO ROUND
                commerceItems.add(ci);
                count++;
            }
        }

    }

    private void calculateOrder(Order order, P2PInfo info) {
        List<CommerceItem> commerceItems = order.getCommerceItems();
        double orderTotal = 0D;
        for (CommerceItem item : commerceItems) {
            LOGGER.debug("item id=" + item.getId() + "; item type=" + item.getType() + "; amount=" + item.getAmount());
            orderTotal += item.getAmount();
        }
        // TODO ROUND
        LOGGER.debug("order subtotal=" + orderTotal);
        order.setSubtotal(orderTotal);
        double shippingFee = order.getShippingFee() == null ? 0D : order.getShippingFee();
        LOGGER.debug("order shippingFee=" + shippingFee);
        order.setTotal(order.getSubtotal() + shippingFee);
    }

    private void persistenceP2PInfo(P2PInfo info) {
        if (null == info.getId()) {
            getP2PMapper().createInfo(info);
        } else {
            getP2PMapper().updateInfo(info);
        }
    }

    private void calculateP2PInfo(P2PInfo info, Order order) {
        Date payTime = info.getPayTime();
        if (null == payTime) {
            payTime = new Date();
        }
        double basePrice = order.getTotal();
        double incoming = calculateIncoming(payTime, info.getExpectDueDate(), info.getPrePeriod(), basePrice, info.getInterestRate());
        info.setExpectIncoming(incoming);
        double handlingFee = calculateHandlingFee(info, order);
        info.setHandlingFee(handlingFee);
    }

    private double calculateHandlingFee(P2PInfo info, Order order) {
        if (null == info.getUnitPrice()) {
            LOGGER.error("NO unit price for p2p info.tender(id=" + info.getTenderId() + ")");
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        if (null == info.getHandlingFeeRate()) {
            LOGGER.error("NO handling fee rate for p2p info.tender(id=" + info.getTenderId() + ")");
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        LOGGER.debug("unitPrice=" + info.getUnitPrice() + "; placeQuantity=" + info.getPlaceQuantity() + "; HandlingFee=" + info.getHandlingFeeRate
                ());
        double total = order.getTotal();
        double result = total * info.getHandlingFeeRate();
        // TODO ROUND
        LOGGER.debug(" HandlingFee result=" + result);
        return result;
    }

    /**
     * This method should be call for tree time:
     * 1. place order (order.createDate)
     * 2. payment done (payment date)
     * 3. tender done
     *
     * @param payTime
     * @param dueDate
     * @param prePeriod
     * @param basePrice
     * @param interestRate
     * @return
     */
    private double calculateIncoming(Date payTime, Date dueDate, Integer prePeriod, double basePrice, Double interestRate) {

        boolean hasIllegalArgument = false;
        if (null == payTime) {
            LOGGER.error("No publish date");
            hasIllegalArgument = true;
        }
        if (null == dueDate) {
            LOGGER.error("No expect due date");
            hasIllegalArgument = true;
        }

        if (null == prePeriod) {
            LOGGER.error("No pre period");
            hasIllegalArgument = true;
        }

        if (null == interestRate) {
            LOGGER.error("No interest rate");
            hasIllegalArgument = true;
        }
        if (hasIllegalArgument) {
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        if (dueDate.before(payTime)) {
            LOGGER.error("dueDate before publish date. dueDate(" + dueDate + "); payTime(" + payTime + ")");
            throw new IllegalArgumentException("INVALID.TENDER");
        }

        Calendar payCalendar = Calendar.getInstance();
        payCalendar.setTime(payTime);
        payCalendar.set(Calendar.HOUR, 0);
        payCalendar.set(Calendar.MINUTE, 0);
        payCalendar.set(Calendar.SECOND, 0);
        payCalendar.set(Calendar.MILLISECOND, 0);


        LOGGER.debug("payTime: " + payTime + "; dueDate" + dueDate);
        Calendar dueCalendar = Calendar.getInstance();
        dueCalendar.setTime(dueDate);
        dueCalendar.add(Calendar.DATE, 1);
        dueCalendar.set(Calendar.HOUR, 0);
        dueCalendar.set(Calendar.MINUTE, 0);
        dueCalendar.set(Calendar.SECOND, 0);
        dueCalendar.set(Calendar.MILLISECOND, 0);

        int dateGap = (int) ((dueCalendar.getTimeInMillis() - payCalendar.getTimeInMillis()) / MILLISECOND_ONE_DAY);
        if (dateGap < prePeriod) {
            LOGGER.error("dateGap < prePeriod. dateGap(" + dateGap + "); prePeriod(" + prePeriod + "); dueDate(" + dueDate + "); payTime(" +
                    payTime + ")");
            throw new IllegalArgumentException("INVALID.TENDER");
        }
        LOGGER.debug("basePrice=" + basePrice + "; interestRate=" + interestRate);
        double total = basePrice * (1 + interestRate);
        int effectDates = dateGap - prePeriod;
        LOGGER.debug("total=" + total + "; dateGap=" + dateGap + "; prePeriod=" + prePeriod + "; effectDates=" + effectDates);
        double result = total * effectDates / DAYS_ONE_YEAR;
        // TODO ROUND
        LOGGER.debug("Incoming result=" + result);
        return result;
    }


    public boolean completeTenderProduct(int productId, boolean ocuppy, Date dueDate) {
        boolean result = true;
        updateCommerceItemStatus(productId, ocuppy);
        List<Order> orders = findRelatedOrder(productId);
        if (null == orders || orders.isEmpty()) {
            //TODO LOG
            return result;
        }
        for (Order order : orders) {
            result = result && completeOrder(order, ocuppy, dueDate);
        }
        return result;
    }

    private boolean completeOrder(Order order, boolean ocuppy, Date dueDate) {
        boolean result = true;
        if (ocuppy) {
            order.setStatus(OrderStatus.PENDING_SHIPPING);
        } else {
            P2PInfo info = queryP2PInfoByOrderId(order.getId());
            double actualIncoming = calculateIncoming(info.getPayTime(), dueDate, info.getPrePeriod(), order.getTotal(), info.getInterestRate());
            info.setActualDueDate(dueDate);
            info.setActualIncoming(actualIncoming);
            p2PMapper.updateInfo(info);
            order.setStatus(OrderStatus.PENDING_TRANSFER_TO_BUYER);
        }
        updateOrder(order);
        return result;
    }


    // need a scheduler job to call this method
    public boolean giveIncomingToBuyer(Order order, P2PInfo info) {
        boolean result = false;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            Transaction transaction = new Transaction();
            transaction.setOrderId(Long.valueOf(order.getUserId()));
            transaction.setAmount(info.getActualIncoming());
            transaction.setPaymentType(PaymentConstants.PAYMENT_TYPE_YEEPAY);
            transaction.setPaymentGroupId(Long.valueOf(PaymentConstants.PLATFORM_PAYMENT_GROUP_ID));
            getPaymentService().createTransaction(transaction);

            params.put(YeePayConstants.PARAM_NAME_USER_ID, Long.valueOf(order.getUserId()));
            params.put(YeePayConstants.PARAM_NAME_ORDER_ID, transaction.getOrderId());
//		params.put(YeePayConstants.PARAM_NAME_PAYMENTGROUP_ID, transactionLog.getPaymentGroupId());


            params.put(YeePayConstants.PARAM_NAME_TRANSACTION_ID, transaction.getId());
//		String trackingNo = YeePayHelper.generateOutboundRequestNo(getConfig(), transactionLog.getId());
//		params.put(YeePayConstants.PARAM_NAME_REQUEST_NO, trackingNo);
            params.put(YeePayConstants.PARAM_NAME_MODE, YeePayConstants.MODE_CONFIRM);
            params.put(YeePayConstants.PARAM_NAME_NOTIFY_URL, getDirectTransactionYeepay().getConfig().getCompleteTransactionNotifyUrl());

            params.put(YeePayConstants.PARAM_NAME_USER_TYPE, YeePayConstants.USER_TYPE_MERCHANT);

            params.put(YeePayConstants.PARAM_NAME_PLATFORM_USER_NO, getDirectTransactionYeepay().getConfig().getTargetPlatformUserNo());
            Map<String, Object> detailsMap = new HashMap<String, Object>();
            List<Map<String, Object>> detailsList = new ArrayList<>();
            params.put(YeePayConstants.PARAM_NAME_DETAILS, detailsMap);
            detailsMap.put(YeePayConstants.PARAM_NAME_DETAIL, detailsList);
            Map<String, Object> detailMap = new HashMap<String, Object>();
            detailsList.add(detailMap);

            Map<String, Object> detail = new HashMap<String, Object>();
            detail.put(YeePayConstants.PARAM_NAME_TARGET_USER_TYPE, YeePayConstants.USER_TYPE_MEMBER);
            // TODO
            String platformUserNo = YeePayHelper.generateOutboundRequestNo(getDirectTransactionYeepay().getConfig(), Long.valueOf(order.getUserId()));
            detail.put(YeePayConstants.PARAM_NAME_TARGET_PLATFORM_USER_NO, platformUserNo);
            detail.put(YeePayConstants.PARAM_NAME_AMOUNT, info.getActualIncoming());
            detailMap.put(YeePayConstants.PARAM_NAME_DETAIL, detail);
            try {
                Map<String, String> invokResult = getDirectTransactionYeepay().invoke(params);
                transaction.setTrackingNo((String) params.get(YeePayConstants.PARAM_NAME_REQUEST_NO));
                if (YeePayConstants.CODE_SUCCESS.equals(invokResult.get(YeePayConstants.PARAM_NAME_CODE))) {

                    result = true;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (result) {
                transaction.setStatus(PaymentConstants.PAYMENT_STATUS_SUCCESS);
                order.setStatus(OrderStatus.NO_PENDING_ACTION);
            } else {
                order.setStatus(OrderStatus.TRANSFER_TO_BUYER_FAILD);
                transaction.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
            }
            getShoppingCartService().updateOrder(order);
            getPaymentService().updateTransaction(transaction);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return result;

    }

    public P2PInfo queryP2PInfoByOrderId(int id) {

        return getP2PMapper().queryInfoByOrderId(id);
    }

    private void updateCommerceItemStatus(int productId, boolean ocuppy) {
        getUserOrderDao().updateCommerceItemStatus(productId, ocuppy);
    }

    private List<Order> findRelatedOrder(int productId) {

        return getUserOrderDao().findRelatedOrder(productId);
    }


    public Tender queryTenderById(int tenderId) {
        return getP2PMapper().queryTenderById(tenderId);
    }


    public ShoppingCartService getShoppingCartService() {
        return shoppingCartService;
    }

    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    public P2PMapper getP2PMapper() {
        return p2PMapper;
    }

    public void setP2PMapper(P2PMapper p2PMapper) {
        this.p2PMapper = p2PMapper;
    }

    public DirectYeePay getDirectTransactionYeepay() {
        return directTransactionYeepay;
    }

    public void setDirectTransactionYeepay(DirectYeePay directTransactionYeepay) {
        this.directTransactionYeepay = directTransactionYeepay;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public UserOrderDao getUserOrderDao() {
        return userOrderDao;
    }

    public void setUserOrderDao(UserOrderDao userOrderDao) {
        this.userOrderDao = userOrderDao;
    }
}
