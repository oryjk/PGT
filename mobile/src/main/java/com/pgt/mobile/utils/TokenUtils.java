package com.pgt.mobile.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Encoder;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by xiaodong on 16-1-15.
 */
public class TokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtils.class);

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
         String token = new BASE64Encoder().encode(str.getBytes());
         return token;
     }


    public static Date getTokenOutTime(){

        Calendar c=Calendar.getInstance();
        Date date=new Date();
        c.setTime(date);
        c.add(Calendar.MONTH,1);
        Date tokenDate = c.getTime();
        return tokenDate;
    }


}
