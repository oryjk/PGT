package com.pgt.mobile.user.controller;

import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
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
    public Map<String, Object> mobileLogin(User user) {

        LOGGER.debug("mLogin");

        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put("error", "User.empty");
            return responseMap;
        }

        if (StringUtils.isBlank(user.getUsername())) {
            responseMap.put("error", "Error.empty.username");
            return responseMap;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            responseMap.put("error", "Error.empty.password");
            return responseMap;
        }
        User userResult = userServiceImp.authorize(user.getUsername());
        if (ObjectUtils.isEmpty(userResult)) {
            responseMap.put("error", "Error.not.find.user");
            return responseMap;
        }
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + userResult.getSalt());
        if (!userResult.getPassword().equals(encryptedPassword)) {
            responseMap.put("error", "Error.password.error");
            return responseMap;
        }
        TransactionStatus status = ensureTransaction();
        // update last login date
        userServiceImp.updateLastLogin(userResult.getId());
        getTransactionManager().commit(status);
        responseMap.put("success", "success");
        responseMap.put("user", user);
        return responseMap;

    }


    @RequestMapping(value = "/mRegister", method = RequestMethod.POST)
    public Map<String, Object> mobileRegister(User user) {

        LOGGER.debug("The username is {},the password is {}.",user.getUsername(), user.getPassword());
        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put("error", "User.empty");
            return responseMap;
        }
        user.setPhoneNumber(user.getUsername());
        boolean isExist = userServiceImp.checkExist(user.getUsername());
        if (isExist) {
            responseMap.put("error", "User.exist");
            return responseMap;
        }
        String phoneId = user.getPhoneId();
        LOGGER.debug("The phone id is {}.", phoneId);
        Cache cache = cacheManager.getCache(Constants.PHONE_CODE);
        Cache.ValueWrapper valueWrapper = cache.get(phoneId);
        if (ObjectUtils.isEmpty(valueWrapper)) {
            responseMap.put("error", "User.no.send.sms");
            return responseMap;
        }
        String phoneCode = (String) valueWrapper.get();
        LOGGER.debug("The phone code is {}.", phoneCode);
        if (!user.getSmsCode().equals(phoneCode)) {
            responseMap.put("error", "User.phone.code.error");
            return responseMap;
        }

        userServiceImp.saveUser(user);
        LOGGER.debug("success for register.");
        responseMap.put("success", "success");
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
