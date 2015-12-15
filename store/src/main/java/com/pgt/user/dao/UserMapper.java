package com.pgt.user.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.user.bean.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by cwang7 on 10/18/15.
 */
@Component
public interface UserMapper extends SqlMapper {
    User selectUser(int id);

    void create(User blog);

    void update(User blog);

    void delete(int id);

    List<User> selectAll();

    User authorize(String username);

    void updateLastLogin(Long id);
}
