package com.pgt.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangxiaodong on 16-3-3.
 */
public class TenderDateUtils {


    public static Integer getDaySub(Date beginDate,Date endDate)
    {
        Integer day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        try
        {
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(endDate.getTime());
            String endStringDate= format.format(c.getTime());
            endDate=format.parse(endStringDate);

            c.setTimeInMillis(beginDate.getTime());
            String beginStringDate=format.format(c.getTime());
            beginDate = format.parse(beginStringDate);

            day=(int) (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return day;
    }

    public static Date convert(String value) {

        String date = "" + value;
        long time = Long.valueOf(date);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String s = dateformat.format(c.getTime());

        try {
        return   dateformat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
