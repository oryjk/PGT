package com.pgt.product.controller;

import com.pgt.base.controller.BaseMobileController;
import com.pgt.configuration.Configuration;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/mProduct")
public class ProductMobileController extends BaseMobileController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMobileController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ESSearchService esSearchService;

    @Autowired
	private Configuration configuration;

	/**
	 * The method query Product
	 * @param productId
	 * @return
     */
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> get(String productId) {
		LOGGER.debug("The method query product");
		Map<String,Object> responseMap= new HashMap<String,Object>();
        responseMap.put("ES",configuration.getUseES());
		if (configuration.getUseES()==true) {
			return getES(productId, responseMap);
		} else {
			return getDB(productId, responseMap);
		}
	}

	public Map<String,Object> getES(String productId,Map<String,Object> responseMap) {

		if (StringUtils.isEmpty(productId)) {
			LOGGER.warn("The pductid is empty");
			return responseMobileFail(responseMap, "ProductId.empty");
		}
		// 从索引库中取出商品
		Map product=findProduct(responseMap,productId);
		LOGGER.debug("The product id is {}", productId);
		responseMap.put("product",product);   
		return responseMap;
	}

	public Map<String,Object> getDB(String productId, Map<String,Object> responseMap) {

		if (StringUtils.isEmpty(productId)) {
			LOGGER.warn("The pductid is empty");
			return responseMobileFail(responseMap, "ProductId.empty");
		}
		LOGGER.debug("The product id is {product}", productId);
		Product product = productService.queryProduct(Integer.valueOf(productId));

		if (ObjectUtils.isEmpty(product)) {
			LOGGER.debug("Can not find the product with id is {productId}", productId);
			return responseMobileFail(responseMap, "Product.empty");
		}
		return responseMap;
	}


    public Map findProduct(Map<String,Object> responseMap,String productId){
		ESTerm productEsterm = new ESTerm();
		productEsterm.setPropertyName("productId");
		productEsterm.setTermValue(productId);
		SearchResponse searchProduct = esSearchService.findProducts(productEsterm, null, null, null, null, null, null);
		SearchHits productHits = searchProduct.getHits();
		SearchHit[] products = productHits.getHits();
		if(ArrayUtils.isEmpty(products)){
			LOGGER.debug("The product is empty");
            return null;
		}
		Map product = products[0].getSource();
		return  product;
	}


	@RequestMapping(value = "/queryProduct/list", method = RequestMethod.POST)
	public Map<String, Object> queryProducts(List<String> productIds) {

		Map<String,Object> responseMap= new HashMap<>();
        LOGGER.debug("The method query Products");
        if(CollectionUtils.isEmpty(productIds)){
		    LOGGER.debug("The products id is empty");
			return responseMobileFail(responseMap, "ProductId.empty");
		}

		List<Map> products= new ArrayList<>();
		for (String productId:productIds) {
		    LOGGER.debug("The query product is {}",productId);
			Map map=findProduct(responseMap,productId);
			products.add(map);
		}

		LOGGER.debug("The method find product is success");
		responseMap.put("products",products);
        return responseMap;
	}


}
