package com.pgt.log;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-2-22.
 */
@Controller
@RequestMapping("log")
public class LogController {
    @Autowired
    private LogImpl log;

    @RequestMapping("main")
    public ModelAndView main(ModelAndView modelAndView){
        modelAndView.setViewName("log/log");
        return modelAndView;
    }

    @RequestMapping("findall")
    public @ResponseBody String  findall(){
        return JSONObject.toJSONString(log.findall());
    }

    @RequestMapping("findfile")
    public @ResponseBody String findfile(@RequestParam String filename){
        return  log.findfile(filename);
    }

    @RequestMapping("del")
    public @ResponseBody String del(@RequestParam String  filename){
        log.del(filename);
        return JSONObject.toJSONString(log.findall());
    }

    @RequestMapping("delbyday")
    public @ResponseBody String day(@RequestParam int day){
        log.delLogFileBeforeTONow(day);
        return JSONObject.toJSONString(log.findall());
    }
}
