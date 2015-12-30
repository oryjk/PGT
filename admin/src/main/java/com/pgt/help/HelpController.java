package com.pgt.help;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.Configuration;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.service.HelpCenterService;
import com.pgt.utils.PaginationBean;

@RestController
@RequestMapping("/help")
public class HelpController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelpController.class);

	@Autowired
	private HelpCenterService helpCenterService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryHelper categoryHelper;

	@Autowired
	private Configuration configuration;

	// 添加一个帮助信息
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addHelCenter(ModelAndView modelAndView, HelpCenter helpCenter) {
		modelAndView.setViewName("redirect:add");
		if (ObjectUtils.isEmpty(helpCenter.getContent())) {
			LOGGER.debug("helpcenter content is null");
			return modelAndView;
		}
		if (ObjectUtils.isEmpty(helpCenter.getTitle())) {
			LOGGER.debug("Title title is null");
			return modelAndView;
		}
		helpCenterService.createHelpCenter(helpCenter);
		modelAndView.setViewName("redirect:/help/listArticle");
		return modelAndView;
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public ModelAndView addHelpCenter(ModelAndView modelAndView) {
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.setViewName("/help/addOrModifyArticle");
		modelAndView.addObject("actionUrl", "add");
		return modelAndView;
	}

	@RequestMapping(value = "update/{helpCenterId}", method = RequestMethod.GET)
	public ModelAndView updateHelpCenter(ModelAndView modelAndView, @PathVariable("helpCenterId") String helpCenterId) {
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.setViewName("/help/addOrModifyArticle");
		HelpCenter helpCenter = helpCenterService.findHelpCenterById(Integer.parseInt(helpCenterId));
		modelAndView.addObject("helpCenter", helpCenter);
		modelAndView.addObject("actionUrl", "../update");
		return modelAndView;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateHlepCenter(HelpCenter helpCenter) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:update/" + helpCenter.getId());
		if (ObjectUtils.isEmpty(helpCenter.getContent())) {
			LOGGER.debug("helpcenter content is null");
			return mav;
		}
		if (ObjectUtils.isEmpty(helpCenter.getTitle())) {
			LOGGER.debug("Title title is null");
			return mav;
		}
		helpCenterService.updateHelpCenter(helpCenter);
		mav.setViewName("redirect:/help/listArticle");
		return mav;
	}

	@RequestMapping(value = "addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(ModelAndView modelAndView) {
		modelAndView.setViewName("/help/addOrModifyCategory");
		modelAndView.addObject("actionUrl", "addCategory");
		return modelAndView;
	}

	@RequestMapping(value = "addCategory", method = RequestMethod.POST)
	public ModelAndView addCategory(ModelAndView modelAndView, Category category) {
		modelAndView.setViewName("/help/addOrModifyCategory");
		modelAndView.addObject("actionUrl", "addCategory");
		if (StringUtils.isBlank(category.getName())) {
			modelAndView.addObject("error", "miss category msg");
			return modelAndView;
		}
		category.setType(CategoryType.HELP_ROOT);
		category.setCode("1");
		category.setColor("#006727");
		categoryService.createCategory(category);
		modelAndView.setViewName("redirect:/help/categoryList");
		return modelAndView;
	}

	@RequestMapping(value = "updateCategory/{categoryId}", method = RequestMethod.GET)
	public ModelAndView updateCategory(ModelAndView modelAndView, @PathVariable("categoryId") String categoryId) {
		Category category = categoryService.queryCategory(Integer.parseInt(categoryId));
		modelAndView.addObject("category", category);
		modelAndView.setViewName("/help/addOrModifyCategory");
		modelAndView.addObject("actionUrl", "../updateCategory");
		return modelAndView;
	}

	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public ModelAndView updateCategory(Category category) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:updateCategory/" + category.getId());
		mav.addObject("actionUrl", "../updateCategory");
		if (StringUtils.isBlank(category.getName())) {
			mav.addObject("error", "miss category name when update");
			return mav;
		}
		category.setType(CategoryType.HELP_ROOT);
		category.setCode("1");
		category.setColor("#006727");
		categoryService.updateCategory(category);
		mav.setViewName("redirect:/help/categoryList");
		return mav;
	}

	@RequestMapping(value = "/delete/{helpCenterId}", method = RequestMethod.GET)
	public ModelAndView deleteHelpCenterById(@PathVariable("helpCenterId") String helpCenterId,
			ModelAndView modelAndView) {
		helpCenterService.deleteHelpCenterById(Integer.parseInt(helpCenterId));
		modelAndView.setViewName("redirect:/help/articleList");
		return modelAndView;
	}

	@RequestMapping(value = "/deleteCategory/{categoryId}", method = RequestMethod.GET)
	public ModelAndView deleteCategoryById(@PathVariable("categoryId") String categoryId, ModelAndView modelAndView) {
		categoryService.deleteCategory(Integer.parseInt(categoryId));
		modelAndView.setViewName("redirect:/help/categoryList");
		return modelAndView;
	}

	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	public ModelAndView getCategory(ModelAndView modelAndView,
			@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
			@RequestParam(value = "capacity", required = false) Long capacity) {
		Category categoryRequest = new Category();
		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 0;
		}
		paginationBean.setCurrentIndex(currentIndex);
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if (!ObjectUtils.isEmpty(capacity)) {
			paginationBean.setCapacity(capacity);
		}
		categoryRequest.setType(CategoryType.HELP_ROOT);

		List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.buildCategoryVoByCategories(categories);
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.addObject("paginationBean", paginationBean);
		modelAndView.setViewName("/help/listCategory");
		return modelAndView;
	}

	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public ModelAndView getArticle(ModelAndView modelAndView,
			@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
			@RequestParam(value = "capacity", required = false) Long capacity,
			@RequestParam(value = "categoryId", required = false) Integer categoryId) {
		HelpCenter helpCenter = new HelpCenter();
		if (!ObjectUtils.isEmpty(categoryId)) {
			Category category = new Category();
			category.setId(categoryId);
			helpCenter.setCategory(category);
		}
		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 0;
		}
		paginationBean.setCurrentIndex(currentIndex);
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if (!ObjectUtils.isEmpty(capacity)) {
			paginationBean.setCapacity(capacity);
		}

		List<Category> categories = categoryHelper.findHelpCenterCategories();
		List<HelpCenter> helpCenters = helpCenterService.queryHelpCenters(helpCenter, paginationBean);
		modelAndView.addObject("helpCenters", helpCenters);
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("paginationBean", paginationBean);
		modelAndView.setViewName("/help/listArticle");
		return modelAndView;
	}
}
