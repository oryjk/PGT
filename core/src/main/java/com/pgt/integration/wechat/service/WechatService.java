package com.pgt.integration.wechat.service;

import com.pgt.cart.bean.Order;
import com.pgt.integration.wechat.WechatConfig;
import com.pgt.integration.wechat.WechatConstant;
import com.pgt.integration.wechat.bean.NotifyData;
import com.pgt.integration.wechat.bean.UnifiedOrderReqData;
import com.pgt.integration.wechat.bean.UnifiedOrderResData;
import com.pgt.payment.PaymentConstants;
import com.pgt.payment.bean.PaymentGroup;
import com.pgt.payment.bean.Transaction;
import com.pgt.payment.bean.TransactionLog;
import com.pgt.payment.service.PaymentService;
import com.pgt.payment.service.TransactionLogService;
import com.pgt.user.bean.User;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.service.UnifiedOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2016/3/20.
 */
public class WechatService {


    private static final int UPPERCASE_START_INDEX = 65;
    private static final int LOWERCASE_START_INDEX = 97;
    private static final int CHARACTER_AMOUNT = 26;
    private static final int NUMBER_START_INDEX = 48;
    private static final int NUMBER_AMOUNT = 10;
    private static final int NONCE_LENGTH = 30;

    private WechatConfig config;
    @Resource(name = "transactionLogService")
    private TransactionLogService transactionLogService;
    @Resource(name = "paymentService")
    private PaymentService paymentService;

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatService.class);

    public void initalConfig() {
        if (null == Configure.getKey()) {
            Configure.setKey(getConfig().getKey());
        }
        if (null == Configure.getAppid()) {
            Configure.setAppID(getConfig().getAppId());
        }
        if (null == Configure.getCertLocalPath()) {
            Configure.setCertLocalPath(getConfig().getCertLocalPath());
        }
        if (null == Configure.getCertPassword()) {
            Configure.setCertPassword(getConfig().getCertPassword());
        }
        if (null == Configure.getMchid()) {
            Configure.setMchID(getConfig().getMchId());
        }
        if (null == Configure.getNotifyURL()) {
            Configure.setNotifyURL(getConfig().getNotifyURL());
        }
    }

    public UnifiedOrderResData pay(Order order, User user) throws Exception {
        initalConfig();
        long costTimeStart = System.currentTimeMillis();
        PaymentGroup paymentGroup = new PaymentGroup();
        Date now = new Date();
        paymentGroup.setOrderId(Long.valueOf(order.getId()));
        paymentGroup.setAmount(order.getTotal());
        paymentGroup.setCreateDate(now);
        paymentGroup.setUpdateDate(now);
        paymentGroup.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
        paymentGroup.setType(PaymentConstants.PAYMENT_TYPE_WECHAT);
        getPaymentService().maintainPaymentGroup(paymentGroup);

        Transaction transaction = new Transaction();
        transaction.setAmount(order.getTotal());
        transaction.setCreationDate(now);
        transaction.setUpdateDate(now);
        transaction.setOrderId(Long.valueOf(order.getId()));
        transaction.setPaymentGroupId(paymentGroup.getId());
        transaction.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
        transaction.setPaymentType(PaymentConstants.PAYMENT_TYPE_WECHAT);
        getPaymentService().createTransaction(transaction);

        TransactionLog transactionLog = new TransactionLog();
        transactionLog.setPaymentType(WechatConstant.TYPE);
        transactionLog.setServiceName(WechatConstant.SERVICE_SCAN_PAY);
        transactionLog.setUserId(Long.valueOf(order.getUserId()));
        transactionLog.setOrderId(Long.valueOf(order.getId()));
        transactionLog.setTransactionId(transaction.getId());
        transactionLog.setPaymentGroupId(paymentGroup.getId());
        getTransactionLogService().createTransactionLog(transactionLog);

        UnifiedOrderReqData data = constructRequestData(order, transactionLog);

        UnifiedOrderService service = new UnifiedOrderService();
        String responseString = service.request(data);
        long costTimeEnd = System.currentTimeMillis();
        long totalTimeCost = costTimeEnd - costTimeStart;
        LOGGER.debug("totalTimeCost: " +  totalTimeCost);
        LOGGER.debug("responseString: " + responseString);
        UnifiedOrderResData resData = null;
        if (Signature.checkIsSignValidFromResponseString(responseString)) {
            resData = (UnifiedOrderResData) Util.getObjectFromXML(responseString, UnifiedOrderResData.class);
            transactionLog.setInbound(responseString);
            getTransactionLogService().updateTransactionLog(transactionLog);
            String returnCode = resData.getReturn_code();
            if (!WechatConstant.SUCCESS.equals(returnCode)) {
                transaction.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
            } else {
                transaction.setStatus(PaymentConstants.PAYMENT_STATUS_PROCCESSING);
                transaction.setTrackingNo(resData.getPrepay_id());
            }
        } else {
            transaction.setStatus(PaymentConstants.PAYMENT_STATUS_FAILED);
        }
        getPaymentService().updateTransaction(transaction);
        return resData;
    }



    public boolean handleNotify(String notifyString) throws IOException, SAXException, ParserConfigurationException {
        if (!Signature.checkIsSignValidFromResponseString(notifyString)) {
            return false;
        }
        NotifyData data = (NotifyData)Util.getObjectFromXML(notifyString, NotifyData.class);
        if (WechatConstant.SUCCESS.equals(data.getResult_code())) {
            // check order is paid
            String transactionLogId = data.getOut_trade_no();
        }
        return true;

    }



    private UnifiedOrderReqData constructRequestData(Order order, TransactionLog transactionLog) {


        String body = "支付订单：" + order.getId();
        String outTradeNo = transactionLog.getId().toString();
        int totalFee = (int)order.getTotal() * 100;
        String ipAddress = "";
        String notifyUrl = getConfig().getNotifyURL();
        UnifiedOrderReqData data = new UnifiedOrderReqData();
        data.setAppid(getConfig().getAppId());
        data.setMch_id(getConfig().getMchId());
        data.setBody(body);
        data.setTotal_fee(totalFee);
        data.setSpbill_create_ip(ipAddress);
        data.setNotify_url(notifyUrl);
        data.setOut_trade_no(outTradeNo);
        data.setTrade_type(getConfig().getTradeType());
        data.setNonce_str(generateNonce());
        data.setSign(Signature.getSign(data.toMap()));
        return data;
    }

    public static  String generateNonce() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < NONCE_LENGTH; i++) {
            int randomInt =Math.abs( random.nextInt());
             if (randomInt % 2 == 1) {
                 result.append((char)(randomInt % NUMBER_AMOUNT + NUMBER_START_INDEX));
             } else {
                 if (i%2 == 1) {
                     result.append((char)(randomInt % CHARACTER_AMOUNT + UPPERCASE_START_INDEX));
                 } else {
                     result.append((char)(randomInt % CHARACTER_AMOUNT + LOWERCASE_START_INDEX));
                 }
             }

        }
        System.out.println(result.toString());
        return result.toString();
    }


    public WechatConfig getConfig() {
        return config;
    }

    public void setConfig(WechatConfig config) {
        this.config = config;
    }

    public TransactionLogService getTransactionLogService() {
        return transactionLogService;
    }

    public void setTransactionLogService(TransactionLogService transactionLogService) {
        this.transactionLogService = transactionLogService;
    }

    public PaymentService getPaymentService() {
        return paymentService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
