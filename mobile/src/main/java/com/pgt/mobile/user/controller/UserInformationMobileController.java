package com.pgt.mobile.user.controller;

import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.UserConstant;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/mUserinformation")
public class UserInformationMobileController {

	@Autowired
	private UserInformationService userInformationService;

	@Autowired
	private URLConfiguration urlConfiguration;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInformationMobileController.class);


	// 创建或修改一个用户信息
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private @ResponseBody Map<String,Object> createUserInformation(UserInformation userInformation,
			BindingResult bindingResult, HttpSession session) {
         Map<String,Object> responseMap=new HashMap<String,Object>();
		// 接收验证错误信息
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (ObjectUtils.isEmpty(user)) {
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.empty");
			return responseMap;
		}
		if(StringUtils.isEmpty(userInformation.getNickname())){
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.empty.nickname");
            return responseMap;
		}
		if(StringUtils.isEmpty(userInformation.getIdCard())){
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.empty.IdCard");
            return responseMap;
		}
		if(StringUtils.isEmpty(userInformation.getPersonEmail())){
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.empty.PersonEmail");
		}
		userInformation.setPhoneNumber(user.getPhoneNumber());
		userInformation.setUser(user);
		UserInformation olduserInformation = userInformationService.queryUserInformation(user);
		if(!ObjectUtils.isEmpty(olduserInformation)){
			userInformation.setId(olduserInformation.getId());
			userInformationService.updateUserInformation(userInformation);
		}else{
			userInformationService.createInformation(userInformation);
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		return responseMap;
	}

	// 用户前台查看个人详细信息
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public @ResponseBody  Map<String,Object> queryUserInformation(HttpSession session) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if (user == null) {
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.empty");
			return responseMap;
		}
	    UserInformation userInformation = userInformationService.queryUserInformation(user);
        if(ObjectUtils.isEmpty(userInformation)){
			responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
			responseMap.put(MobileConstans.MOBILE_MESSAGE,"UserInformation.empty");
			return responseMap;
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		responseMap.put("userInformation",userInformation);
		return responseMap;
	}

}
