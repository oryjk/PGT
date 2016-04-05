package com.pgt.banner;

import com.alibaba.fastjson.JSONObject;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.BannerQuery;
import com.pgt.common.bean.Media;
import com.pgt.configuration.Configuration;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
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
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private  MediaService mediaService;
    @Autowired
    private Configuration configuration;

    @RequestMapping(value = "/onlinepawmbannerList", method = RequestMethod.GET)
    public ModelAndView bannerList(ModelAndView modelAndView,@RequestParam(value = "currentIndex", required = false) Integer currentIndex) {
        Category categoryRequest = new Category();
        categoryRequest.setType(CategoryType.livepawn_categroy_banner);
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        Integer total = categoryService.queryCategoryTotal(categoryRequest);
        if (total > 0) {
            paginationBean.setTotalAmount(total);
            List<Category> categoriesList = categoryService.queryCategories(categoryRequest, paginationBean);
            for(int i = 0;i<categoriesList.size();i++){
                Integer mediaid = categoriesList.get(i).getFrontMedia().getId();
                Media media = mediaService.findMedia(mediaid, MediaType.livepawn_categroy_banner);
                LOGGER.debug("" + media.getPath());
                categoriesList.get(i).setDescription(media.getPath());
            }
            modelAndView.addObject("categoriesList", categoriesList);
            LOGGER.debug("" + JSONObject.toJSONString(categoriesList));
        }
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
        category.setType(CategoryType.livepawn_categroy_banner);
        String categoryId = categoryService.createCategory(category);
        LOGGER.debug("" + categoryId);
        LOGGER.debug("" + category.getFrontMedia().getId());
        LOGGER.debug("" + category.getName());
        modelAndView.setViewName("redirect:/banner/onlinepawmbannerList");
        return modelAndView;
    }
}