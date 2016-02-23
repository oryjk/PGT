package com.pgt.category;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryService;
import com.pgt.common.bean.Media;
import com.pgt.configuration.Configuration;
import com.pgt.internal.bean.Role;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.product.service.ProductService;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/tenderCategory")
public class TenderCategoryController extends InternalTransactionBaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TenderCategoryController.class);

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ProductService  productService;
	@Autowired
	private Configuration   configuration;

	@Autowired
	private ESSearchService esSearchService;

	@Autowired
	private MediaService mediaService;

	@ModelAttribute
	public void modelAttribute (ModelAndView modelAndView) {
		modelAndView.addObject("staticServer", configuration.getStaticServer());
		modelAndView.addObject("categories", categoryService.queryTenderRootCategories());
	}


	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	public ModelAndView get (HttpServletRequest pRequest, ModelAndView modelAndView, @RequestParam(value = "type", required = false) CategoryType categoryType,
	                         @RequestParam(value = "currentIndex", required = false) Integer currentIndex) {
		/*
		if (!verifyPermission(pRequest)) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		*/
		LOGGER.debug("Get all {} categories", categoryType);
		if (ObjectUtils.isEmpty(categoryType)) {
			LOGGER.debug("The category type is empty,use default category type tender_Root.");
			categoryType = CategoryType.TENDER_ROOT;
		}
		Category categoryRequest = new Category();
		categoryRequest.setCode(null);
		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 0;
		}
		paginationBean.setCurrentIndex(currentIndex);
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		categoryRequest.setType(categoryType);
		Integer total = categoryService.queryCategoryTotal(categoryRequest);

		if(total>0) {
			paginationBean.setTotalAmount(total);
			List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
			modelAndView.addObject("categories", categories);
		}
		modelAndView.addObject("categoryType", categoryType.toString());
		modelAndView.addObject("staticServer", configuration.getStaticServer());
		modelAndView.setViewName("/tender-category/categoryList");
		return modelAndView;
	}


	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create (HttpServletRequest pRequest, ModelAndView modelAndView) {

		/*
		// verify permission
		if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		*/
		// main logic
		LOGGER.debug("create GET.");
		modelAndView.addObject("category", new Category());
		List<Category> categories = categoryService.queryTenderRootCategories();
		modelAndView.setViewName("/tender-category/addCategory");
		modelAndView.addObject("categories", categories);
		return modelAndView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView create (HttpServletRequest pRequest, Category category, ModelAndView modelAndView, BindingResult bindingResult) {
		/*
		// verify permission
		if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
			return new ModelAndView(PERMISSION_DENIED);
		}*/
		// main logic
		LOGGER.debug("Begin create category.");
		if (bindingResult.hasErrors()) {
			LOGGER.debug("Some data value mission.");
			return modelAndView;
		}
		if (category.getType().equals(CategoryType.TENDER_ROOT)) {
			category.setParent(null);
		}
		String categoryId = categoryService.createCategory(category, category.getFrontMedia().getId());
		category = categoryService.queryCategory(category.getId());
		esSearchService.createCategoryIndex(category);
		if (category.getType().equals(CategoryType.TENDER_ROOT)) {
			esSearchService.createHotSaleIndex(category.getId());
		}
		LOGGER.debug("The category is is {}.", categoryId);
		LOGGER.debug("end create category.");
		modelAndView.setViewName("/tender-category/addAndModifyCategorySuccess");
		return modelAndView;
	}



	@RequestMapping(value = "/update/{categoryId}", method = RequestMethod.GET)
	public ModelAndView update (HttpServletRequest pRequest, @PathVariable("categoryId") Integer categoryId, ModelAndView modelAndView) {

		/*
		// verify permission
		if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		*/
		// main logic
		LOGGER.debug("Update category id is {}.", categoryId);
		Category category = categoryService.queryCategory(categoryId);
		if (ObjectUtils.isEmpty(category)) {
			LOGGER.debug("Can find category with id is {}.", categoryId);
			return modelAndView;
		}
		modelAndView.addObject("category", category);
		modelAndView.setViewName("/tender-category/modifyCategory");
		return modelAndView;
	}

	@RequestMapping(value = "/update/", method = RequestMethod.POST)
	public ModelAndView update (HttpServletRequest pRequest, Category category, ModelAndView modelAndView, BindingResult bindingResult) {
		/*
		// verify permission
		if (!verifyPermission(pRequest, Role.MERCHANDISER, Role.PROD_ORDER_MANAGER, Role.ADMINISTRATOR)) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		*/
		// main logic
		if (bindingResult.hasErrors()) {
			LOGGER.debug("Some property not valid.");
			return modelAndView;
		}
		Integer count = categoryService.updateCategory(category);
		if (count != 1) {
			LOGGER.debug("Not success update the category.");
			return modelAndView;
		}
		if (category.getType().equals(CategoryType.TENDER_ROOT)) {
			List<Media> medias = mediaService.findMediaByRefId(category.getId(), MediaType.category);
			if (!CollectionUtils.isEmpty(medias)) {
				mediaService.deleteMedia(medias.get(0).getId());
			}
			Media media = mediaService.findMedia(category.getFrontMedia().getId(), MediaType.category);
			LOGGER.debug("Can not find the media id with {}. ", category.getFrontMedia().getId());
			if (!ObjectUtils.isEmpty(media)) {
				media.setReferenceId(category.getId());
				mediaService.updateMedia(media);
			}

		}
		category = categoryService.queryCategory(category.getId());
		esSearchService.updateCategoryIndex(category);
		if (category.getType().equals(CategoryType.TENDER_ROOT)) {
			esSearchService.createHotSaleIndex(category.getId());
		}
		modelAndView.setViewName("/tender-category/addAndModifyCategorySuccess");
		return modelAndView;
	}

	@RequestMapping(value = "/getSubCategories/{rootCategoryId}", method = RequestMethod.GET)
	public ModelAndView getSubCategories (@PathVariable("rootCategoryId") Integer rootCategoryId, ModelAndView modelAndView) {
		LOGGER.debug("The root category id is {}. ", rootCategoryId);
		List<Category> subCategories = categoryService.querySubCategories(rootCategoryId);
		modelAndView.setViewName("/tender-category/categoryList");
		modelAndView.addObject("categoryType", CategoryType.TENDER_HIERARCHY);
		modelAndView.addObject("subCategories", subCategories);
		return modelAndView;
	}


	@RequestMapping(value = "/getSubCategoriesAjax/{rootCategoryId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity getSubCategories (@PathVariable("rootCategoryId") Integer rootCategoryId) {
		LOGGER.debug("The root category id is {}. ", rootCategoryId);
		ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
		List<Category> categories = categoryService.querySubCategories(rootCategoryId);
		LOGGER.debug("The sub category size is {}.", categories.size());
		Map<String, Object> body = responseEntity.getBody();
		body.put("categories", categories);
		return responseEntity;
	}

}