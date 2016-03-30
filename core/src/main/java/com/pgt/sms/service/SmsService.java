package com.pgt.sms.service;

import com.pgt.cart.bean.Order;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 12/1/15.
 */
@Service
public class SmsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);
    @Autowired
    private Configuration configuration;


    public String sendLoginSms(String phoneNumber, String code) {
        try {
            return sendSms(phoneNumber, configuration.getSmsLoginContent(), code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String sendRegisterSms(String phoneNumber, String code) {
        try {
            return sendSms(phoneNumber, configuration.getSmsRegisterContent(), code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String sendResetPasswordSms(String phoneNumber, String code) {
        try {
            return sendSms(phoneNumber, configuration.getSmsResetPasswordContent(), code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }


    public String sendOnlinePawnSms(String phoneNumber, String code) {
        try {
            return sendSms(phoneNumber, configuration.getSmsOnlinePawnContent(), code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public String sendOnlinePawnSmsToMe(String phoneNumber, String content) {
        try {
            return sendSms(phoneNumber, content, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }

    public void sendPaidOrderMessage(Order order) {
        List<String> serviceTels = configuration.getServiceTels();
        if (CollectionUtils.isEmpty(serviceTels)) {
            LOGGER.debug("The service tels is empty, can not send the order message.");
            return;
        }
        serviceTels.stream().forEach(s -> {
            try {
                sendSms(s, configuration.getSmsOrderContent(), String.valueOf(order.getId()));
            } catch (IOException e) {
                LOGGER.error("Send order message has IO error.{}.", e.getMessage());
                e.printStackTrace();
            }
        });
    }


    private String sendSms(String phoneNumber, String content, String code) throws IOException {
        String requestUrl = configuration.getSmsUrl();

        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(requestUrl);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair(Constants.SMS_USERNAME, configuration.getSmsUsername()));
        nvps.add(new BasicNameValuePair(Constants.SMS_PASSWORD, configuration.getSmsPassword()));
        nvps.add(new BasicNameValuePair(Constants.SMS_CONTENT, content + code));
        nvps.add(new BasicNameValuePair(Constants.SMS_PHONE, phoneNumber));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        CloseableHttpResponse response = httpclient.execute(httpPost);

        try {
            HttpEntity entity = response.getEntity();
            String responseContent = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return responseContent;
        } finally {
            response.close();
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


}
