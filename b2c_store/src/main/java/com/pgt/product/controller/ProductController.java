package com.pgt.product.controller;

import com.pgt.cart.bean.Order;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.common.BreadBuilder;
import com.pgt.common.bean.BreadCrumb;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;
import com.pgt.communication.service.ConsultingService;
import com.pgt.communication.service.DiscussService;
import com.pgt.configuration.Configuration;
import com.pgt.constant.Constants;
import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by carlwang on 10/31/15.
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private ConsultingService consultingService;

    @Autowired
    private DiscussService discussService;

    @Autowired
    private Configuration configuration;

    @Autowired
    private ESSearchService esSearchService;

    @Autowired
    private BreadBuilder breadBuilder;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductHelper productHelper;

    /**
     * PDP page
     *
     * @param productId
     * @param modelAndView
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/{productId}", method = RequestMethod.GET)
    public ModelAndView get(@PathVariable("productId") String productId, ModelAndView modelAndView,
                            BindingResult bindingResult) {

        modelAndView.addObject("ES", configuration.getUseES());

        if (configuration.getUseES() == true) {
            return getES(productId, modelAndView, bindingResult);
        } else {
            return getDB(productId, modelAndView, bindingResult);
        }

    }

    public ModelAndView getES(String productId, ModelAndView modelAndView, BindingResult bindingResult) {

        if (StringUtils.isEmpty(productId)) {
            LOGGER.warn("The pduct id is empty.");
            return modelAndView;
        }

        // 从索引库中取出商品
        ESTerm productEsterm = new ESTerm();
        productEsterm.setPropertyName("productId");
        productEsterm.setTermValue(productId);
        SearchResponse searchProduct = esSearchService.findProducts(productEsterm, null, null, null, null, null, null);
        SearchHits productHits = searchProduct.getHits();
        SearchHit[] products = productHits.getHits();
        if (ObjectUtils.isEmpty(products)) {
            LOGGER.debug("Can not find the products.");
            return modelAndView;
        }
        Map product = products[0].getSource();

        LOGGER.debug("The product id is {product}", productId);

        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("the product is empty prductId:");
            return modelAndView;
        }

        // 设置类似商品分页大小
        PaginationBean similarPage = new PaginationBean();
        similarPage.setCapacity(20);
        similarPage.setCurrentIndex(0);

        // 封装咨询的信息
        ConsultingCustom consultingCustom = new ConsultingCustom();
        int total = consultingService.queryAllConsultingByProductCount(Integer.parseInt(productId), consultingCustom);
        CommPaginationBean conPaginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(), 0,
                total);
        consultingCustom.setPaginationBean(conPaginationBean);
        List<Consulting> consultings = consultingService.queryAllConsultingByProduct(Integer.valueOf(productId),
                consultingCustom);

        // 封装讨论的信息
        DiscussCustom discussCustom = new DiscussCustom();
        int discussToatl = discussService.queryProductAllDiscussCount(Integer.parseInt(productId));
        CommPaginationBean disPaginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(), 0,
                discussToatl);
        discussCustom.setPaginationBean(disPaginationBean);
        List<Discuss> discuss = discussService.queryProductAllDiscuss(Integer.valueOf(productId), discussCustom);

        String parentCategoryId = null;
        for (SearchHit hits : products) {
            Integer productSearchId = (Integer) hits.getSource().get("productId");
            if (Integer.parseInt(productId) == productSearchId) {
                parentCategoryId = (String) hits.getSource().get("parentCategoryId");
            }
        }
        // 根据拿到的分类id查询该分类下面的所有商品
        if (!StringUtils.isEmpty(parentCategoryId)) {
            ESTerm esterm = new ESTerm();
            esterm.setPropertyName("parentCategoryId");
            esterm.setTermValue(parentCategoryId);
            SearchResponse searchResponse = esSearchService.findProducts(esterm, null, null, null, null, null, null);
            // 建立新的结果集，除去当前商品
            SearchHits similarHits = searchResponse.getHits();
            SearchHit[] oldSimilarProducts = similarHits.getHits();
            if (oldSimilarProducts.length == 0) {
                LOGGER.debug("the similarProducts length is 0 prductId:");
                return modelAndView;
            }
            LOGGER.debug("the similarHits length", oldSimilarProducts.length);

            List<SearchHit> similarProducts = Arrays.asList(oldSimilarProducts);
            // filter the same product id.
            SearchHit[] newSimilarProducts = similarProducts.stream()
                    .filter(searchHitFields -> !searchHitFields.getSource().get("productId").equals(productId))
                    .toArray(SearchHit[]::new);
            modelAndView.addObject("similarProducts", newSimilarProducts);
        }

        List<BreadCrumb> breadCrumb = breadBuilder.buildPdpBreadCrumb(productId);
        modelAndView.addObject("product", product);
        modelAndView.addObject("consultings", consultings);
        modelAndView.addObject("conPaginationBean", conPaginationBean);
        modelAndView.addObject("discussList", discuss);
        modelAndView.addObject("disPaginationBean", disPaginationBean);
        modelAndView.addObject("breadCrumb", breadCrumb);
        modelAndView.setViewName(Constants.PDP_PAGE);

        return modelAndView;

    }

    public ModelAndView getDB(String productId, ModelAndView modelAndView, BindingResult bindingResult) {

        if (StringUtils.isEmpty(productId)) {
            LOGGER.warn("The pduct id is empty.");
            return modelAndView;
        }
        LOGGER.debug("The product id is {product}", productId);
        Product product = productService.queryProduct(Integer.valueOf(productId));

        if (ObjectUtils.isEmpty(product)) {
            LOGGER.debug("Can not find the product with id is {productId}", productId);
            return modelAndView;
        }

        Category category = categoryService.queryParentCategoryByProductId(product.getProductId());
        List<Product> similarProducts = productHelper.findProductsByCategoryId(category.getId().toString());
        // 移除当前商品
        similarProducts.remove(product);

        modelAndView.addObject("similarProducts", similarProducts);

        // 封装咨询的信息
        ConsultingCustom consultingCustom = new ConsultingCustom();
        int total = consultingService.queryAllConsultingByProductCount(Integer.parseInt(productId), consultingCustom);
        CommPaginationBean conPaginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(), 0,
                total);
        consultingCustom.setPaginationBean(conPaginationBean);
        List<Consulting> consultings = consultingService.queryAllConsultingByProduct(Integer.valueOf(productId),
                consultingCustom);

        // 封装讨论的信息
        DiscussCustom discussCustom = new DiscussCustom();
        int discussToatl = discussService.queryProductAllDiscussCount(Integer.parseInt(productId));
        CommPaginationBean disPaginationBean = new CommPaginationBean(configuration.getCommunicationCapacity(), 0,
                discussToatl);
        discussCustom.setPaginationBean(disPaginationBean);
        List<Discuss> discuss = discussService.queryProductAllDiscuss(Integer.valueOf(productId), discussCustom);

        List<BreadCrumb> breadCrumb = breadBuilder.buildPdpBreadCrumb(productId);
        modelAndView.addObject("product", product);
        modelAndView.addObject("consultings", consultings);
        modelAndView.addObject("conPaginationBean", conPaginationBean);
        modelAndView.addObject("discussList", discuss);
        modelAndView.addObject("disPaginationBean", disPaginationBean);
        modelAndView.addObject("breadCrumb", breadCrumb);
        modelAndView.setViewName(Constants.PDP_PAGE);

        return modelAndView;

    }

    @RequestMapping(value = "/buy",method = RequestMethod.GET)
    public ModelAndView buy(ModelAndView modelAndView){
        LOGGER.debug("The method site-bar access buy");
        modelAndView.setViewName("/site-bar/buy");
        return modelAndView;
   }

    @RequestMapping(value = "/collection",method = RequestMethod.GET)
    public ModelAndView collection(ModelAndView modelAndView){
        LOGGER.debug("The method site-bar access collection");
        modelAndView.setViewName("/site-bar/collection");
        return modelAndView;
    }


    @RequestMapping(value = "/history",method = RequestMethod.GET)
    public ModelAndView history(ModelAndView modelAndView){
        LOGGER.debug("The method site-bar access history");
        modelAndView.setViewName("/site-bar/history");
        return modelAndView;
    }



    public BreadBuilder getBreadBuilder() {
        return breadBuilder;
    }

    public void setBreadBuilder(BreadBuilder breadBuilder) {
        this.breadBuilder = breadBuilder;
    }
}
