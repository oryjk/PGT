package com.pgt.user.service.imp;

import com.pgt.base.service.TransactionService;
import com.pgt.cart.util.FieldsRegexValidator;
import com.pgt.user.bean.User;
import com.pgt.user.dao.UserMapper;
import com.pgt.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by cwang7 on 10/18/15.
 */
@Service
public class UserServiceImp extends TransactionService implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImp.class);
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
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            user.setCreateDate(new Date());
            user.setUpdateDate(new Date());
            user.setSalt(System.currentTimeMillis() + "");
            String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + user.getSalt());
            user.setPassword(encryptedPassword);
            user.setAvailable(true);
            userMapper.create(user);
        } catch (Exception e) {
            LOGGER.error("The error message is {}.", e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
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
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + user.getSalt());
            user.setPassword(encryptedPassword);
            userMapper.update(user);
        } catch (Exception e) {
            LOGGER.error("The error message is {}.", e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }


    @Override
    public void updateUser(User user) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            userMapper.update(user);
        } catch (Exception e) {
            LOGGER.error("The error message is {}.", e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }

    @Override
    public User findUser(String username, String password) {
        return null;
    }

    @Override
    public User authorize(String username) {
        List<User> users = userMapper.authorize(username);
        if (CollectionUtils.isEmpty(users)) {
            return null;
        }
        return users.get(0);
    }

    @Override
    public void updateLastLogin(Long id) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            userMapper.updateLastLogin(id);
        } catch (Exception e) {
            LOGGER.error("The error message is {}.", e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }

    @Override
    public boolean checkExist(String username) {
        User user = authorize(username);
        return !ObjectUtils.isEmpty(user);
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
