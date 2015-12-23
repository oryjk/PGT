package com.pgt.product;

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
 * Created by carlwang on 12/22/15.
 */

@RestController
@RequestMapping("/product")
public class ProductController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;

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
        return modelAndView;
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
    public ModelAndView deleteProduct(@PathVariable("productId") String productId, ModelAndView modelAndView) {
        LOGGER.debug("Delete the product with product is is {}.", productId);
        if (StringUtils.isEmpty(productId)) {
            LOGGER.debug("The parameter product id is null.");
            return modelAndView;
        }

        productService.deleteProduct(productId);
        LOGGER.debug("The product has deleted with product is is {}.", productId);
        return modelAndView;
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
    public ModelAndView updateProduct(Product product, ModelAndView modelAndView) {
        if (product == null) {
            LOGGER.debug("The parameter product is null.");
            return modelAndView;
        }
        productService.updateProduct(product);
        LOGGER.debug("The product has updated with product is is {}.", product.getProductId());
        return modelAndView;
    }

}
