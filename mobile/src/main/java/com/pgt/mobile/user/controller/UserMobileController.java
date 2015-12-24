package com.pgt.mobile.user.controller;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.mobile.base.constans.MobileConstans;
import com.pgt.user.bean.ResetPasswordStep;
import com.pgt.user.bean.User;
import com.pgt.user.service.imp.UserServiceImp;
import com.pgt.utils.ErrorMsgUtil;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/mUser")
public class UserMobileController {

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
    private Configuration configuration;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserMobileController.class);


    @RequestMapping(value = "/mLogin", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> mobileLogin(User user) {

        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.empty");
            return responseMap;
        }

        if (StringUtils.isBlank(user.getUsername())) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.empty.username");
            return responseMap;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.empty.password");
            return responseMap;
        }
        User userResult = userServiceImp.authorize(user.getUsername());
        if (ObjectUtils.isEmpty(userResult)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.not.find.user");
            return responseMap;
        }

        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + userResult.getSalt());
        if (!userResult.getPassword().equals(encryptedPassword)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"Error.password.error");
            return responseMap;
        }
        TransactionStatus status = ensureTransaction();
        // update last login date
        userServiceImp.updateLastLogin(userResult.getId());
        getTransactionManager().commit(status);
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        responseMap.put("user", user);
        return responseMap;

    }


    @RequestMapping(value = "/mRegister", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> mobileRegister(User user, HttpServletRequest request) {

        LOGGER.debug("The username is {},the password is {}.",user.getUsername(), user.getPassword());
        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.empty");
            return responseMap;
        }
        user.setUsername(user.getPhoneNumber());
        boolean isExist = userServiceImp.checkExist(user.getUsername());
        if (isExist) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.exist");
            return responseMap;
        }
        String phoneId = user.getPhoneId();
        LOGGER.debug("The phone id is {}.", phoneId);
        Cache cache = cacheManager.getCache(Constants.PHONE_CODE);
        Cache.ValueWrapper valueWrapper = cache.get(phoneId);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.no.send.sms");
            return responseMap;
        }
        String phoneCode = (String) valueWrapper.get();
        LOGGER.debug("The phone code is {}.", phoneCode);
        if (!user.getSmsCode().equals(phoneCode)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.phone.code.error");
            return responseMap;
        }
        userServiceImp.saveUser(user);
        LOGGER.debug("success for register.");
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }


    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public @ResponseBody  Map<String,Object> updatePassword(User newUserPassword,HttpSession session, String oldpassword){

        Map<String, Object> responseMap = new HashMap<>();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.empty");
        }
        //旧密码为空
        if(ObjectUtils.isEmpty(oldpassword)){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"oldpassword.empty");
            return responseMap;
        }
        String oldMd5Password = DigestUtils.md5Hex(oldpassword+ user.getSalt());
        //旧密码输入不正确
        if(!oldMd5Password.endsWith(user.getPassword())){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"olpassword.input.error");
            return responseMap;
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
    public @ResponseBody Map<String,Object> logout(HttpSession session) {

        Map<String,Object> responseMap = new HashMap<String,Object>();
        session.removeAttribute(UserConstant.CURRENT_USER);
        session.removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        session.removeAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        session.removeAttribute(CartConstant.CURRENT_ORDER);

        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_SUCCESS);
        return responseMap;
    }


    @RequestMapping(value = "/resetPassword",method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> resetPassword(User resetUser,HttpSession session,HttpServletRequest request){

        Map<String,Object> responseMap = new HashMap<String,Object>();

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);

        if(ObjectUtils.isEmpty(user)){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE, "user.empty");
            return responseMap;
        }

        if(ObjectUtils.isEmpty(resetUser)){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE, "restUser.empty");
            return responseMap;
        }

        if(StringUtils.isEmpty(resetUser.getPassword())){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"password.empty");
            return responseMap;
        }

        if(StringUtils.isEmpty(resetUser.getSmsCode())){
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"smsCode.empty");
            return responseMap;
        }

        String phoneId = resetUser.getPhoneId();
        LOGGER.debug("The phone id is {}.", phoneId);
        Cache cache = cacheManager.getCache(Constants.PHONE_CODE);
        Cache.ValueWrapper valueWrapper = cache.get(phoneId);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.no.send.sms");
            return responseMap;
        }
        String phoneCode = (String) valueWrapper.get();
        LOGGER.debug("The phone code is {}.", phoneCode);
        if (!resetUser.getSmsCode().equals(phoneCode)) {
            responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
            responseMap.put(MobileConstans.MOBILE_MESSAGE,"User.phone.code.error");
            return responseMap;
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
