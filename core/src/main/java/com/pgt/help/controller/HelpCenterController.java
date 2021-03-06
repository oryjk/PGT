package com.pgt.help.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.constant.Constants;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.service.HelpCenterService;

@RestController
@RequestMapping("/helpcenter")
public class HelpCenterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(HelpCenterController.class);

	@Autowired
	private HelpCenterService helpCenterService;

	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView queryAllHelpCenter(ModelAndView modelAndView) {
		LOGGER.debug("The method query AllHelpCenter");
		modelAndView.setViewName(Constants.HELP_CENTER);
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		HelpCenter helpCenter = helpCenterService.findHelpCenterById(Integer.parseInt("1"));
		modelAndView.addObject("helpCenter", helpCenter);
		return modelAndView;
	}

	@RequestMapping(value = "/{helpCenterId}", method = RequestMethod.GET)
	public ModelAndView queryHelpCenterById(ModelAndView modelAndView,
			@PathVariable("helpCenterId") String helpCenterId) {
		LOGGER.debug("The method queryHelpCenterById");
		if (helpCenterId == null) {
			helpCenterId = "1";
		}
		List<HelpCategoryVo> HelpCategorVoList = helpCenterService.findAllHelpCategoryVo();
		modelAndView.addObject("helpCategorVoList", HelpCategorVoList);
		modelAndView.setViewName(Constants.HELP_CENTER);
		HelpCenter helpCenter = helpCenterService.findHelpCenterById(Integer.parseInt(helpCenterId));
		LOGGER.debug("The helpCenter id is {}",helpCenter.getId());
		modelAndView.addObject("helpCenter", helpCenter);
		return modelAndView;
	}

	@RequestMapping(value = "/mQueryList", method = RequestMethod.POST)
	public Map<String,Object> mQueryAllHelpCenter() {
		LOGGER.debug("The method to query helpCenter");
		Map<String,Object> responseMap= new HashMap<>();
		List<HelpCategoryVo> helpCategoryList = helpCenterService.findAllHelpCategoryVo();
		responseMap.put("helpCategoryList",helpCategoryList);
		return responseMap;
	}

	@RequestMapping(value = "/mQueryById", method = RequestMethod.POST)
	public Map<String,Object> mQueryHelpCenterById(String helpCenterId){
		Map<String,Object> responseMap= new HashMap<>();
		LOGGER.debug("The method to query helpCenterById");
		if (helpCenterId == null) {
			helpCenterId = "1";
		}
		HelpCenter helpCenter = helpCenterService.findHelpCenterById(Integer.parseInt(helpCenterId));
		responseMap.put("helpCenter", helpCenter);
		return responseMap;
	}


}
