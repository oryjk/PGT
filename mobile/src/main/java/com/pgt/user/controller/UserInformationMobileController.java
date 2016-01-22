package com.pgt.user.controller;

import com.pgt.constant.UserConstant;
import com.pgt.base.controller.BaseMobileController;
import com.pgt.base.constans.MobileConstants;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * The method to create or update userInformation
	 * @param userInformation
	 * @param session
     * @return json  return message sunncess or fail
     */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Map<String,Object> createUserInformation(UserInformation userInformation,HttpSession session) {
         Map<String,Object> responseMap=new HashMap<>();
		LOGGER.debug("The method create userInformation");
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if(ObjectUtils.isEmpty(user)){
			LOGGER.debug("The user is empty");
			return responseMobileFail(responseMap, "User.empty");
		}
		if(StringUtils.isEmpty(userInformation.getNickname())){
			LOGGER.debug("Error.empty.nickname");
            return responseMobileFail(responseMap, "Error.empty.nickname");
		}
		if(StringUtils.isEmpty(userInformation.getIdCard())){
			LOGGER.debug("Error.empty.IdCard");
			return responseMobileFail(responseMap, "Error.empty.IdCard");
		}
		if(StringUtils.isEmpty(userInformation.getPersonEmail())){
			LOGGER.debug("Error.empty.PersonEmail");
			return responseMobileFail(responseMap, "Error.empty.PersonEmail");
		}
		userInformation.setPhoneNumber(user.getPhoneNumber());
		userInformation.setUser(user);
		UserInformation olduserInformation = userInformationService.queryUserInformation(user);
		if(!ObjectUtils.isEmpty(olduserInformation)){
			userInformation.setId(olduserInformation.getId());
			userInformationService.updateUserInformation(userInformation);
			LOGGER.debug("update a userInformation");
		}else{
			userInformationService.createInformation(userInformation);
			LOGGER.debug("create a userInformation");
		}
		responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
		return responseMap;
	}

	/**
	 * The method query userInformation
	 * @param session
	 * @return
     */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public  Map<String,Object> queryUserInformation(HttpSession session) {
		Map<String,Object> responseMap = new HashMap<>();
		LOGGER.debug("The method query userInformation");
		User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
		if(ObjectUtils.isEmpty(user)){
			LOGGER.debug("User.empty");
			return responseMobileFail(responseMap, "User.empty");
		}
	    UserInformation userInformation = userInformationService.queryUserInformation(user);
        if(ObjectUtils.isEmpty(userInformation)){
			LOGGER.debug("UserInformation.empty");
			return responseMobileFail(responseMap, "UserInformation.empty");
		}
		responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
		responseMap.put("userInformation",userInformation);
		return responseMap;
	}

}
