package com.pgt.mobile.product.controller;

import com.pgt.configuration.Configuration;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mProduct")
public class ProductMobileController extends BaseMobileController{

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductMobileController.class);

	@Autowired
	private ProductService productService;

	@Autowired
	private ESSearchService esSearchService;

    @Autowired
	private Configuration configuration;


	@RequestMapping(value = "/query", method = RequestMethod.POST)
	public @ResponseBody  Map<String, Object> get(String productId) {

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
			LOGGER.warn("The pductid is empty.");
			return responseMobileFail(responseMap, "ProductId.empty");
		}
		// 从索引库中取出商品
		ESTerm productEsterm = new ESTerm();
		productEsterm.setPropertyName("productId");
		productEsterm.setTermValue(productId);
		SearchResponse searchProduct = esSearchService.findProducts(productEsterm, null, null, null, null, null, null);
		SearchHits productHits = searchProduct.getHits();
		SearchHit[] products = productHits.getHits();
		if(ArrayUtils.isEmpty(products)){
			return responseMobileFail(responseMap, "Product.empty");
		}
		Map product = products[0].getSource();
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


}
