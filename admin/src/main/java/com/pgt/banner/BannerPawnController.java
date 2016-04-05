package com.pgt.banner;

import com.pgt.category.bean.Category;
import com.pgt.common.bean.BannerQuery;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liqiang on 16-4-5.
 */
@Controller
@RequestMapping("/banner")
public class BannerPawnController  extends InternalTransactionBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BannerPawnController.class);
    @RequestMapping(value = "/onlinepawmbannerList", method = RequestMethod.GET)
    public ModelAndView bannerList(ModelAndView modelAndView) {
        modelAndView.setViewName("pawn-online/pawn-online");
        return modelAndView;
    }


    @RequestMapping(value = "onlinepawmadd", method = RequestMethod.GET)
    public ModelAndView add(ModelAndView modelAndView,HttpServletRequest pRequest){
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("pawn-online/pawn-online-add");
        return modelAndView;
    }

    @RequestMapping(value = "onlinepawmadd", method = RequestMethod.POST)
    public ModelAndView addpost(ModelAndView modelAndView,Category category, HttpServletRequest pRequest){
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("" + category.getFrontMedia().getId());
        LOGGER.debug("" + category.getName());
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("pawn-online/pawn-online-add");
        return modelAndView;
    }
}