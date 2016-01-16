package com.pgt.web.search;

import com.alibaba.fastjson.JSONObject;
import com.pgt.mobile.search.controller.ESSearchMobileController;
import com.pgt.mobile.search.controller.EssearchBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-1-16.
 */
@Controller
public class SearchController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);
    @Autowired
    ESSearchMobileController esSearchMobileController;

    @RequestMapping("/web/search")
    public ModelAndView search(ModelAndView modelAndView){
        EssearchBean essearchBean = new EssearchBean();
        essearchBean.setTerm("玉石");
        modelAndView.addObject("data", esSearchMobileController.query(essearchBean));
        modelAndView.setViewName("search/search");
        LOGGER.debug("*********************************" + JSONObject.toJSONString(esSearchMobileController.query(essearchBean)));
        return  modelAndView;
    }
}
