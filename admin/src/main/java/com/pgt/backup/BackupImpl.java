package com.pgt.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liqiang on 16-2-19.
 */
@Component
public class BackupImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger(BackupImpl.class);
    private static final String url = "/home/liqiang/Desktop/";
    public void add(){
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
            sb.append(url).append(df.format(new Date()));
            FileWriter fw = new FileWriter(filename.toString());
            fw.write(sb.toString());
            fw.close();
            process.destroy();
            LOGGER.debug("backup database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean del(String filename){
        StringBuffer filepath = new StringBuffer();
        filepath.append(url).append(filename);
        File file = new File(filepath.toString());
        return false;
    }
}
