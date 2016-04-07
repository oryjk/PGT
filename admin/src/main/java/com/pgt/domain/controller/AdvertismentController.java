package com.pgt.domain.controller;

import com.pgt.category.bean.Category;
import com.pgt.home.bean.RecommendedProduct;
import com.pgt.home.service.RecommendedProductService;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.product.bean.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by liqiang on 16-4-7.
 */
@Controller
@RequestMapping("domain")
public class AdvertismentController extends InternalTransactionBaseController {
    @Autowired
    private RecommendedProductService recommendedProductService;
    @Autowired
    private RecommendedProduct recommendedProduct;

    @RequestMapping("advertismentB2C")
    public ModelAndView index(ModelAndView modelAndView){
        buildB2cList(modelAndView);
        return  modelAndView;
    }

    public void buildB2cList(ModelAndView modelAndView){
        recommendedProduct.setProductType(ProductType.PRODUCT);
        List<RecommendedProduct> recommendedProductList = recommendedProductService.queryAllRecommendedProduct(recommendedProduct);
        modelAndView.addObject("domainList", recommendedProductList);
        modelAndView.setViewName("domain/b2c/b2c-list");
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public ModelAndView createDoGet(ModelAndView modelAndView, HttpServletRequest pRequest){
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.addObject("category", new Category());
        modelAndView.setViewName("domain/advertisment-add-and-modify");
        return  modelAndView;
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public ModelAndView createDoPost(ModelAndView modelAndView, HttpServletRequest pRequest, @RequestParam RecommendedProduct recommendedProduct){
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.addObject("category", new Category());
        recommendedProductService.createRecommendedProductService(recommendedProduct);
        buildB2cList(modelAndView);
        return  modelAndView;
    }
}
