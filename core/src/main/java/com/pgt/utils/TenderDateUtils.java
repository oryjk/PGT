package com.pgt.utils;

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

}
