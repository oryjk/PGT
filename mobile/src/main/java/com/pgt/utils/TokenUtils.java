package com.pgt.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaodong on 16-1-15.
 */
public class TokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);

    /**
     * The method create tokenNumber
     * @param phoneId
     * @param username
     * @return tokenNumber
     */
     public static String generateToken(String phoneId,String username){
         LOGGER.debug("The phoneId is{},and username is{}",phoneId,username);
         if(StringUtils.isEmpty(phoneId)){
             LOGGER.debug("The phoneId is empty");
             return null;
         }
         if(StringUtils.isEmpty(username)){
             LOGGER.debug("The username is empty");
             return null;
         }
         String str = phoneId+username;
         String token = DigestUtils.md5Hex(str);
         return token;
     }

    /**
     * @return The method return a month time later
     */
    public static Date getTokenOutTime(){

        Calendar c=Calendar.getInstance();
        Date date=new Date();
        c.setTime(date);
        c.add(Calendar.MONTH,1);
        Date tokenDate = c.getTime();
        return tokenDate;
    }


}
