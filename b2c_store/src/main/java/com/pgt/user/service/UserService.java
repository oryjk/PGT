package com.pgt.user.service;

import com.pgt.user.bean.User;

import java.util.List;

/**
 * Created by cwang7 on 10/18/15.
 */
public interface UserService {
    User findUser(String id);

    void saveUser(User user);

    void deleteUser(String id);

    List<User> findAll();

    void updateUser(User user);

    User findUser(String username, String password);

    User authorize(String username);

    void updateLastLogin(Long id);

    boolean checkExist(String username);
    
    void updateUserPassword(User user);
}
