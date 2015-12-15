package com.pgt.product.controller;

import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by cwang7 on 10/29/15.
 */

@RestController
@RequestMapping("/admin/product")
public class ProductAdminController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAdminController.class);
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ResponseEntity findProduct(@PathVariable("productId") String productId) {
        LOGGER.debug("search the product with id {productId}.", productId);
        Product product = productService.queryProduct(Integer.valueOf(productId));
        ResponseEntity responseEntity = null;
        if (product == null) {
            LOGGER.debug("Can not find the product with id is {productId}", productId);
            responseEntity = new ResponseEntity(HttpStatus.OK);
            return responseEntity;
        }
        LOGGER.debug("Have one record with product id is {productId}", productId);
        responseEntity = new ResponseEntity(product, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createProduct(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createProduct(Product product) {
        LOGGER.debug("Create a product.");
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        if (product == null) {
            LOGGER.debug("Can not create a product, because the product is null");
            return responseEntity;
        }

        Integer productId = productService.createProduct(product);
        LOGGER.debug("Product has created, the product is is {productId}.", productId);
        return responseEntity;
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.POST)
    public ResponseEntity deleteProduct(@PathVariable("productId") String productId) {
        LOGGER.debug("Delete the product with product is is {productId}.", productId);
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        if (StringUtils.isEmpty(productId)) {
            LOGGER.debug("The parameter product id is null.");
            return responseEntity;
        }

        productService.deleteProduct(productId);
        LOGGER.debug("The product has deleted with product is is {productId}.", productId);
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

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public ModelAndView updateProduct(ModelAndView modelAndView) {
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity updateProduct(Product product) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        if (product == null) {
            LOGGER.debug("The parameter product is null.");
            return responseEntity;
        }
        productService.updateProduct(product);
        LOGGER.debug("The product has updated with product is is {productId}.", product.getProductId());
        return responseEntity;
    }

}
