package com.pgt.backup;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqiang on 16-2-19.
 */
@Controller
@RequestMapping("backup")
public class BackupController {
    @Autowired
    private BackupImpl backupImpl;

    @RequestMapping("main")
    public ModelAndView main(ModelAndView modelAndView){
        modelAndView.setViewName("backup/backup");
        return  modelAndView;
    }

    @RequestMapping("findall")
    public @ResponseBody String findall(){
        return JSONObject.toJSONString(backupImpl.findall());
    }

    @RequestMapping("add")
    public @ResponseBody String add(){
        boolean info = backupImpl.add();
        Map map = new HashMap<>();
        map.put("info" , info);
        map.put("data", backupImpl.findall());
        return JSONObject.toJSONString(map);
    }

    @RequestMapping("del")
    public @ResponseBody String del(@RequestParam String filename){
        Map map = new HashMap<>();
        map.put("info" ,  backupImpl.del(filename));
        map.put("data", backupImpl.findall());
        return JSONObject.toJSONString(map);
    }

    @RequestMapping("recover")
    public @ResponseBody String recover(@RequestParam String filename){
        Map map = new HashMap<>();
        map.put("info" ,  backupImpl.recover(filename));
        map.put("data", backupImpl.findall());
        return JSONObject.toJSONString(map);
    }
}
