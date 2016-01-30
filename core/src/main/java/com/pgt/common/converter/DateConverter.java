package com.pgt.common.converter;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by carlwang on 10/24/15.
 */
public class DateConverter implements Converter<String, Date> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DateConverter.class);
    @Override
    public Date convert(String source) {

        if (StringUtils.isEmpty(source)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setLenient(false);
        try {
            return simpleDateFormat.parse(source);
        } catch (ParseException e) {
            LOGGER.error("ParseException when parse date type,message is {}.", e.getMessage());
        }
        return null;
    }

}
