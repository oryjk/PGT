package com.pgt.web.shopping;

import com.alibaba.fastjson.JSONObject;
import com.pgt.cart.bean.Order;
import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.constant.CartConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liqiang on 16-1-22.
 */
@Controller
public class ShoppingCartController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartController.class);

    @RequestMapping("shoppingCartList")
    public ModelAndView shoppingCartList(ModelAndView modelAndView, HttpServletRequest request){
        modelAndView.addObject("order",  request.getSession().getAttribute("order"));
        LOGGER.debug("*******************" + JSONObject.toJSONString( request.getSession().getAttribute("order")));
        modelAndView.setViewName("shopping-cart/shopping-cart");
        return modelAndView;
    }
}
