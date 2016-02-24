package com.pgt.product.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.Configuration;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.bean.ProductType;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.service.ESSearchService;
import com.pgt.search.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 12/22/15.
 */

@RestController
@RequestMapping("/product")
public class ProductController extends InternalTransactionBaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private Configuration configuration;

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
    public ModelAndView createProduct(HttpServletRequest pRequest, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        modelAndView.addObject("product", new Product());
        List<Category> categories = categoryService.queryAllParentCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("action", "/product/create/stepBase");
        modelAndView.setViewName("/product/productBaseModify");
        return modelAndView;
    }


    @RequestMapping(value = "/create/stepBase", method = RequestMethod.POST)
    public ModelAndView createStepBase(HttpServletRequest pRequest, Product product, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        TransactionStatus status = ensureTransaction();
        try {
            // main logic
            if (ObjectUtils.isEmpty(product)) {
                LOGGER.debug("The product is empty.");
                return modelAndView;
            }
            productService.createProduct(product);
            if (product.getStatus() == 1) {
                esSearchService.productIndex(product);
            }
            modelAndView.addObject("product", product);
            modelAndView.addObject("staticServer", configuration.getStaticServer());
            modelAndView.setViewName("redirect:/product/update/productImageModify/" + product.getProductId());
            return modelAndView;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createProductMedias(ProductMedia productMedia) {
        TransactionStatus status = ensureTransaction();
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        try {
            removeOldProductMediaRef(productMedia);
            Integer mediaId = mediaService.create(productMedia);
            Product product = productService.queryProduct(productMedia.getReferenceId());
            if (ObjectUtils.isEmpty(product)) {
                LOGGER.debug("The product is empty with id is {}.", productMedia.getReferenceId());
                responseEntity.getBody().put("success", false);
                responseEntity.getBody().put("message", "Can not update product index.");
                return responseEntity;
            }

            if(product.getType()!=null&&product.getType().endsWith(ProductType.LIVE_PAWNAGE.toString())){
                LOGGER.debug("The product type is {}.",ProductType.LIVE_PAWNAGE);
            }else{
                esSearchService.updateProductIndex(product);
                LOGGER.debug("The product type is {}.",ProductType.DEAD_PAWNAGE);
            }
            responseEntity.getBody().put("success", true);
            responseEntity.getBody().put("mediaId", mediaId);
            return responseEntity;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return responseEntity;

    }

    private void removeOldProductMediaRef(ProductMedia productMedia) {
        if (productMedia.getType().equals(MediaType.front)) {
            ProductMedia oldProductMedia = mediaService.findFrontByProductId(String.valueOf(productMedia.getReferenceId()));
            if (!ObjectUtils.isEmpty(oldProductMedia)) {
                mediaService.deleteMedia(oldProductMedia.getId());
            }
        }
        if (productMedia.getType().equals(MediaType.advertisement)) {
            ProductMedia oldProductMedia = mediaService.findAdByProductId(String.valueOf(productMedia.getReferenceId()));
            if (!ObjectUtils.isEmpty(oldProductMedia)) {
                mediaService.deleteMedia(oldProductMedia.getId());
            }
        }
        if (productMedia.getType().equals(MediaType.thumbnail)) {
            ProductMedia oldProductMedia = mediaService.findThumbnailMediasByProductId(String.valueOf(productMedia.getReferenceId()));
            if (!ObjectUtils.isEmpty(oldProductMedia)) {
                mediaService.deleteMedia(oldProductMedia.getId());
            }
        }
        if (productMedia.getType().equals(MediaType.mobileDetail)) {
            ProductMedia oldProductMedia = mediaService.findThumbnailMediasByProductId(String.valueOf(productMedia.getReferenceId()));
            if (!ObjectUtils.isEmpty(oldProductMedia)) {
                mediaService.deleteMedia(oldProductMedia.getId());
            }
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createProduct(HttpServletRequest pRequest, Product product, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        TransactionStatus status = ensureTransaction();
        try {
            // main logic
            LOGGER.debug("Create a product.");
            if (product == null) {
                LOGGER.debug("Can not create a product, because the product is null");
                return modelAndView;
            }
            product.setCreationDate(new Date());
            product.setUpdateDate(new Date());
            productService.createProduct(Integer.valueOf(product.getRelatedCategoryId()), product);
            LOGGER.debug("Product has created, the product is is {}.", product.getProductId());
            return modelAndView;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        esSearchService.productIndex(product);
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteProduct(HttpServletRequest pRequest, @PathVariable("productId") String productId, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ResponseEntity<>(new HashMap(), HttpStatus.FORBIDDEN);
        }
        TransactionStatus status = ensureTransaction();
        try {// main logic
            LOGGER.debug("Delete the product with product is is {}.", productId);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
            Map<String, Object> response = responseEntity.getBody();
            if (StringUtils.isEmpty(productId)) {
                LOGGER.debug("The parameter product id is null.");
                response.put("success", false);
                response.put("message", "The product id is empty.");
                return responseEntity;
            }
            Category rootCategory = categoryService.queryRootCategoryByProductId(Integer.valueOf(productId));
            productService.deleteProduct(productId);
            esSearchService.deleteProductIndex(productId);
            if (!ObjectUtils.isEmpty(rootCategory)) {
//                esSearchService.createHotSaleIndex(rootCategory.getId());
            }
            response.put("success", true);
            LOGGER.debug("The product has deleted with product id is {}.", productId);
            return responseEntity;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return null;
    }

    @RequestMapping(value = "/delete/list", method = RequestMethod.POST)
    public ResponseEntity deleteProducts(HttpServletRequest pRequest, List<String> products) {
        ResponseEntity responseEntity = new ResponseEntity(HttpStatus.OK);
        TransactionStatus status = ensureTransaction();
        try {// verify permission
            if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
                return new ResponseEntity<>(new HashMap(), HttpStatus.FORBIDDEN);
            }
            // main logic
            if (products == null || products.isEmpty()) {
                LOGGER.debug("The products is null.");
                return responseEntity;
            }
            productService.deleteProducts(products);
            LOGGER.debug("The products has deleted.");
            return responseEntity;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/update/{productId}", method = RequestMethod.GET)
    public ModelAndView updateProduct(HttpServletRequest pRequest, @PathVariable("productId") Integer productId, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        Product product = productService.queryProduct(productId);
        List<Category> categories = categoryService.queryAllParentCategories();
        modelAndView.setViewName("/product/productBaseModify");
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty with id {}.", productId);
            return modelAndView;
        }
        modelAndView.addObject("product", product);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("action", "/product/update/stepBase");
        return modelAndView;
    }


    @RequestMapping(value = "/update/stepBase", method = RequestMethod.POST)
    public ModelAndView updateStepBase(HttpServletRequest pRequest, Product product, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty.");
            return modelAndView;
        }
        TransactionStatus status = ensureTransaction();
        try {
            product.setUpdateDate(new Date());
            productService.updateProductBase(product);

        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        modelAndView.setViewName("redirect:/product/update/productImageModify/" + product.getProductId());
        if (product.getStatus() == 0) {
            esSearchService.deleteProductIndex(String.valueOf(product.getProductId()));
            return modelAndView;
        }
        esSearchService.updateProductIndex(product);
        return modelAndView;

    }

    @RequestMapping(value = "/update/productImageModify/{productId}", method = RequestMethod.GET)
    public ModelAndView productImageModify(@PathVariable(value = "productId") Integer productId, ModelAndView modelAndView) {
        Product product = productService.queryProduct(productId);
        modelAndView.addObject("product", product);
        modelAndView.addObject("staticServer", configuration.getStaticServer());
        modelAndView.setViewName("/product/productImageModify");
        return modelAndView;
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateProduct(HttpServletRequest pRequest, Product product, ModelAndView modelAndView) {
        TransactionStatus status = ensureTransaction();
        try {
            // verify permission
            if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
                return new ModelAndView(PERMISSION_DENIED);
            }
            // main logic
            if (product == null) {
                LOGGER.debug("The parameter product is null.");
                return modelAndView;
            }
            product.setUpdateDate(new Date());
            productService.updateProduct(product);
            LOGGER.debug("The product has updated with product is is {}.", product.getProductId());
            return modelAndView;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
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
