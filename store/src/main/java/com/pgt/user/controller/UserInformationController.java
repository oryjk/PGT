package com.pgt.user.controller;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.common.bean.Media;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import com.pgt.user.validation.group.AddUserInformationGroup;


/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/userinformation")
public class UserInformationController {

	@Autowired
	private UserInformationService userInformationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInformationController.class);

	// 创建一个用户信息
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private ModelAndView createUserInformation(ModelAndView modelAndView,
			@Validated(value = AddUserInformationGroup.class) UserInformation userInformation,
			BindingResult bindingResult, HttpSession session, Media frontMedia) {

		// 接收验证错误信息
		if (bindingResult.hasErrors()) {

			modelAndView.setViewName("");
			modelAndView.getModel().put("error", UserConstant.UNKNOWN_ERROR);
			return modelAndView;

		}

		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);

		if (user == null) {

		}

		userInformation.setUser(user);

		if (ObjectUtils.isEmpty(frontMedia)) {

		}

		userInformationService.createInformation(userInformation);

		modelAndView.setViewName("");
		return modelAndView;

	}

	// 用户前台查看个人详细信息
	@RequestMapping("")
	public ModelAndView queryUserInformation(ModelAndView modelAndView) {

		return null;
	}

	// 修改用户信息

	// 删除个人的用户信息

	// 批量删除个人信息

}
