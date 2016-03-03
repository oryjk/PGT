package com.pgt.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangxiaodong on 16-3-3.
 */
public class TenderDateUtils {


    public static Integer getDaySub(String beginDateStr,String endDateStr)
    {
        Integer day=0;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate;
        Date endDate;
        try
        {
            beginDate = format.parse(beginDateStr);
            endDate= format.parse(endDateStr);
            day=(int) (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        return day;
    }

}
