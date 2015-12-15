package com.pgt.user.service;

import java.util.List;

import com.pgt.user.bean.UserInformation;

public interface UserInformationService {

	void createInformation(UserInformation userInformation);

	void updateUserInformation(UserInformation userInformation);

	void deleteUserInformationById(Integer id);

	UserInformation queryUserInformation();

	List<UserInformation> queryAllUserInformations();

}
