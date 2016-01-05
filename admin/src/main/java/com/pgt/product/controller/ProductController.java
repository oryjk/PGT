package com.pgt.product.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.media.MediaService;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 12/22/15.
 */

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ModelAndView findProduct(@PathVariable("productId") String productId, ModelAndView modelAndView) {
        LOGGER.debug("search the product with id {productId}.", productId);
        Product product = productService.queryProduct(Integer.valueOf(productId));
        if (product == null) {
            LOGGER.debug("Can not find the product with id is {}", productId);
            return modelAndView;
        }
        LOGGER.debug("Have one record with product id is {}", productId);
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createProduct(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        List<Category> categories = categoryService.queryAllParentCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("/product/productBaseModify");
        return modelAndView;
    }


    @RequestMapping(value = "/create/stepBase", method = RequestMethod.POST)
    public ModelAndView createStepBase(Product product, ModelAndView modelAndView) {
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty.");
            return modelAndView;
        }
        productService.createProduct(product);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/product/productImageModify");
        return modelAndView;
    }

    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createProductMedias(ProductMedia productMedia) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
        Integer mediaId = mediaService.create(productMedia);
        responseEntity.getBody().put("success", true);
        responseEntity.getBody().put("mediaId", mediaId);
        return responseEntity;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createProduct(Product product, ModelAndView modelAndView) {
        LOGGER.debug("Create a product.");
        if (product == null) {
            LOGGER.debug("Can not create a product, because the product is null");
            return modelAndView;
        }

        productService.createProduct(Integer.valueOf(product.getRelatedCategoryId()), product);
        LOGGER.debug("Product has created, the product is is {}.", product.getProductId());
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteProduct(@PathVariable("productId") String productId, ModelAndView modelAndView) {
        LOGGER.debug("Delete the product with product is is {}.", productId);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
        Map<String, Object> response = responseEntity.getBody();
        if (StringUtils.isEmpty(productId)) {
            LOGGER.debug("The parameter product id is null.");
            response.put("success", false);
            response.put("message", "The product id is empty.");
            return responseEntity;
        }

        productService.deleteProduct(productId);
        response.put("success", true);
        LOGGER.debug("The product has deleted with product id is {}.", productId);
        return responseEntity;
    }

    @RequestMapping(value = "/delete/list", method = RequestMethod.POST)
    public ResponseEntity deleteProducts(List<String> products) {

        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        if (products == null || products.isEmpty()) {
            LOGGER.debug("The products is null.");
            return responseEntity;
        }
        productService.deleteProducts(products);
        LOGGER.debug("The products has deleted.");
        return responseEntity;
    }

    @RequestMapping(value = "/update/{productId}", method = RequestMethod.GET)
    public ModelAndView updateProduct(@PathVariable("productId") Integer productId, ModelAndView modelAndView) {
        Product product = productService.queryProduct(productId);
        modelAndView.setViewName("/product/addAndModifyProduct");
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty with id {}.", productId);
            return modelAndView;
        }
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateProduct(Product product, ModelAndView modelAndView) {
        if (product == null) {
            LOGGER.debug("The parameter product is null.");
            return modelAndView;
        }
        productService.updateProduct(product);
        LOGGER.debug("The product has updated with product is is {}.", product.getProductId());
        return modelAndView;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView searchProducts(@RequestParam(required = false, value = "term") String term,
                                       @RequestParam(required = false, value = "currentIndex") Integer currentIndex, ModelAndView modelAndView) {
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCurrentIndex(currentIndex);
        searchPaginationBean.setTerm(term);
        List<Product> products = searchService.queryProduct(searchPaginationBean);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

}
