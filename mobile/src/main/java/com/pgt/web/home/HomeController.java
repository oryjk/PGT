package com.pgt.web.home;

import com.alibaba.fastjson.JSONObject;
import com.pgt.mobile.home.controller.HomeMobileController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by liqiang on 16-1-14.
 */
@Controller
public class HomeController{
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    HomeMobileController homeMobileController;

    @RequestMapping("home")
    public ModelAndView home(ModelAndView modelAndView){
        Map<String,Object> dataMap = homeMobileController.index();
        modelAndView.setViewName("home/home");
        LOGGER.debug("******************************1*" + dataMap.get("hotProducts"));
        LOGGER.debug("******************************" + JSONObject.toJSONString(dataMap));
        modelAndView.addObject("data", dataMap);
        return modelAndView;
    }
}
