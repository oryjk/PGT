package com.pgt.interceptor;
import com.pgt.base.constans.MobileConstants;
import com.pgt.constant.PathConstant;
import com.pgt.constant.UserConstant;
import com.pgt.token.bean.Token;
import com.pgt.token.service.TokenService;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import com.pgt.utils.ResponseUtils;
import com.pgt.utils.UserAgentUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by xiaodong on 16-1-15.
 */
public class TokenInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenInterceptor.class);

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        JSONObject jo = new JSONObject();

        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        LOGGER.info("requestUri:" + requestUri);
        LOGGER.info("contextPath:" + contextPath);
        LOGGER.info("url:" + url);
        String agent= request.getHeader("User-Agent").toLowerCase();
        LOGGER.debug("The request agent is {}",agent);

        if(UserAgentUtils.isWebBrowser(agent)==true){
            LOGGER.debug("The mobile web Release");
            return true;
        }

        LOGGER.debug("TokenInterceptor has run");
        User user= (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        LOGGER.debug("The session createTime {}",request.getSession().getCreationTime());
        LOGGER.debug("The session lastAccessTime {}",request.getSession().getLastAccessedTime());


        //不需要登陆的请求,放行
        if (url.matches(PathConstant.NO_LOGIN_TOKEN_PATH)) {
            LOGGER.debug("Not need interceptor.");
            return true;
        }

        //session中存在user信息,放行
        if(!ObjectUtils.isEmpty(user)){
            LOGGER.debug("The user is success");
          return true;
        }


        String tokenNumber = request.getParameter("token");
        if(StringUtils.isEmpty(tokenNumber)){
            LOGGER.debug("The tokenNumber is empty");
            jo.put("message","The token number is empty");
            ResponseUtils.renderJson(response, jo.toString());
            return false;
        }
        String username=request.getParameter("username");
        if(StringUtils.isEmpty(username)){
            LOGGER.debug("The username is empty");
            jo.put("message","The username is empty");
            ResponseUtils.renderJson(response, jo.toString());
            return false;
        }
        String phoneId=request.getParameter("phoneId");
        if(StringUtils.isEmpty(phoneId)){
            LOGGER.debug("The phoneId is empty");
            jo.put("message","The phoneId is empty");
            ResponseUtils.renderJson(response, jo.toString());
            return false;
        }

        Token tokenQuery = new Token();
        tokenQuery.setUsername(username);
        tokenQuery.setPhoneId(phoneId);
        tokenQuery.setNowDate(new Date());
        Token tokenResult =tokenService.queryToken(tokenQuery);

        if(tokenNumber.endsWith(tokenResult.getTokenNumber())){
            //deal log
            User userResult = userService.authorize(username);
            request.getSession().setAttribute(UserConstant.CURRENT_USER,userResult);
            return true;
        }else{
            jo.put("message","need.Login");
            jo.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_SUCCESS);
            ResponseUtils.renderJson(response, jo.toString());
            return false;
        }
    }





    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

}
