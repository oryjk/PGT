package com.pgt.backup;

import com.alibaba.fastjson.JSONObject;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liqiang on 16-2-19.
 */
@Component
public class BackupImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackupImpl.class);
    private static final String url = "/home/liqiang/Desktop/";
    @Autowired
    private ComboPooledDataSource dataSource;

    public boolean add(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process process = rt.exec("mysqldump -u root -proot mp");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuffer sb = new StringBuffer();
            while ((line=bufferedReader.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
            process.waitFor();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
            StringBuffer filename = new StringBuffer();
            filename.append(url).append(df.format(new Date()));
            FileWriter fw = new FileWriter(filename.toString() + ".sql");
            fw.write(sb.toString());
            fw.close();
            process.destroy();
            LOGGER.debug("backup database");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List findall(){
        File files = new File(url);
        File[] tempList = files.listFiles();
        List list = new ArrayList();
        for(File f:tempList){
            if(f.getName().matches(".*.sql$")){
                Map map = new HashMap();
                map.put("name", f.getName());
                map.put("size", f.length()/1024 + "kb");
                map.put("create_time",  f.getName().split("_")[0]);
                list.add(map);
            }
        }
        return list;
    }

    public boolean recover(String filename){
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setSchemas("mp"); // 设置接受flyway进行版本管理的多个数据库
        flyway.setEncoding("UTF-8"); // 设置sql脚本文件的编码
        flyway.clean();
        LOGGER.debug("clean database");
        try {
            Runtime rt = Runtime.getRuntime();
            StringBuffer sb = new StringBuffer();
            sb.append(url).append(filename);
            LOGGER.debug("recover is " + sb.toString());
            Process process = rt.exec("mysql -uroot -proot mp <" + sb.toString());
            process.waitFor();
            LOGGER.debug("recover database");
            process.destroy();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean del(String filename){
        StringBuffer filepath = new StringBuffer();
        filepath.append(url).append(filename);
        File file = new File(filepath.toString());
        if (file.isFile() && file.exists()) {
            file.delete();
            return  true;
        }
        return false;
    }

    public void setDataSource(ComboPooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
