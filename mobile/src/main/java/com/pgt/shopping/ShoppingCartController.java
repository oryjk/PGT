package com.pgt.shopping;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liqiang on 16-1-22.
 */
@Controller
@RequestMapping("shoppingCart")
public class ShoppingCartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @RequestMapping("shoppingCartList")
    public ModelAndView shoppingCartList(ModelAndView modelAndView, HttpServletRequest request){
        LOGGER.debug("view is shopping-cart page");
        modelAndView.setViewName("shopping-cart/shopping-cart");
        return modelAndView;
    }
}
