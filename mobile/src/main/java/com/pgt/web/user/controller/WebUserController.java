package com.pgt.web.user.controller;

import com.pgt.cart.constant.CartConstant;
import com.pgt.cart.service.ProductBrowseTrackService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.constant.Constants;
import com.pgt.constant.UserConstant;
import com.pgt.mail.service.MailService;
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
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * Created by hd on 16-1-16.
 */
@Controller
@RequestMapping("/web")
public class WebUserController {

    @Resource
    private UserServiceImp userServiceImp;

    @Autowired
    private URLConfiguration urlConfiguration;

    @Resource(name = "productBrowseTrackService")
    private ProductBrowseTrackService mProductBrowseTrackService;



    @Autowired
    private SimpleCacheManager cacheManager;

    @Autowired
    private MailService mailService;

    @Autowired
    private Configuration configuration;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebUserController.class);



    @RequestMapping(value = "/wlogin", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView modelAndView, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (user != null) {
            LOGGER.debug("Redirect home page.");
            modelAndView.setViewName("redirect:/web/personalInformation");
           return modelAndView;
    }
        LOGGER.debug("Go to login page with get method");
        modelAndView.addObject(Constants.USER, new User());
        modelAndView.setViewName("user/login");
        return modelAndView;
    }


    @RequestMapping(value = "/wlogin", method = {RequestMethod.POST})
    public ModelAndView login(@Validated(value = LoginGroup.class) User user, BindingResult bindingResult,
                              ModelAndView modelAndView, @RequestParam(value = "redirect", required = false) String redirect,
                              HttpServletRequest request, HttpServletResponse response) {
        if (user.getCount() == null) {
            user.setCount(0);
        }
        user.setCount(user.getCount() + 1);
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Has login error in result bind.");
            modelAndView.setViewName("user/login");
            return modelAndView;
        }


        User userResult = userServiceImp.authorize(user.getUsername());
        if (ObjectUtils.isEmpty(userResult)) {
            modelAndView.setViewName("user/login");
            bindingResult
                    .addError(new FieldError("user", "loginError", ErrorMsgUtil.getMsg("Error.login", null, null)));
            return modelAndView;
        }
        String encryptedPassword = DigestUtils.md5Hex(user.getPassword() + userResult.getSalt());
        if (!userResult.getPassword().equals(encryptedPassword)) {
            LOGGER.debug("The password is not right.");
            modelAndView.setViewName("user/login");
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
            modelAndView.setViewName("user/login");
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
        modelAndView.setViewName("my-account/personalinformation");
        modelAndView.addObject("redirect","/");
        if (!StringUtils.isEmpty(redirect)) {
            LOGGER.debug("Need redirect to {}.", redirect);
            modelAndView.addObject("redirect",redirect);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/wlogout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session, ModelAndView modelAndView) {
        session.removeAttribute(UserConstant.CURRENT_USER);
        session.removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        session.removeAttribute(Constants.LOGIN_SESSION_SECURITY_CODE);
        session.removeAttribute(CartConstant.CURRENT_ORDER);
        modelAndView.setViewName("redirect:/web/wlogin");
        return modelAndView;
    }



    @RequestMapping(value = "/wregister", method = RequestMethod.GET)
    public ModelAndView register(ModelAndView modelAndView, HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(UserConstant.CURRENT_USER);
        if (user != null) {
            try {
                response.sendRedirect("my-account/personalinformation");
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.debug("Go to register page with get method");
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("user/signup");
        return modelAndView;

    }

    @RequestMapping(value = "/wregister", method = {RequestMethod.POST})
    public ModelAndView register(@Validated(value = RegistrationGroup.class) User user, BindingResult bindingResult,
                                 ModelAndView modelAndView, HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("user/signup");
            modelAndView.getModel().put("error", UserConstant.UNKNOWN_ERROR);
            return modelAndView;
        }

        if (!configuration.isSmsMock()) {

            if (!StringUtils.isBlank(user.getSmsCode())) {
                String smsCode = user.getSmsCode();
                String phoneCode = (String) request.getSession().getAttribute(Constants.REGISTER_SESSION_PHONE_CODE);
                if (!smsCode.equals(phoneCode)) {
                    bindingResult.addError(
                            new FieldError("user", "smsCode", ErrorMsgUtil.getMsg("Error.user.smsCode", null, null)));
                    modelAndView.addObject("user", user);
                    return modelAndView;
                }
            }
        }
        if (!user.getPassword().equals(user.getPassword2())) {
            modelAndView.setViewName("user/signup");
            modelAndView.getModel().put("error", UserConstant.PASSWORD_NOT_SAME);
            return modelAndView;
        }
        boolean isExist = userServiceImp.checkExist(user.getUsername());
        if (isExist) {
            modelAndView.setViewName("user/signup");
            bindingResult
                    .addError(new FieldError("user", "userExist", ErrorMsgUtil.getMsg("Error.userExist", null, null)));
            return modelAndView;
        }
        userServiceImp.saveUser(user);
        request.getSession().removeAttribute(Constants.REGISTER_SESSION_SECURITY_CODE);
        request.getSession().removeAttribute(Constants.REGISTER_SESSION_PHONE_CODE);
        modelAndView.setViewName("user/login");
        return modelAndView;
    }






    @RequestMapping(value = "/wupdatePassword", method = RequestMethod.GET)
    public ModelAndView updatePassword(ModelAndView modelAndView,HttpSession session){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            modelAndView.setViewName("redirect:" + "/web/wlogin");
            return modelAndView;
        }
        modelAndView.setViewName("my-account/person-info/modifypassword");
        return modelAndView;
    }

    @RequestMapping(value = "/wupdatePasswordSubmit", method = RequestMethod.POST)
    public ModelAndView updatePasswordSubmit(User newUserPassword,BindingResult bindingResult,ModelAndView modelAndView,HttpSession session,String oldpassword){

        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if (user == null) {
            modelAndView.setViewName("redirect:" + "/web/wlogin");
            return modelAndView;
        }

        //旧密码为空
        if(ObjectUtils.isEmpty(oldpassword)){
            bindingResult.addError(
                    new FieldError("updtePassword", "updatePasswordError", ErrorMsgUtil.getMsg("NotEmpty.user.password", null, null)));
            modelAndView.setViewName("my-account/person-info/modifypassword");
            return modelAndView;
        }

        String oldMd5Password = DigestUtils.md5Hex(oldpassword + user.getSalt());
        //旧密码输入不正确
        if(!oldMd5Password.endsWith(user.getPassword())){
            bindingResult.addError(
                    new FieldError("updtePassword", "updatePasswordError", ErrorMsgUtil.getMsg("Error.internalUser.password.notMatch", null, null)));
            modelAndView.setViewName("my-account/person-info/modifypassword");
            return modelAndView;
        }
        //修改密码
        if (newUserPassword.getPassword().equals(newUserPassword.getPassword2())) {
            user.setPassword(newUserPassword.getPassword());
            user.setPassword2(newUserPassword.getPassword2());
            userServiceImp.updateUserPassword(user);
        }

        modelAndView.setViewName("redirect:/web/wlogout");
        return modelAndView;
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


    public MailService getMailService() {
        return mailService;
    }


    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

}
