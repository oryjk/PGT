package com.pgt.user.service.imp;

import java.util.List;

import com.pgt.user.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.user.bean.UserInformation;
import com.pgt.user.dao.UserInformationMapper;
import com.pgt.user.service.UserInformationService;

@Service
public class UserInformationImpl implements UserInformationService {

	@Autowired
	private UserInformationMapper userInformationMapper;

	public void createInformation(UserInformation userInformation) {

		userInformationMapper.createInformation(userInformation);

	}

	@Override
	public void updateUserInformation(UserInformation userInformation) {

		userInformationMapper.updateUserInformation(userInformation);

	}

	@Override
	public void deleteUserInformationById(Integer id) {

		userInformationMapper.deleteUserInformationById(id);

	}

	@Override
	public UserInformation queryUserInformation(User user) {

		return userInformationMapper.queryUserInformation(user);
	}

	@Override
	public List<UserInformation> queryAllUserInformations() {

		return userInformationMapper.queryAllUserInformations();
	}

}
