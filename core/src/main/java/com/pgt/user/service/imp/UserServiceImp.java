package com.pgt.user.service.imp;

import com.pgt.cart.util.FieldsRegexValidator;
import com.pgt.user.bean.User;
import com.pgt.user.dao.UserMapper;
import com.pgt.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by cwang7 on 10/18/15.
 */
@Service
public class UserServiceImp implements UserService {

    @Resource(name = "passwordRegexValidator")
    private FieldsRegexValidator passwordRegexValidator;

    @Resource(name = "loginRegexValidator")
    private FieldsRegexValidator loginRegexValidator;

    @Autowired
    private UserMapper userMapper;


    @Override

    public User findUser(String id) {
        return null;
    }

    @Override

    public void saveUser(User user) {
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setSalt(System.currentTimeMillis() + "");
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + user.getSalt());
        user.setPassword(encryptedPassword);
        user.setAvailable(true);
        userMapper.create(user);

    }

    @Override
    public void deleteUser(String id) {

    }

    @Override
    @Test
    public List<User> findAll() {
        return null;
    }

    @Override
    public void updateUserPassword(User user) {
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + user.getSalt());
        user.setPassword(encryptedPassword);
        userMapper.update(user);
    }
    
    
    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public User findUser(String username, String password) {
        return null;
    }

    @Override
    public User authorize(String username) {
        return userMapper.authorize(username);
    }

    @Override
    public void updateLastLogin(Long id) {
        userMapper.updateLastLogin(id);
    }

    @Override
    public boolean checkExist(String username) {
        User user = authorize(username);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        return true;
    }

    public FieldsRegexValidator getPasswordRegexValidator() {
        return passwordRegexValidator;
    }

    public void setPasswordRegexValidator(FieldsRegexValidator passwordRegexValidator) {
        this.passwordRegexValidator = passwordRegexValidator;
    }

    public FieldsRegexValidator getLoginRegexValidator() {
        return loginRegexValidator;
    }

    public void setLoginRegexValidator(FieldsRegexValidator loginRegexValidator) {
        this.loginRegexValidator = loginRegexValidator;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
}
