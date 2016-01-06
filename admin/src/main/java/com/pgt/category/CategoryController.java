package com.pgt.category;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.Configuration;
import com.pgt.product.service.ProductService;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 12/17/15.
 */

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private Configuration configuration;

    @RequestMapping(value = "/categoryList", method = RequestMethod.GET)
    public ModelAndView get(ModelAndView modelAndView, @RequestParam(value = "type", required = false) CategoryType categoryType,
                            @RequestParam(value = "currentIndex", required = false) Integer currentIndex) {
        LOGGER.debug("Get all {} categories.", categoryType);
        if (ObjectUtils.isEmpty(categoryType)) {
            LOGGER.debug("The category type is empty,use default category type root.");
            categoryType = CategoryType.ROOT;
        }
        Category categoryRequest = new Category();
        categoryRequest.setCode(null);
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        categoryRequest.setType(categoryType);
        Integer total = categoryService.queryCategoryTotal(categoryRequest);
        paginationBean.setTotalAmount(total);
        List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("categoryType", categoryType.toString());
        modelAndView.addObject("staticServer", configuration.getStaticServer());
        modelAndView.setViewName("category/categoryList");
        return modelAndView;
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView) {
        LOGGER.debug("create GET.");
        modelAndView.addObject("category", new Category());
        List<Category> categories = categoryService.queryAllParentCategories();
        modelAndView.setViewName("/category/addCategory");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Category category, ModelAndView modelAndView, BindingResult bindingResult) {
        LOGGER.debug("Begin create category.");
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Some data value mission.");
            return modelAndView;
        }
        String categoryId = categoryService.createCategory(category, category.getFrontMedia().getId());
        LOGGER.debug("The category is is {}.", categoryId);
        LOGGER.debug("end create category.");
        modelAndView.setViewName("/category/addAndModifyCategorySuccess");
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{categoryId}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable("categoryId") Integer categoryId, ModelAndView modelAndView) {
        LOGGER.debug("Delete category id is {}.", categoryId);
        if (categoryId == null) {
            LOGGER.debug("The category id is empty,do nothing.");
            return modelAndView;
        }
        Integer count = categoryService.deleteCategory(categoryId);
        if (count != 1) {
            LOGGER.debug("Not success delete the category.");
            return modelAndView;
        }
        LOGGER.debug("Success delete the category with id {}.", categoryId);

        return modelAndView;

    }

    @RequestMapping(value = "/update/{categoryId}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("categoryId") Integer categoryId, ModelAndView modelAndView) {
        LOGGER.debug("Update category id is {}.", categoryId);
        Category category = categoryService.queryCategory(categoryId);
        if (ObjectUtils.isEmpty(category)) {
            LOGGER.debug("Can find category with id is {}.", categoryId);
            return modelAndView;
        }
        modelAndView.addObject("category", category);
        modelAndView.setViewName("/category/modifyCategory");
        return modelAndView;
    }

    @RequestMapping(value = "/update/", method = RequestMethod.POST)
    public ModelAndView update(Category category, ModelAndView modelAndView, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Some property not valid.");
            return modelAndView;
        }
        Integer count = categoryService.updateCategory(category);
        if (count != 1) {
            LOGGER.debug("Not success update the category.");
            return modelAndView;
        }
        modelAndView.setViewName("/category/addAndModifyCategorySuccess");
        return modelAndView;
    }

    @RequestMapping(value = "/getSubCategories/{rootCategoryId}", method = RequestMethod.GET)
    public ModelAndView getSubCategories(@PathVariable("rootCategoryId") Integer rootCategoryId, ModelAndView modelAndView) {
        LOGGER.debug("The root category id is {}.", rootCategoryId);
        List<Category> categories = categoryService.querySubCategories(rootCategoryId);
        LOGGER.debug("The sub category size is {}.", categories.size());
        modelAndView.setViewName("category/categoryList");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("categoryType", CategoryType.HIERARCHY);
        return modelAndView;
    }


    @RequestMapping(value = "/getSubCategoriesAjax/{rootCategoryId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity getSubCategories(@PathVariable("rootCategoryId") Integer rootCategoryId) {
        LOGGER.debug("The root category id is {}.", rootCategoryId);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        List<Category> categories = categoryService.querySubCategories(rootCategoryId);
        LOGGER.debug("The sub category size is {}.", categories.size());
        Map<String, Object> body = responseEntity.getBody();
        body.put("categories", categories);

        return responseEntity;
    }

}
