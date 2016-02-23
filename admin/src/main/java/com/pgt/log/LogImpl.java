package com.pgt.log;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liqiang on 16-2-22.
 */
@Component
public class LogImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogImpl.class);
    //日志存放URL
    private static final String LOGURL = "/home/liqiang/Desktop/";

    //所有日志
    public List findall(){
        File files = new File(LOGURL);
        File[] tempList = files.listFiles();
        List list = new ArrayList<>();
        for(File f:tempList){
            if(f.getName().matches(".*.log$")){
                Map map = new HashMap<>();
                map.put("name", f.getName());
                list.add(map);
            }
        }
        return list;
    }

    //删除
    public boolean del(String filename){
        StringBuffer filepath = new StringBuffer();
        filepath.append(LOGURL).append(filename);
        File file = new File(filepath.toString());
        if (file.isFile() && file.exists()) {
            file.delete();
            return  true;
        }
        return false;
    }

    //查看日志
    public String findfile(String filename){
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader(LOGURL + filename));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line=bufferedReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            return JSONObject.toJSONString(sb.toString());
        }catch(Exception e){
            LOGGER.debug("file is not found");
            e.printStackTrace();
        }
        return null;
    }

    //删除当前时间x天前的日志（jetty logs目录下日志格式  2015_02_20 . stderrout . log）
    public boolean delLogFileBeforeTONow(int day){
        File files = new File(LOGURL);
        File[] tempList = files.listFiles();
        List list = new ArrayList();
        for(File f:tempList){
            if(f.getName().matches(".*.log$")){
                SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd");

                //before time
                int before_time = day * 24 * 60 * 60 * 1000;
                Date date = new Date();
                date.setTime(new Date().getTime() - before_time);

                //file log time
                int f_year = Integer.parseInt(f.getName().split("\\.")[0].split("_")[0]);
                int f_mouth = Integer.parseInt(f.getName().split("\\.")[0].split("_")[1]);
                int f_day = Integer.parseInt(f.getName().split("\\.")[0].split("_")[2]);
                Calendar cal = Calendar.getInstance();
                cal.set(f_year, f_mouth, f_day);
                cal.before(date);

                if(cal.getTime().after(date) == true){
                    f.delete();
                    return true;
                }

                LOGGER.debug("file time" + cal.getTime().after(date));
                LOGGER.debug("before time" + df.format(date));
            }
        }
        return false;
    }
}
