package com.pgt.web.user.controller;

import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by hd on 16-1-15.
 */
@Controller
@RequestMapping("/web")
public class PersonalinformationController {
    private static final Logger LOGGER	= LoggerFactory.getLogger(PersonalinformationController.class);
    @RequestMapping("/personalInformation")
    public ModelAndView personalinformation(ModelAndView modelAndView,HttpSession session){
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            LOGGER.debug("User should login firstly when accessing personal information.");

            modelAndView.setViewName("redirect:/web/wlogin");
            return modelAndView;

        }

        modelAndView.setViewName("my-account/personalinformation");
        return modelAndView;

    }
}
