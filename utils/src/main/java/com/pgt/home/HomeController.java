package com.pgt.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-3-4.
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public ModelAndView mian(ModelAndView modelAndView){
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
