package com.pgt.interceptor;

import com.pgt.base.bean.MyAccountNavigationEnum;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.user.bean.User;
import com.pgt.user.bean.UserInformation;
import com.pgt.user.service.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is use to capsulation the my account need attribute, like my account navigationList,
 * <p>
 * Created by carlwang on 3/13/16.
 */
public class MyAccountInterceptor implements HandlerInterceptor {
    @Autowired
    private UserInformationService userInformationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (ObjectUtils.isEmpty(modelAndView)) {
            return;
        }
        List<MyAccountNavigationEnum> navigationList = new ArrayList<>();
        navigationList.add(MyAccountNavigationEnum.MY_ORDER);
        navigationList.add(MyAccountNavigationEnum.MY_ASSET);
        navigationList.add(MyAccountNavigationEnum.MY_FAVORITE);
        navigationList.add(MyAccountNavigationEnum.MY_RECENT_VIEW);
        navigationList.add(MyAccountNavigationEnum.MY_INFO);
        navigationList.add(MyAccountNavigationEnum.MY_ADDRESS);
        navigationList.add(MyAccountNavigationEnum.CHANGE_PASSWORD);
        modelAndView.addObject(Constants.NAVIGATION_LIST, navigationList);
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (!ObjectUtils.isEmpty(user)) {
            UserInformation userInformation = userInformationService.queryUserInformation(user);
            modelAndView.addObject(UserConstant.USER_INFO, userInformation);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
