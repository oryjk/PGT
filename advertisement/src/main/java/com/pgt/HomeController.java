package com.pgt;

import com.pgt.product.bean.ProductType;
import com.pgt.search.service.AdvertisementSearchEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 4/6/16.
 */
@RequestMapping("/")
@Controller
public class HomeController {
    @Autowired
    private AdvertisementSearchEngineService advertisementSearchEngineService;

    @RequestMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("/index/index");
        //b2c
        buildB2CRecommendProducts(modelAndView);
        //p2p
        buildP2PRecommendProducts(modelAndView);
        return modelAndView;
    }

    public void buildB2CRecommendProducts(ModelAndView modelAndView){
        List<Map<String,Object>> advertisementB2CList = advertisementSearchEngineService.findRecommendProducts(ProductType.PRODUCT);
        modelAndView.addObject("advertisementB2CList", advertisementB2CList);
    }

    public void buildP2PRecommendProducts(ModelAndView modelAndView){
        List<Map<String,Object>> advertisementP2PList = advertisementSearchEngineService.findRecommendProducts(ProductType.TENDER);
        modelAndView.addObject("advertisementP2PList", advertisementP2PList);
    }
}
