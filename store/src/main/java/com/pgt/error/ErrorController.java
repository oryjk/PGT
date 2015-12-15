package com.pgt.error;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by carlwang on 12/10/15.
 */
@RestController
public class ErrorController {
    @RequestMapping(value = "/notFound")
    public ModelAndView error(ModelAndView modelAndView) {
        modelAndView.setViewName("/error/404");
        return modelAndView;
    }
}
