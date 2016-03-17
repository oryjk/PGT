package com.pgt.tender.controller;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.Media;
import com.pgt.common.bean.ViewMapperConfiguration;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.media.helper.MediaHelper;
import com.pgt.product.bean.P2PProduct;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.product.service.TenderProductService;
import com.pgt.search.service.ESSearchService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.tender.bean.CreateTender;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.bean.TenderQuery;
import com.pgt.tender.service.TenderCategoryService;
import com.pgt.tender.service.TenderService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 16-1-27.
 */
@RestController
@RequestMapping("/tender")
public class TenderController extends InternalTransactionBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

    @Autowired
    private TenderService tenderService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TenderProductService tenderProductService;

    @Autowired
    private TenderCategoryService tenderCategoryService;

    @Autowired
    private ViewMapperConfiguration viewMapperConfiguration;


    @Autowired
    private URLConfiguration urlConfiguration;

    @Autowired
    private MediaHelper mediaHelper;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;


    @RequestMapping(value = "/tenderList", method = RequestMethod.GET)
    public ModelAndView get(@RequestParam(value = "term", required = false) String term,
                            @RequestParam(value = "sortProperty", required = false) String sortProperty,
                            @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                            @RequestParam(value = "currentIndex", required = false) Long currentIndex, ModelAndView modelAndView,
                            HttpServletRequest pRequest, TenderQuery tenderQuery) {

        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.setViewName(viewMapperConfiguration.getTenderListPage());
        LOGGER.debug("The method query tenderList");
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0L;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());

        if (!StringUtils.isEmpty(term)) {
            LOGGER.debug("The query term is {}", term);
            tenderQuery.setNameLike(true);
            tenderQuery.setName(term);
        }

        if (!StringUtils.isEmpty(sortProperty)) {
            LOGGER.debug("The sortProerty is {} and sortValve is {}", sortProperty, sortValue);
            tenderQuery.orderbyCondition(sortValue.endsWith("ASC") ? true : false, sortProperty);
        }

        List<Tender> tenderAll = tenderService.queryTenderByQuery(tenderQuery);
        if (CollectionUtils.isEmpty(tenderAll)) {
            LOGGER.debug("The query tender is empty");
            return modelAndView;
        }
        paginationBean.setTotalAmount(tenderAll.size());
        tenderQuery.setPaginationBean(paginationBean);
        List<Tender> tenderList = tenderService.queryTenderByQuery(tenderQuery);
        if (!CollectionUtils.isEmpty(tenderList)) {
            modelAndView.addObject("tenderList", tenderList);
            modelAndView.addObject("paginationBean", paginationBean);
        }
        modelAndView.addObject(Constants.ROOT_CATEGORY_ID, tenderQuery.getRootCategoryId());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createTenderUI(ModelAndView modelAndView, HttpServletRequest pRequest) {

        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }

        LOGGER.debug("The method create tender UI");
        List<Category> categories = categoryService.queryAllTenderParentCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.setViewName("/p2p-tender/tender-add-and-modify");
        return modelAndView;
    }


    @RequestMapping(value = "/tenderIndex/{tenderId}", method = RequestMethod.GET)
    public ResponseEntity tenderIndex(@PathVariable("tenderId") Integer tenderId) {

        LOGGER.debug("the tenderIndex is {}.", tenderId);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        Map<String, Object> response = responseEntity.getBody();
        if (tenderId == null) {
            LOGGER.debug("The tender id is null.");
            response.put("success", false);
            response.put("message", "The tender id is  empty.");
            return responseEntity;
        }
        Tender tender = tenderService.queryTenderById(tenderId, false);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender id is null.");
            response.put("success", false);
            response.put("message", "The tender id  is empty.");
            return responseEntity;
        }

        SearchResponse searchResponse = tenderSearchEngineService.findTenderById(tenderId);
        if (searchResponse.getHits().getTotalHits() < 1) {
            tenderSearchEngineService.createTenderIndex(tender);
        } else {
            tenderSearchEngineService.updateTender(tender);
        }
        response.put("success", true);
        LOGGER.debug("The  update with tenderIndex id is {}.", tenderId);
        return responseEntity;

    }


    @RequestMapping(value = "/updateUI/{tenderId}", method = RequestMethod.GET)
    public ModelAndView updateTenderUI(ModelAndView modelAndView, @PathVariable("tenderId") Integer tenderId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, null);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty for is {}", tenderId);
            return modelAndView;
        }
        LOGGER.debug("The method update tender UI");
        List<Category> categories = categoryService.queryAllTenderParentCategories();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("tender", tender);
        modelAndView.setViewName("/p2p-tender/tender-add-and-modify");
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createTenderStepBase(ModelAndView modelAndView, @Validated(value = CreateTender.class) Tender tender,
                                             BindingResult bindingResult, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        List<Category> categories = categoryService.queryAllTenderParentCategories();
        modelAndView.addObject("categories", categories);
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            Map<String, String> errors = new HashMap<>();
            for (FieldError err : fieldErrors) {
                errors.put(err.getField(), err.getDefaultMessage());
                LOGGER.debug("File name{} ,message{}", err.getField(), err.getDefaultMessage());
            }
            modelAndView.addObject("errors", errors);
            modelAndView.setViewName("/p2p-tender/tender-add-and-modify");
            LOGGER.debug("The tender input property not valid.");
            return modelAndView;
        }

        tenderService.createTender(tender);
        modelAndView.setViewName("redirect:/tender/addMedias?tenderId=" + tender.getTenderId());
        return modelAndView;
    }

    @RequestMapping(value = "/addMedias", method = RequestMethod.GET)
    public ModelAndView addMedias(ModelAndView modelAndView, @RequestParam("tenderId") Integer tenderId) {
        modelAndView.setViewName("/p2p-tender/tenderImageModify");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, false);
        if (!ObjectUtils.isEmpty(tender)) {
            modelAndView.addObject("tender", tender);
        }
        modelAndView.addObject("tenderId", tenderId);
        return modelAndView;
    }

    @RequestMapping(value = "/addMedias", method = RequestMethod.POST)
    public ResponseEntity addMedias(Media media) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        TransactionStatus status = ensureTransaction();
        try {
            int saveStatus = saveMedia(media);
            responseEntity.getBody().put("success", true);
            responseEntity.getBody().put("mediaId", saveStatus);
            return responseEntity;
        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return responseEntity;


    }


    private int saveMedia(Media media) {
        if (media.getType().equals(MediaType.tender_hero) || media.getType().equals(MediaType.tender_main)) {
            return mediaHelper.addMultiMedia(media, Tender.class);
        }
        return mediaHelper.addSingleMedia(media, Tender.class);
    }

    @RequestMapping(value = "/addProductStepBase", method = RequestMethod.GET)
    public ModelAndView addProducts(ModelAndView modelAndView, @RequestParam("tenderId") Integer tenderId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.setViewName("/p2p-tender/addProductStepBase");
        modelAndView.addObject("tenderId", tenderId);
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @RequestMapping(value = "/addProductStepBase", method = RequestMethod.POST)
    public ModelAndView addProducts(HttpServletRequest pRequest, P2PProduct product, ModelAndView modelAndView) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }

        // main logic
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty.");
            return modelAndView;
        }
        tenderProductService.createTenderProduct(product);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("redirect:/tender/addProductImageModify?productId=" + product.getProductId().toString());
        return modelAndView;
    }

    @RequestMapping(value = "/addProductImageModify", method = RequestMethod.GET)
    public ModelAndView addProductImageModify(ModelAndView modelAndView, @RequestParam("productId") Integer productId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        modelAndView.setViewName("/p2p-tender/productImageModify");
        if (ObjectUtils.isEmpty(productId)) {
            LOGGER.debug("The product id is empty");
            return modelAndView;
        }
        modelAndView.addObject("productId", productId);
        P2PProduct product = tenderProductService.queryTenderProduct(productId);
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty");
            return modelAndView;
        }
        modelAndView.addObject("product", product);
        return modelAndView;
    }


    @RequestMapping(value = "/addProductImageModify", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createTenderMedias(ProductMedia productMedia) {
        TransactionStatus status = ensureTransaction();
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        try {
            removeOldProductMediaRef(productMedia);
            Integer mediaId = mediaService.create(productMedia);
            Product product = tenderProductService.queryProduct(productMedia.getReferenceId());
            if (ObjectUtils.isEmpty(product)) {
                LOGGER.debug("The product is empty with id is {}.", productMedia.getReferenceId());
                responseEntity.getBody().put("success", false);
                responseEntity.getBody().put("message.", "Can not update product index.");
                return responseEntity;
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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateTender(ModelAndView modelAndView, Tender tender, HttpServletRequest pRequest) {
        modelAndView.setViewName("/tender/tenderAddAndModify");
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(tender.getName())) {
            LOGGER.debug("The name is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(tender.getDescription())) {
            LOGGER.debug("The tender description is empty");
            return modelAndView;
        }
        tenderService.updateTender(tender);
        modelAndView.setViewName("redirect:/tender/addMedias?tenderId=" + tender.getTenderId());
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{tenderId}", method = RequestMethod.GET)
    public ResponseEntity delete(@PathVariable("tenderId") Integer tenderId) {
        TransactionStatus status = ensureTransaction();
        try {
            LOGGER.debug("Delete the tenderId is {}.", tenderId);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
            Map<String, Object> response = responseEntity.getBody();
            if (tenderId == null) {
                LOGGER.debug("The tender id is null.");
                response.put("success", false);
                response.put("message", "The tender id is empty.");
                return responseEntity;
            }
            Tender tender = tenderService.queryTenderById(tenderId, false);
            if (ObjectUtils.isEmpty(tender)) {
                LOGGER.debug("The tender id is null.");
                response.put("success", false);
                response.put("message", "The tender id is empty.");
                return responseEntity;
            }

            //delete db
            List<P2PProduct> products = tender.getProducts();
            tenderService.deleteTender(tenderId);
            tenderCategoryService.deleteTenderCategoryByTenderId(tenderId);
            if (!CollectionUtils.isEmpty(products)) {
                for (Product product : products) {
                    tenderProductService.deleteProduct(product.getProductId().toString());
                }
            }
            //delete es
            esSearchService.deleteTender(tenderId.toString());
            response.put("success", true);
            LOGGER.debug("The  deleted with tender id is {}.", tenderId);
            return responseEntity;

        } catch (Exception e) {
            status.setRollbackOnly();
        } finally {
            getTransactionManager().commit(status);
        }
        return null;
    }


    @RequestMapping(value = "/queryTenderById/{tenderId}")
    public ModelAndView queryTenderById(@PathVariable("tenderId") Integer tenderId, ModelAndView modelAndView, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("delete the tenderId is {} ", tenderId);
        modelAndView.setViewName("/p2p-tender/tender-detail");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, null);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        LOGGER.debug("The query tender id is {}", tender.getTenderId());
        modelAndView.addObject("tender", tender);

        return modelAndView;
    }


    @RequestMapping(value = "/createTenderProduct/{tenderId}", method = RequestMethod.GET)
    public ModelAndView createTenderProduct(ModelAndView modelAndView, @PathVariable("tenderId") Integer tenderId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("The method createTenderProduct");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, false);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        modelAndView.addObject("tender", tender);
        modelAndView.setViewName("/p2p-tender/item-add-and-modify");
        return modelAndView;
    }

    @RequestMapping(value = "/createTenderProduct", method = RequestMethod.POST)
    public ModelAndView createTenderProduct(ModelAndView modelAndView, P2PProduct product, Integer tenderId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("The method crateTenderProduct");
        modelAndView.setViewName("/p2p-tender/item-add-and-modify");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty");
            return modelAndView;
        }
        if (StringUtils.isEmpty(product.getName())) {
            LOGGER.debug("The product is empty");
            return modelAndView;
        }

        if (ObjectUtils.isEmpty(product.getStock())) {
            LOGGER.debug("The stock is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(product.getSalePrice())) {
            LOGGER.debug("The salePrice is empty");
            return modelAndView;
        }
        product.setTenderId(tenderId);
        tenderProductService.createTenderProduct(product);
        LOGGER.debug("The crateTender product is successful");
        modelAndView.setViewName("redirect:/tender/queryTenderById/" + tenderId);
        return modelAndView;
    }


    @RequestMapping(value = "/updateTenderProduct/{tenderId}/{productId}", method = RequestMethod.GET)
    public ModelAndView updateTenderProduct(ModelAndView modelAndView, @PathVariable("tenderId") Integer tenderId,
                                            @PathVariable("productId") Integer productId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("The method updateTenderProduct");
        if (ObjectUtils.isEmpty(productId)) {
            LOGGER.debug("The product is empty");
            return modelAndView;
        }
        Product product = tenderProductService.queryProduct(productId);
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, false);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        modelAndView.addObject("tender", tender);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/p2p-tender/addProductStepBase");
        return modelAndView;
    }


    @RequestMapping(value = "/updateTenderProduct", method = RequestMethod.POST)
    public ModelAndView updateTenderProduct(ModelAndView modelAndView, P2PProduct product, Integer tenderId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("The method updateTenderProduct");

        product.setTenderId(tenderId);
        tenderProductService.updateTenderProduct(product);
        modelAndView.addObject("product", product);
        modelAndView.addObject("staticServer", configuration.getStaticServer());
        modelAndView.setViewName("redirect:/tender/addProductImageModify?productId=" + product.getProductId());
        return modelAndView;
    }


    @RequestMapping(value = "/queryTenderByProductId/{tenderId}/{productId}", method = RequestMethod.GET)
    public ModelAndView queryTenderByProductId(ModelAndView modelAndView, @PathVariable("tenderId") Integer tenderId,
                                               @PathVariable("productId") Integer productId, HttpServletRequest pRequest) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        LOGGER.debug("The method queryTenderByProductId");
        if (ObjectUtils.isEmpty(tenderId)) {
            LOGGER.debug("The tenderId is empty");
            return modelAndView;
        }
        Tender tender = tenderService.queryTenderById(tenderId, false);
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        if (ObjectUtils.isEmpty(productId)) {
            LOGGER.debug("The productId is empty");
            return modelAndView;
        }
        Product product = tenderProductService.queryProduct(productId);
        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("The product is empty and id is {}", productId);
        }
        modelAndView.addObject("tender", tender);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("/p2p-tender/item-detail");
        return modelAndView;
    }


    @RequestMapping(value = "/deleteTenderProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteProduct(HttpServletRequest pRequest, @PathVariable("productId") String productId) {
        // verify permission
        if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
            return new ResponseEntity<>(new HashMap(), HttpStatus.FORBIDDEN);
        }
        TransactionStatus status = ensureTransaction();
        try {// main logic
            LOGGER.debug("Delete the product with product is is {}.", productId);
            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
            Map<String, Object> response = responseEntity.getBody();
            if (StringUtils.isEmpty(productId)) {
                LOGGER.debug("The parameter product id is null .");
                response.put("success", false);
                response.put("message", "The product id is empty.");
                return responseEntity;
            }
            tenderProductService.deleteProduct(productId);
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

    /**
     * create tender.product's media
     *
     * @param productMedia
     * @return
     */
    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createProductMedias(ProductMedia productMedia) {
        TransactionStatus status = ensureTransaction();
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        try {
            removeOldProductMediaRef(productMedia);
            Integer mediaId = mediaService.create(productMedia);
            Tender tender = tenderService.queryTenderByProductId(productMedia.getReferenceId());
            if (ObjectUtils.isEmpty(tender)) {
                LOGGER.debug("The tender is empty with product id is {}.", productMedia.getReferenceId());
                responseEntity.getBody().put("success", false);
                responseEntity.getBody().put("message", "Can not update product index.");
                return responseEntity;
            }
            tenderSearchEngineService.updateTender(tender);
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


}
