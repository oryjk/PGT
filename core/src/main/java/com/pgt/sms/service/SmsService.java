package com.pgt.sms.service;

import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.integration.yeepay.YeePayConstants;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 12/1/15.
 */
@Service
public class SmsService {

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
