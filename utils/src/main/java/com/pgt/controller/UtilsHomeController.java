package com.pgt.controller;

import com.alibaba.fastjson.JSONObject;
import com.pgt.controller.bean.War;
import com.pgt.controller.bean.Wars;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by fei on 2016/3/5.
 */
@Controller
@RequestMapping("/gradle")
public class UtilsHomeController {
    @RequestMapping("home")
    public ModelAndView main(ModelAndView modelAndView, HttpServletRequest request){
        try{
           //方法三
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("gradle.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            Enumeration es =  properties.propertyNames();
            List list = new ArrayList<>();
            while (es.hasMoreElements()){
                String name = (String) es.nextElement();
                Map<String, Object> map = new HashMap<>();
                map.put("name", name);
                map.put("value", properties.getProperty(name));
                list.add(map);
            }
            modelAndView.addObject("wars", list);
        }catch(Exception e){
            e.printStackTrace();
        }
        modelAndView.setViewName("home/home");
        return  modelAndView;
    }

    @RequestMapping("execute")
    public ModelAndView warExecute(ModelAndView modelAndView, Wars wars){
        try{
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("gradle.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            String path = this.getClass().getClassLoader().getResource("gradle.properties").getPath();
            for (War obj:wars.getWars()){
                OutputStream out = new FileOutputStream(path);
                properties.setProperty(obj.getName(), obj.getValue());
                System.out.println(JSONObject.toJSONString(properties));
                properties.store(out, "Update " + obj.getName() + " name");
            }
            String execueWarPath = this.getClass().getClassLoader().getResource("executewar.sh").getPath();
            Process  process = Runtime.getRuntime().exec(execueWarPath + " " + properties.getProperty("PROJECTURL"));
            process.waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
        modelAndView.setViewName("home/success");
        return modelAndView;
    }

}

//方法一 flyway
// Scanner scanner = new Scanner(this.getClass().getClassLoader());
// Location location =new Location("");
//Resource[] resources = scanner.scanForResources(location, "",".properties");
// System.out.println("11111111111111111111111" +  resources[0].loadAsString("UTF-8"));
//方法二 file read
// System.out.println("11111111111111111111111" +  this.getClass().getClassLoader().getResource("gradle.properties").getPath());