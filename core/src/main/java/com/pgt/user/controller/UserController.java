package com.pgt.user.controller;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.integration.yeepay.YeePayConstants;
import com.pgt.integration.yeepay.YeePayHelper;
import com.pgt.integration.yeepay.direct.service.DirectYeePay;
import com.pgt.user.bean.ResetPasswordStep;
import com.pgt.user.bean.User;
import com.pgt.user.service.imp.UserServiceImp;
import com.pgt.user.validation.group.LoginGroup;
import com.pgt.user.validation.group.RegistrationGroup;
import com.pgt.utils.CookieUtils;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.pgt.user.bean.ResetPasswordStep.CHECK_USER_EXIST;

/**
 * Created by cwang7 on 10/17/15.
 */
@RestController
@RequestMapping("/user")
public class UserController {

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
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        String code = (String) request.getSession().getAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        if(!StringUtils.isBlank(code)){
            modelAndView.addObject("code",code);
        }
        if (user != null) {
            LOGGER.debug("Redirect home page.");
            modelAndView.setViewName("redirect:" + urlConfiguration.getHomePage());
            return modelAndView;
        }
        LOGGER.debug("Go to login page with get method");
        modelAndView.addObject(Constants.USER, new User());
        return modelAndView;
    }


    @RequestMapping(value = "/mLogin", method = RequestMethod.POST)
    public Map<String, Object> mobileLogin(User user) {
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
        LOGGER.debug("The user name is {},the password is {}.", user.getUsername(), user.getPassword());
        Map<String, Object> responseMap = new HashMap<>();
        if (ObjectUtils.isEmpty(user)) {
            responseMap.put("error", "User.empty");
            return responseMap;
        }
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

        user.setPhoneNumber(user.getUsername());
        userServiceImp.saveUser(user);
        LOGGER.debug("success for register.");
        responseMap.put("success", "success");
        return responseMap;
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public ModelAndView login(@Validated(value = LoginGroup.class) User user, BindingResult bindingResult,
                              ModelAndView modelAndView, @RequestParam(value = "redirect", required = false) String redirect,
                              HttpServletRequest request, HttpServletResponse response) {
        if (user.getCount() == null) {
            user.setCount(0);
        }
        user.setCount(user.getCount() + 1);
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Has login error in result bind.");
            modelAndView.setViewName(urlConfiguration.getLoginPage());
            modelAndView.getModel().put("error", UserConstant.UNKNOWN_ERROR);
            return modelAndView;
        }
        String code = (String) request.getSession().getAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        if (!StringUtils.isBlank(code)) {
            String authCode = user.getAuthCode();
            if (!code.equalsIgnoreCase(authCode)) {
                LOGGER.debug("The auth code is not right.");
                bindingResult.addError(
                        new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.authCode", null, null)));
                modelAndView.addObject("user", user);
                modelAndView.addObject("code",code);
                return modelAndView;
            }
        }

        User userResult = userServiceImp.authorize(user.getUsername());
        if (ObjectUtils.isEmpty(userResult)) {
            modelAndView.setViewName(urlConfiguration.getLoginPage());
            bindingResult
                    .addError(new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.login", null, null)));
            return modelAndView;
        }
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + userResult.getSalt());
        if (!userResult.getPassword().equals(encryptedPassword)) {
            LOGGER.debug("The password is not right.");
            modelAndView.setViewName(urlConfiguration.getLoginPage());
            bindingResult
                    .addError(new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.login", null, null)));
            return modelAndView;
        }
        // clean important fields
        user.setPassword(null);
        user.setSalt(null);
        // check available
        if (!userResult.isAvailable()) {
            LOGGER.debug("The user is exist.");
            modelAndView.setViewName(urlConfiguration.getLoginPage());
            modelAndView.getModel().put("error", UserConstant.NOT_AVAILAVLE);
            return modelAndView;
        }
        TransactionStatus status = ensureTransaction();
        // update last login date
        userServiceImp.updateLastLogin(user.getId());
        getTransactionManager().commit(status);
        // set internal user into session
        // TODO set cookies to mark as remember me status
        request.getSession().setAttribute(UserConstant.CURRENT_USER, userResult);

        // merge and set recently browsed products
        getProductBrowseTrackService().mergeBrowsedProductsForLoginUser(request, response);

        String encode = CookieUtils.encodeBase64(userResult.getId() + "");
        Cookie cookie = new Cookie(UserConstant.CURRENT_USER, encode);
        cookie.setMaxAge(UserConstant.OVERDUE);
        response.addCookie(cookie);
        user.setCount(0);
        if (!StringUtils.isEmpty(redirect)) {
            LOGGER.debug("Need redirect to {}.", redirect);
            modelAndView.setViewName("redirect:" + redirect);
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/");

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (user != null) {
            try {
                response.sendRedirect(urlConfiguration.getHomePage());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.debug("Go to register page with get method");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName(urlConfiguration.getRegisterPage());
        return modelAndView;

    }

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public ModelAndView register(@Validated(value = RegistrationGroup.class) User user, BindingResult bindingResult,
                                 ModelAndView modelAndView, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName(urlConfiguration.getRegisterPage());
            modelAndView.getModel().put("error", UserConstant.UNKNOWN_ERROR);
            return modelAndView;
        }
        
        String code = (String) request.getSession().getAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        if (!StringUtils.isBlank(code)) {
            String authCode = user.getAuthCode();
            if (!code.equalsIgnoreCase(authCode)) {
                bindingResult.addError(
                        new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.authCode", null, null)));
                modelAndView.addObject("user", user);
                return modelAndView;
            }
        }
        if (!configuration.isSmsMock()) {

            if (!StringUtils.isBlank(user.getSmsCode())) {
                String smsCode = user.getSmsCode();
                String phoneCode = (String) request.getSession().getAttribute(Constants.REGISTER_SESSION_PHONE_CODE);
                if (!smsCode.equals(phoneCode)) {
                    bindingResult.addError(
                            new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.user.smsCode", null, null)));
                    modelAndView.addObject("user", user);
                    return modelAndView;
                }
            }
        }
        if (!user.getPassword().equals(user.getPassword2())) {
            modelAndView.setViewName(urlConfiguration.getRegisterPage());
            modelAndView.getModel().put("error", UserConstant.PASSWORD_NOT_SAME);
            return modelAndView;
        }
        boolean isExist = userServiceImp.checkExist(user.getUsername());
        if (isExist) {
            modelAndView.setViewName(urlConfiguration.getRegisterPage());
            bindingResult
                    .addError(new FieldError("user", "userExist", ErrorMsgUtil.getMsg("Error.userExist", null, null)));
            return modelAndView;
        }
        userServiceImp.saveUser(user);
        modelAndView.setViewName("redirect:" + urlConfiguration.getLoginPage());
        request.getSession().removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        request.getSession().removeAttribute(Constants.REGISTER_SESSION_PHONE_CODE);
        return modelAndView;
    }


    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ModelAndView resetPassword(HttpServletRequest request, ModelAndView modelAndView) {
        ResetPasswordStep resetPasswordStep = (ResetPasswordStep) request.getSession().getAttribute(Constants.STEP);
        if (resetPasswordStep == null) {
            resetPasswordStep = CHECK_USER_EXIST;
            request.getSession().setAttribute(Constants.STEP, resetPasswordStep);
        }
        LOGGER.debug("This is reset password step {}.", resetPasswordStep.toString());
        modelAndView.setViewName(Constants.RESET_PASSWORD);
        modelAndView.addObject(Constants.STEP, resetPasswordStep.toString());
        modelAndView.addObject(Constants.USER, new User());
        return modelAndView;
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ModelAndView resetPassword(User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request
    ) {
        ResetPasswordStep resetPasswordStep = (ResetPasswordStep) request.getSession().getAttribute(Constants.STEP);
        switch (resetPasswordStep) {
            case CHECK_USER_EXIST:
                LOGGER.debug("step one,check user exist.");
                return checkExist(user, bindingResult, modelAndView, request);
            case CHECK_PHONE_CODE:
                LOGGER.debug("step two,check phone code.");
                return checkResetPasswordPhoneCode(user, bindingResult, modelAndView, request);
            case SET_NEW_PASSWORD:
                LOGGER.debug("step three,reset password.");
                return setNewPassword(user, bindingResult, modelAndView, request);
        }
        return modelAndView;
    }

    private ModelAndView setNewPassword(User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request) {
        if (StringUtils.isBlank(user.getPassword())) {
            bindingResult.addError(
                    new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.user.passwordConfirm.invalid", null, null)));
            modelAndView.addObject("user", user);
        }
        if (user.getPassword().equals(user.getPassword2())) {
            User userResult=(User) request.getSession().getAttribute("userResult");
            userResult.setPassword(user.getPassword());
            userResult.setPassword2(user.getPassword2());
        	userServiceImp.updateUserPassword(userResult);
            request.getSession().setAttribute(Constants.STEP, ResetPasswordStep.COMPLETE);

        }
        modelAndView.setViewName(Constants.RESET_PASSWORD);
        return modelAndView;

    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public ModelAndView updatePassword(ModelAndView modelAndView,HttpSession session){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            modelAndView.setViewName("redirect:" + urlConfiguration.getLoginPage());
            return modelAndView;
        }
        modelAndView.setViewName("my-account/person-info/update-password");
        return modelAndView;
    }

    @RequestMapping(value = "/updatePasswordSubmit", method = RequestMethod.POST)
    public ModelAndView updatePasswordSubmit(User newUserPassword,BindingResult bindingResult,ModelAndView modelAndView,HttpSession session,String oldpassword){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            modelAndView.setViewName("redirect:" + urlConfiguration.getLoginPage());
            return modelAndView;
        }

        //旧密码为空
        if(ObjectUtils.isEmpty(oldpassword)){
            bindingResult.addError(
                    new FieldError("updtePassword", "updatePasswordError", ErrorMsgUtil.getMsg("NotEmpty.user.password", null, null)));
             return modelAndView;
        }

        String oldMd5Password = DigestUtils.md5Hex(oldpassword+ user.getSalt());
        //旧密码输入不正确
        if(!oldMd5Password.endsWith(user.getPassword())){
            bindingResult.addError(
                    new FieldError("updtePassword", "updatePasswordError", ErrorMsgUtil.getMsg("Error.internalUser.password.notMatch", null, null)));
            return modelAndView;
        }
        //修改密码
        if (newUserPassword.getPassword().equals(newUserPassword.getPassword2())) {
                user.setPassword(newUserPassword.getPassword());
                user.setPassword2(newUserPassword.getPassword2());
                userServiceImp.updateUserPassword(user);
        }

        modelAndView.setViewName("redirect:/user/logout");
        return modelAndView;
    }




    private ModelAndView checkResetPasswordPhoneCode(User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request) {
        if (user.getSmsCode().equalsIgnoreCase((String) request.getSession().getAttribute(Constants.RESET_PASSWOR_SESSION_PHONE_CODE))) {
            request.getSession().setAttribute(Constants.STEP, ResetPasswordStep.SET_NEW_PASSWORD);
            modelAndView.setViewName(Constants.RESET_PASSWORD);
            return modelAndView;
        }
        bindingResult.addError(
                new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.user.smsCode", null, null)));
        modelAndView.setViewName(Constants.RESET_PASSWORD);
        return modelAndView;
    }

    private ModelAndView checkExist(User user, BindingResult bindingResult, ModelAndView modelAndView, HttpServletRequest request) {
        String code = (String) request.getSession().getAttribute(Constants.RESET_PASSWORD_SESSION_SECURITY_CODE);
        if (StringUtils.isBlank(code)) {
            bindingResult.addError(
                    new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.authCode", null, null)));
            modelAndView.addObject("user", user);
        }
        String authCode = user.getAuthCode();
        if (!code.equalsIgnoreCase(authCode)) {
            bindingResult.addError(
                    new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.authCode", null, null)));
            modelAndView.addObject("user", user);
            modelAndView.setViewName(Constants.RESET_PASSWORD);
            return modelAndView;
        }
        User userResult = userServiceImp.authorize(user.getUsername());

        if (userResult != null) {
            LOGGER.debug("The user is exist.");
            request.getSession().setAttribute(Constants.STEP, ResetPasswordStep.CHECK_PHONE_CODE);
            modelAndView.setViewName(Constants.RESET_PASSWORD);
            modelAndView.addObject(Constants.USER, user);
            request.getSession().setAttribute("userResult",userResult);
            return modelAndView;
        }
        LOGGER.debug("The user is not exist.");
        modelAndView.setViewName(Constants.RESET_PASSWORD);
        bindingResult.addError(
                new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.authCode", null, null)));
        return modelAndView;
    }


    /**
     * 用户登出
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, ModelAndView modelAndView) {
        session.removeAttribute(UserConstant.CURRENT_USER);
        session.removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        session.removeAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        session.removeAttribute(CartConstant.CURRENT_ORDER);
        modelAndView.setViewName("redirect:" + urlConfiguration.getLoginPage());
        return modelAndView;
    }

    @RequestMapping(value = "/yeepayAccountInfo", method = RequestMethod.GET)
    public ModelAndView yeepayAccountInfo(HttpServletRequest request, HttpServletResponse response) {

        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (null == user) {
            ModelAndView mav = new ModelAndView("redirect:" + urlConfiguration.getLoginPage());
            return mav;
        }
        // reload user
        user = userServiceImp.authorize(user.getUsername());
        if (YeePayConstants.REGISTOR_STATUS_SUCCESS == user.getYeepayStatus()) {
        	Map<String, Object> params = new HashMap();
            params.put(YeePayConstants.PARAM_NAME_USER_ID, user.getId());
            params.put(YeePayConstants.PARAM_NAME_PLATFORM_USER_NO,
                    YeePayHelper.generateOutboundUserNo(getAccountInfoYeepay().getConfig(), user.getId()));
            try {
                Map<String, String> result = getAccountInfoYeepay().invok(params);
                ModelAndView mav = new ModelAndView("/my-account/yeepay/accountInfo");
                String accountNo = YeePayHelper.generateOutboundUserNo(getAccountInfoYeepay().getConfig(), user.getId());
                mav.addObject("accountNo", accountNo);
                mav.addObject("idNo", user.getYeepayUserId());
                mav.addObject("name", user.getYeepayUserName());
                mav.addObject("result", result);
                return mav;
            } catch (IOException e) {
                ModelAndView mav = new ModelAndView("/my-account/yeepay/accountInfoError");
                return mav;
            }
        } else {
        	 ModelAndView mav = new ModelAndView("/my-account/yeepay/accountInfoUnregistor");
             return mav;
        }
        
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
