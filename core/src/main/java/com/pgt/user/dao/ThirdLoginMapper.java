package com.pgt.user.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.user.bean.ThirdLogin;
import org.springframework.stereotype.Component;

/**
 * Created by zhangxiaodong on 16-3-7.
 */
@Component
public interface ThirdLoginMapper extends SqlMapper{


    void createThirdLogin(ThirdLogin thirdLogin);

    void updateThirdLogin(ThirdLogin thirdLogin);

    void deleteThirdLoginById(Integer id);

    ThirdLogin queryThirdLoginById(Integer id);

}
