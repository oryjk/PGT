package com.pgt.mobile.user.controller;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.mobile.base.controller.BaseMobileController;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.mobile.utils.TokenUtils;
import com.pgt.token.bean.Token;
import com.pgt.token.service.TokenService;
import com.pgt.user.bean.User;
import com.pgt.user.service.imp.UserServiceImp;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangxiaodong 2015年12月5日
 */
@RestController
@RequestMapping("/mUser")
public class UserMobileController extends BaseMobileController {

    @Resource
    private UserServiceImp userServiceImp;

    @Autowired
    private URLConfiguration urlConfiguration;

    @Resource(name = "productBrowseTrackService")
    private ProductBrowseTrackService mProductBrowseTrackService;

    @Resource(name = "accountInfoYeepay")
    private DirectYeePay accountInfoYeepay;

    @Autowired
    private SimpleCacheManager cacheManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private Configuration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserMobileController.class);


    @RequestMapping(value = "/mLogin", method = RequestMethod.POST)
    public  Map<String, Object> mobileLogin(User user,HttpServletRequest request) {

        Map<String, Object> responseMap = new HashMap<>();

        if (ObjectUtils.isEmpty(user)) {
            return responseMobileFail(responseMap, "User.empty");
        }
        if(StringUtils.isEmpty(user.getPhoneId())){
            return responseMobileFail(responseMap, "PhoneId.empty");
        }
        LOGGER.debug("The phone id is {}.", user.getPhoneId());
        User oldUser = (User)request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if(!ObjectUtils.isEmpty(oldUser)){
            return responseMobileFail(responseMap, "User.alreadyLogin");
        }

        if (StringUtils.isEmpty(user.getUsername())) {
          return   responseMobileFail(responseMap, "Error.empty.username");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
           return  responseMobileFail(responseMap, "Error.empty.password");
        }
        User userResult = userServiceImp.authorize(user.getUsername());
        if (ObjectUtils.isEmpty(userResult)) {
           return  responseMobileFail(responseMap, "Error.not.find.user");
        }
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + userResult.getSalt());
        if (!userResult.getPassword().equals(encryptedPassword)) {
            return responseMobileFail(responseMap, "Error.password.error");
        }

        TransactionStatus status = ensureTransaction();
        // update last login date
        userServiceImp.updateLastLogin(userResult.getId());

        Token token = new Token();
        Token tokenQuery = new Token();
        tokenQuery.setUsername(user.getUsername());
        tokenQuery.setPhoneId(user.getPhoneId());
        Token tokenResult =tokenService.queryToken(tokenQuery);
        if(!ObjectUtils.isEmpty(tokenResult)){
            tokenService.deleteTokenById(tokenResult.getId());
        }
        String tokenNumber =TokenUtils.generateToken(user.getPhoneId(),user.getUsername());
        token.setCreateDate(new Date());
        token.setPhoneId(user.getPhoneId());
        token.setUsername(user.getUsername());
        token.setOutDate(TokenUtils.getTokenOutTime());
        token.setTokenNumber(tokenNumber);
        tokenService.createToken(token);
        getTransactionManager().commit(status);

        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        responseMap.put("user", userResult);
        responseMap.put("token",tokenNumber);
        HttpSession session=request.getSession();
        session.setAttribute(UserConstant.CURRENT_USER,userResult);
        session.setAttribute(UserConstant.TOKEN,token);
        return responseMap;
    }


    @RequestMapping(value = "/mRegister", method = RequestMethod.POST)
    public  Map<String, Object> mobileRegister(User user, HttpServletRequest request) {

        LOGGER.debug("The username is {},the password is {}.",user.getUsername(), user.getPassword());
        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
           return  responseMobileFail(responseMap, "User.empty");
        }
        user.setUsername(user.getPhoneNumber());
        boolean isExist = userServiceImp.checkExist(user.getUsername());
        if (isExist) {
           return responseMobileFail(responseMap, "User.exist");
        }
        String phoneId = user.getPhoneId();
        LOGGER.debug("The phone id is {}.", phoneId);
        Cache cache = cacheManager.getCache(Constants.PHONE_CODE);
        Cache.ValueWrapper valueWrapper = cache.get(phoneId);
        if (ObjectUtils.isEmpty(valueWrapper)) {
           return responseMobileFail(responseMap, "User.no.send.sms");
        }
        String phoneCode = (String) valueWrapper.get();
        LOGGER.debug("The phone code is {}.", phoneCode);
        if (!user.getSmsCode().equals(phoneCode)) {
           return responseMobileFail(responseMap, "User.phone.code.error");
        }
        userServiceImp.saveUser(user);
        LOGGER.debug("success for register.");
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }


    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public  Map<String,Object> updatePassword(User newUserPassword,HttpSession session, String oldpassword){

        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(newUserPassword)) {
            responseMobileFail(responseMap, "User.empty");
        }
        if(StringUtils.isEmpty(newUserPassword.getPhoneId())){
            return responseMobileFail(responseMap, "PhoneId.empty");
        }
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if(ObjectUtils.isEmpty(user)){
            return responseMobileFail(responseMap, "User.empty");
        }

        if(StringUtils.isEmpty(newUserPassword.getPhoneId())){
            return responseMobileFail(responseMap, "PhoneId.empty");
        }
        //旧密码为空
        if(ObjectUtils.isEmpty(oldpassword)){
            return responseMobileFail(responseMap, "oldpassword.empty");
        }
        String oldMd5Password = DigestUtils.md5Hex(oldpassword+ user.getSalt());
        //旧密码输入不正确
        if(!oldMd5Password.endsWith(user.getPassword())){
            return responseMobileFail(responseMap, "olpassword.input.error");
        }
        //修改密码
        if (newUserPassword.getPassword().equals(newUserPassword.getPassword2())) {
            user.setPassword(newUserPassword.getPassword());
            user.setPassword2(newUserPassword.getPassword2());
            userServiceImp.updateUserPassword(user);
        }
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return  responseMap;
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public  Map<String,Object> logout(HttpServletRequest request,String phoneId,String username,String token) {
        Map<String,Object> responseMap = new HashMap<String,Object>();
        if(StringUtils.isEmpty(phoneId)) {
            return responseMobileFail(responseMap, "PhoneId.empty");
        }
        HttpSession session=request.getSession();
        session.removeAttribute(UserConstant.CURRENT_USER);
        session.removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        session.removeAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        session.removeAttribute(CartConstant.CURRENT_ORDER);

        Token tokenQuery = new Token();
        tokenQuery.setUsername(username);
        tokenQuery.setPhoneId(phoneId);
        Token tokenResult =tokenService.queryToken(tokenQuery);
        tokenService.deleteTokenById(tokenResult.getId());
        LOGGER.debug("The logout userId is {} ",tokenQuery.getId());
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }


    @RequestMapping(value = "/resetPassword",method=RequestMethod.POST)
    public  Map<String,Object> resetPassword(User resetUser,HttpServletRequest request,HttpSession session){

        Map<String,Object> responseMap = new HashMap<String,Object>();
        if(StringUtils.isEmpty(resetUser.getPhoneId())) {
            return responseMobileFail(responseMap, "PhoneId.empty");
        }
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if(ObjectUtils.isEmpty(user)){
            return responseMobileFail(responseMap, "User.empty");
        }
        if(ObjectUtils.isEmpty(resetUser)){
          return  responseMobileFail(responseMap, "restUser.empty");
        }
        if(StringUtils.isEmpty(resetUser.getPassword())){
           return responseMobileFail(responseMap, "password.empty");
        }
        if(StringUtils.isEmpty(resetUser.getSmsCode())){
           return responseMobileFail(responseMap, "smsCode.empty");
        }

        String phoneId = resetUser.getPhoneId();
        LOGGER.debug("The phone id is {}.", phoneId);
        Cache cache = cacheManager.getCache(Constants.PHONE_CODE);
        Cache.ValueWrapper valueWrapper = cache.get(phoneId);
        if (ObjectUtils.isEmpty(valueWrapper)) {
         return   responseMobileFail(responseMap, "User.no.send.sms");
        }
        String phoneCode = (String) valueWrapper.get();
        LOGGER.debug("The phone code is {}.", phoneCode);
        if (!resetUser.getSmsCode().equals(phoneCode)) {
           return responseMobileFail(responseMap, "User.phone.code.error");
        }

        //修改密码
        user.setPassword(resetUser.getPassword());
        userServiceImp.updateUserPassword(user);
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }


    @Autowired
    private DataSourceTransactionManager mTransactionManager;

    protected TransactionStatus ensureTransaction() {
        return ensureTransaction(TransactionDefinition.PROPAGATION_REQUIRED);
    }

    protected TransactionStatus ensureTransaction(int pPropagationBehavior) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(pPropagationBehavior);
        TransactionStatus status = getTransactionManager().getTransaction(def);
        return status;
    }

    public DataSourceTransactionManager getTransactionManager() {
        return mTransactionManager;
    }

    public void setTransactionManager(final DataSourceTransactionManager pTransactionManager) {
        mTransactionManager = pTransactionManager;
    }

    public URLConfiguration getUrlConfiguration() {
        return urlConfiguration;
    }

    public void setUrlConfiguration(URLConfiguration urlConfiguration) {
        this.urlConfiguration = urlConfiguration;
    }

    public ProductBrowseTrackService getProductBrowseTrackService() {
        return mProductBrowseTrackService;
    }

    public void setProductBrowseTrackService(final ProductBrowseTrackService pProductBrowseTrackService) {
        mProductBrowseTrackService = pProductBrowseTrackService;
    }

    public DirectYeePay getAccountInfoYeepay() {
        return accountInfoYeepay;
    }

    public void setAccountInfoYeepay(DirectYeePay accountInfoYeepay) {
        this.accountInfoYeepay = accountInfoYeepay;
    }

    public SimpleCacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(SimpleCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
}
