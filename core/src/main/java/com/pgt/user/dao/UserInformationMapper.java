package com.pgt.user.dao;

import java.util.List;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import org.springframework.stereotype.Component;

/**
 * @author zhangxiaodong
 *
 *         2015年12月4日
 */

@Component
public interface UserInformationMapper extends SqlMapper {

	void createInformation(UserInformation userInformation);

	void updateUserInformation(UserInformation userInformation);

	void deleteUserInformationById(Integer id);

	UserInformation queryUserInformation(User user);

	List<UserInformation> queryAllUserInformations();

}
