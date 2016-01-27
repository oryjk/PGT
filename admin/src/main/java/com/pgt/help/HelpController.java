package com.pgt.help;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pgt.help.bean.HelpCenterSites;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.category.validation.CreateGroup;
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

	@RequestMapping(value = "/categoryList", method = RequestMethod.GET)
	public ModelAndView getCategory(ModelAndView modelAndView, RedirectAttributes redirectAttributes,
			@RequestParam(value = "currentPage", required = false) Integer currentPage,
			@RequestParam(value = "capacity", required = false) Long capacity) {
		Category categoryRequest = new Category();
		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentPage)) {
			currentPage = 1;
		}
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if (!ObjectUtils.isEmpty(capacity)) {
			paginationBean.setCapacity(capacity);
		}
		paginationBean.setCurrentIndex((currentPage - 1) * paginationBean.getCapacity());
		categoryRequest.setType(CategoryType.HELP_ROOT);

		paginationBean.setTotalAmount(categoryService.getHelpCategoryCount());
		List<Category> categories = categoryService.queryCategories(categoryRequest, paginationBean);
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.buildCategoryVoByCategories(categories);
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.addObject("paginationBean", paginationBean);
		modelAndView.setViewName("/help/listCategory");
		return modelAndView;
	}

	@RequestMapping(value = "addCategory", method = RequestMethod.GET)
	public ModelAndView addCategory(ModelAndView modelAndView) {
		modelAndView.setViewName("/help/addOrModifyCategory");
		modelAndView.addObject("actionUrl", "addCategory");
		return modelAndView;
	}

	@RequestMapping(value = "addCategory", method = RequestMethod.POST)
	public ModelAndView addCategory(ModelAndView modelAndView, @Validated(value = CreateGroup.class) Category category,
			BindingResult bindingResult) {
		modelAndView.setViewName("/help/addOrModifyCategory");
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			modelAndView.addObject("errors", errors);
			modelAndView.addObject("actionUrl", "addCategory");
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
	public ModelAndView updateCategory(@Validated(value = CreateGroup.class) Category category,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:updateCategory/" + category.getId());
		redirectAttributes.addFlashAttribute("actionUrl", "../updateCategory");
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			redirectAttributes.addFlashAttribute("errors", errors);
			return mav;
		}
		category.setType(CategoryType.HELP_ROOT);
		category.setCode("1");
		category.setColor("#006727");
		categoryService.updateCategory(category);
		mav.setViewName("redirect:/help/categoryList");
		return mav;
	}

	@RequestMapping(value = "/updateCategoryStatus", method = RequestMethod.POST)
	public ModelAndView updateCategoryStatus(@RequestParam(value = "categoryId", required = false) Integer categoryId,
			@RequestParam(value = "status", required = false) Integer status) {
		ModelAndView mav = new ModelAndView();
		Category category = categoryService.queryCategory(categoryId);
		category.setStatus(status);
		categoryService.updateCategory(category);
		mav.setViewName("redirect:/help/categoryList");
		return mav;
	}

	@RequestMapping(value = "/deleteCategory/{categoryId}", method = RequestMethod.GET)
	public ModelAndView deleteCategoryById(@PathVariable("categoryId") String categoryId, ModelAndView modelAndView) {
		categoryService.deleteCategory(Integer.parseInt(categoryId));
		modelAndView.setViewName("redirect:/help/categoryList");
		return modelAndView;
	}

	// Article Section
	@RequestMapping(value = "/articleList", method = RequestMethod.GET)
	public ModelAndView getArticle(ModelAndView modelAndView,
			@RequestParam(value = "currentIndex", required = false) Integer currentPage,
			@RequestParam(value = "capacity", required = false) Long capacity,
			@RequestParam(value = "categoryId", required = false) Integer categoryId) {
		HelpCenter helpCenter = new HelpCenter();
		if (!ObjectUtils.isEmpty(categoryId)) {
			Category category = new Category();
			category.setId(categoryId);
			helpCenter.setCategory(category);
		}
		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentPage)) {
			currentPage = 1;
		}
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if (!ObjectUtils.isEmpty(capacity)) {
			paginationBean.setCapacity(capacity);
		}
		paginationBean.setCurrentIndex((currentPage - 1) * paginationBean.getCapacity());

		List<Category> categories = categoryHelper.findHelpCenterCategories();
		List<HelpCenter> helpCentersList = helpCenterService.queryHelpCenters(helpCenter, null);
		paginationBean.setTotalAmount(helpCentersList.size());
		List<HelpCenter> helpCenters = helpCenterService.queryHelpCenters(helpCenter, paginationBean);
		modelAndView.addObject("helpCenters", helpCenters);
		modelAndView.addObject("categories", categories);
		modelAndView.addObject("paginationBean", paginationBean);
		modelAndView.setViewName("/help/listArticle");
		return modelAndView;
	}

	// 添加一个帮助信息
	@RequestMapping(value = "/addArticle", method = RequestMethod.POST)
	public ModelAndView addHelCenter(ModelAndView modelAndView, @Validated HelpCenter helpCenter,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
			modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
			modelAndView.setViewName("/help/addOrModifyArticle");
			modelAndView.addObject("errors", errors);
			modelAndView.addObject("actionUrl", "addArticle");
			return modelAndView;
		}
		helpCenterService.createHelpCenter(helpCenter);
		modelAndView.setViewName("redirect:/help/articleList");
		return modelAndView;
	}

	@RequestMapping(value = "/addArticle", method = RequestMethod.GET)
	public ModelAndView addHelpCenter(ModelAndView modelAndView) {
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.setViewName("/help/addOrModifyArticle");
		HelpCenterSites[] helpCenterSites= HelpCenterSites.values();
		modelAndView.addObject("helpCenterSites",helpCenterSites);
		modelAndView.addObject("actionUrl", "addArticle");
		return modelAndView;
	}

	@RequestMapping(value = "/updateArticle/{helpCenterId}", method = RequestMethod.GET)
	public ModelAndView updateHelpCenter(ModelAndView modelAndView, @PathVariable("helpCenterId") String helpCenterId) {
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.setViewName("/help/addOrModifyArticle");
		HelpCenter helpCenter = helpCenterService.findHelpCenterById(Integer.parseInt(helpCenterId));
		modelAndView.addObject("helpCenter", helpCenter);
		HelpCenterSites[] helpCenterSites= HelpCenterSites.values();
		modelAndView.addObject("helpCenterSites",helpCenterSites);
		modelAndView.addObject("actionUrl", "../updateArticle");
		return modelAndView;
	}

	@RequestMapping(value = "/updateArticle", method = RequestMethod.POST)
	public ModelAndView updateHlepCenter(@Validated HelpCenter helpCenter, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:updateArticle/" + helpCenter.getId());
		if (bindingResult.hasErrors()) {
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			redirectAttributes.addFlashAttribute("errors", errors);
			List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
			redirectAttributes.addFlashAttribute("helpCategorVoList", HelpCategorVoList);
			return mav;
		}
		helpCenterService.updateHelpCenter(helpCenter);
		mav.setViewName("redirect:/help/articleList");
		return mav;
	}

	@RequestMapping(value = "/deleteArticle/{helpCenterId}", method = RequestMethod.GET)
	public ModelAndView deleteHelpCenterById(@PathVariable("helpCenterId") String helpCenterId,
			ModelAndView modelAndView) {
		helpCenterService.deleteHelpCenterById(Integer.parseInt(helpCenterId));
		modelAndView.setViewName("redirect:/help/articleList");
		return modelAndView;
	}

}
