package com.pgt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xiaodong on 16-1-22.
 */
public class UserAgentUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAgentUtils.class);

    private static  String[] keywords = { "Mozilla", " ucbrowser", "OBUA", "Nokia", "JUC", "IUC","Opera","MQQBrowser"};

    public static boolean  isWebBrowser(String userAgent){
        for (String agent:keywords) {
            if(userAgent.contains(agent.toLowerCase())){
                LOGGER.debug("The mobile agent is {}",agent);
                return true;
            }
        }
     return false;
    }


}
