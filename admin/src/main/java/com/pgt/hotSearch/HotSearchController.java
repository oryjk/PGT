package com.pgt.hotSearch;

import com.pgt.hot.bean.HotSearch;
import javafx.application.HostServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by xiaodong on 16-1-30.
 */
@RequestMapping("/hotSearch")
public class HotSearchController {

    @Autowired
    private HostServices  hostServices;

    private static final Logger LOGGER = LoggerFactory.getLogger(HotSearchController.class);



    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView query(ModelAndView  modelAndView){

       return modelAndView;
    }


    @RequestMapping(value = "/addHotSearch",method = RequestMethod.POST)
    public ModelAndView addHotSearch(ModelAndView modelAndView, HotSearch hotSearch){

        if(ObjectUtils.isEmpty(hotSearch)){
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }

        return modelAndView;
    }








}
