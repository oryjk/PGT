package com.pgt.utils;


import java.io.*;

import java.util.List;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.data.ImportDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.product.service.ProductServiceImp;

@Component
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	@Autowired
	private ImportDataService importDataService;
	@Autowired
	private CategoryService categoryService;

	public String fileToJson(String path) throws Exception{
		FileReader fr = new FileReader(path);
		StringBuffer str = new StringBuffer();
		try {
			int i = 0;
			while ((i = fr.read()) != -1) {
				str.append((char) i);
			}
		} finally {
			fr.close();
		}
		return str.toString();
	}

	public void categoriesCreate(String path){
		try{
			LOGGER.debug(fileToJson(path));
			List<Category> categorys=JSONObject.parseArray(fileToJson(path), Category.class);
			for(int i = 0 ; i < categorys.size(); i++){
				importDataService.createCatalogData(categorys.get(i));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
	
 	
