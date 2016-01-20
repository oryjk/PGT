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
 * Created by hd on 16-1-16.
 */
@Controller
@RequestMapping("/web")
public class RecentBrowseController {
    private static final Logger LOGGER	= LoggerFactory.getLogger(RecentBrowseController.class);
    @RequestMapping("/recentBrowse")
    public ModelAndView recentBrowse(ModelAndView modelAndView,HttpSession session){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            LOGGER.debug("User should login firstly when accessing recent Browse.");
            modelAndView.setViewName("redirect:/web/wlogin");
            return modelAndView;

        }
        modelAndView.setViewName("my-account/recentbrowse");
        return modelAndView;
    }

}
