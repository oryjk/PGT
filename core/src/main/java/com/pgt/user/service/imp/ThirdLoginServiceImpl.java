package com.pgt.user.service.imp;

import com.pgt.user.bean.ThirdLogin;
import com.pgt.user.dao.ThirdLoginMapper;
import com.pgt.user.service.ThirdLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangxiaodong on 16-3-7.
 */
@Service
public class ThirdLoginServiceImpl implements ThirdLoginService {

    @Autowired
    private ThirdLoginMapper thirdLoginMapper;

    @Override
    public void createThirdLogin(ThirdLogin thirdLogin) {
    thirdLoginMapper.createThirdLogin(thirdLogin);
    }

    @Override
    public void updateThirdLogin(ThirdLogin thirdLogin) {
     thirdLoginMapper.updateThirdLogin(thirdLogin);
    }

    @Override
    public void deleteThirdLoginById(Integer id) {
     thirdLoginMapper.deleteThirdLoginById(id);
    }

    @Override
    public ThirdLogin queryThirdLoginById(Integer id) {
        return  thirdLoginMapper.queryThirdLoginById(id);
    }
}
