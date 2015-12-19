package com.pgt.utils;


import com.alibaba.fastjson.JSONObject;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.data.ImportDataService;
import org.flywaydb.core.internal.util.Location;
import org.flywaydb.core.internal.util.scanner.Resource;
import org.flywaydb.core.internal.util.scanner.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class JsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
	@Autowired
	private ImportDataService importDataService;
	@Autowired
	private CategoryService categoryService;


	public String fileToJson(String path) throws Exception{
		Scanner scanner = new Scanner(this.getClass().getClassLoader());
		Location location =new Location(path);
		LOGGER.debug("The file path is {}.",location.getPath());
		Resource[] resources = scanner.scanForResources(location, "",".json");
		if(!ObjectUtils.isEmpty(resources)){
			return resources[0].loadAsString("UTF-8");
		}
		return null;
	}

	public void categoriesCreate(String path){
		try{
			LOGGER.debug("The initial data path is {},",path);
			LOGGER.debug(fileToJson(path));
			List<Category> categorys=JSONObject.parseArray(fileToJson(path), Category.class);
			for(int i = 0 ; i < categorys.size(); i++){
				importDataService.createCatalogData(categorys.get(i));
			}

			LOGGER.debug("End initial data.");
		}catch (Exception e) {
			LOGGER.error("Some error occured when initial data,the error message is {}.",e.getMessage());
		}
	}

}
	
 	
