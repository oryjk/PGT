package com.pgt.utils;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * Created by carlwang on 11/19/15.
 */

@Service
public class ErrorMsgUtil {
    private static ReloadableResourceBundleMessageSource messageSource;


    public static String getMsg(String key, String[] args, Locale locale) {
        return messageSource.getMessage(key, args, locale);
    }


    public ReloadableResourceBundleMessageSource getMessageSource() {
        return this.messageSource;
    }

    @Resource(name = "messageSource")
    public void setMessageSource(
            ReloadableResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
