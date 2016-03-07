package com.pgt.controller;

import com.pgt.controller.bean.War;
import com.pgt.controller.bean.Wars;
import com.pgt.controller.constant.WarConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
@Scope("singleton")
@RequestMapping("/gradle")
public class UtilsHomeController {
    @Autowired
    WarConstant warConstant;

    @RequestMapping("home")
    public ModelAndView main(ModelAndView modelAndView){
        try{
           //方法三
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(warConstant.GRADLEURL);
            Properties properties = new Properties();
            properties.load(inputStream);
            Enumeration es =  properties.propertyNames();
            List list = new ArrayList<>();
            while (es.hasMoreElements()){
                String name = (String) es.nextElement();
                Map<String, Object> map = new HashMap<>();
                map.put(warConstant.NAME, name);
                map.put(warConstant.VALUE, properties.getProperty(name));
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
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(warConstant.GRADLEURL);
            Properties properties = new Properties();
            properties.load(inputStream);
            String path = this.getClass().getClassLoader().getResource(warConstant.GRADLEURL).getPath();
            for (War obj:wars.getWars()){
                OutputStream out = new FileOutputStream(path);
                properties.setProperty(obj.getName(), obj.getValue());
                properties.store(out, "Update " + obj.getName() + " name");
            }
            String execueWarPath = this.getClass().getClassLoader().getResource(warConstant.EXETUTESHURL).getPath();
            Process  chmod = Runtime.getRuntime().exec("chmod +x " + execueWarPath);
            chmod.waitFor();
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