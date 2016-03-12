package com.pgt.user.service;

import com.pgt.user.bean.ThirdLogin;

/**
 * Created by zhangxiaodong on 16-3-7.
 */
public interface ThirdLoginService {

    void createThirdLogin(ThirdLogin thirdLogin);

    void updateThirdLogin(ThirdLogin thirdLogin);

    void deleteThirdLoginById(Integer id);

    ThirdLogin queryThirdLoginById(Integer id);

    ThirdLogin queryThirdLoginByToken(String token);

    ThirdLogin queryThirdLoginByOpenId(String openId);
}
