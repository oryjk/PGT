package com.pgt.mobile.user.controller;

import com.pgt.constant.UserConstant;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/mUserinformation")
public class UserInformationMobileController extends BaseMobileController {

	@Autowired
	private UserInformationService userInformationService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserInformationMobileController.class);

	// 创建或修改一个用户信息
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object> createUserInformation(UserInformation userInformation,String phoneId,HttpSession session) {
         Map<String,Object> responseMap=new HashMap<String,Object>();

		if(StringUtils.isEmpty(phoneId)){
			return responseMobileFail(responseMap, "PhoneId.empty");
		}
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if(ObjectUtils.isEmpty(user)){
			return responseMobileFail(responseMap, "User.empty");
		}

		if(StringUtils.isEmpty(userInformation.getNickname())){
            return responseMobileFail(responseMap, "Error.empty.nickname");
		}
		if(StringUtils.isEmpty(userInformation.getIdCard())){
			return responseMobileFail(responseMap, "Error.empty.IdCard");
		}
		if(StringUtils.isEmpty(userInformation.getPersonEmail())){
			return responseMobileFail(responseMap, "Error.empty.PersonEmail");
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
	public  Map<String,Object> queryUserInformation(HttpSession session,String phoneId) {
		Map<String,Object> responseMap = new HashMap<String,Object>();

		if(StringUtils.isEmpty(phoneId)){
			return responseMobileFail(responseMap, "PhoneId.empty");
		}

		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if(ObjectUtils.isEmpty(user)){
			return responseMobileFail(responseMap, "User.empty");
		}

	    UserInformation userInformation = userInformationService.queryUserInformation(user);
        if(ObjectUtils.isEmpty(userInformation)){
			return responseMobileFail(responseMap, "UserInformation.empty");
		}
		responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
		responseMap.put("userInformation",userInformation);
		return responseMap;
	}

}
