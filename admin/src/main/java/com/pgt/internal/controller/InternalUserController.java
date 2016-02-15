package com.pgt.internal.controller;

import com.pgt.cart.bean.ResponseBean;
import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.constant.CookieConstant;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.bean.InternalUserBuilder;
import com.pgt.internal.bean.InternalUserInvestType;
import com.pgt.internal.bean.Role;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.service.InternalUserService;
import com.pgt.internal.service.InternalUserValidationService;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.service.PawnService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Yove on 10/20/2015.
 */
@RestController
@RequestMapping
public class InternalUserController extends InternalTransactionBaseController implements InternalUserMessages, InternalUserProperties {

    private static final Logger LOGGER = LoggerFactory.getLogger(InternalUserController.class);

    @Resource(name = "internalUserService")
    private InternalUserService mInternalUserService;

    @Resource(name = "internalUserValidationService")
    private InternalUserValidationService mInternalUserValidationService;

	@Resource(name = "pawnService")
	private PawnService mPawnService;

    @Resource(name = "responseBuilderFactory")
    private ResponseBuilderFactory mResponseBuilderFactory;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        ModelAndView mav = new ModelAndView("/login");
        InternalUser iu = (InternalUser) pRequest.getSession().getAttribute(INTERNAL_USER);
        if (iu != null && RepositoryUtils.idIsValid(iu.getId()) && iu.isAvailable()) {
            // internal user has already login
            LOGGER.debug("Internal user: {} already stay in login state, no need to login again", iu.getLogin());
            mav.setViewName(REDIRECT_DASHBOARD);
            return mav;
        }
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        pRequest.getSession().invalidate();
        Cookie cookie = new Cookie(CookieConstant.INTERNAL_LOGIN_TOKEN, StringUtils.EMPTY);
        cookie.setMaxAge(0);
        pResponse.addCookie(cookie);
        return new ModelAndView("/login");
    }

    @RequestMapping(value = "/internal/register", method = RequestMethod.GET)
    public ModelAndView signUp(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("/internal/register");
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        return mav;
    }

    @RequestMapping(value = "/internal/iu-list")//, method = RequestMethod.GET)
    public ModelAndView listInternalUser(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                         @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
                                         @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
                                         @RequestParam(value = "keyword", required = false) String keyword) {
        // verify permission
        if (!verifyPermission(pRequest)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
        long caLong = RepositoryUtils.safeParse2LongId(capacity);
        LOGGER.debug("Query internal users with index: {}, capacity: {} and keyword: {}", ciLong, caLong, keyword);
        InternalPaginationBuilder ipb = new InternalPaginationBuilder();
        InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword).createInternalPagination();
        getInternalUserService().queryInternalUserPage(pagination);
        ModelAndView mav = new ModelAndView("/internal/iu-list");
        mav.addObject(ResponseConstant.INTERNAL_USER_PAGE, pagination);
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        return mav;
    }


    @RequestMapping(value = "/internal/iu-modify", method = RequestMethod.GET)
    public ModelAndView internalUserModify(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                           @RequestParam(value = "uid", required = true) String uid) {
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("/internal/iu-modify");
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        LOGGER.debug("Load internal user with id: {} to modify", uid);
        int idInt = RepositoryUtils.safeParseId(uid);
        if (idInt > 0) {
            InternalUser piu = getInternalUserService().findInternalUser(idInt);
            if (piu == null) {
                LOGGER.debug("Cannot find internal user with id: {}", uid);
                return mav;
            }
            mav.addObject(ResponseConstant.INTERNAL_USER, piu);
            LOGGER.debug("Found internal user: {}", piu);
        }
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView authorize(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                  @RequestParam(value = "login", required = true) String login,
                                  @RequestParam(value = "password", required = true) String password,
                                  @RequestParam(value = "remember", required = false, defaultValue = "false") boolean remember) {
        // trim login
        login = login.trim();
        ModelAndView mav = new ModelAndView("/login");
        // check internal user exist
        InternalUser iu = new InternalUserBuilder().setLogin(login).setPassword(password).createInternalUser();
        TransactionStatus status = ensureTransaction();
        try {
            InternalUser piu = getInternalUserService().findInternalUser(login);
            if (piu == null) {
                LOGGER.debug("Cannot find internal user by login: {}", login);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_LOGIN_NOT_EXIST));
                return mav;
            }
            // check password
            String encryptedPassword = DigestUtils.md5Hex(password + piu.getSalt());
            LOGGER.debug("Login internal user: {} by password: {} with salt: {}", login, password, piu.getSalt());
            if (!piu.getPassword().equals(encryptedPassword)) {
                LOGGER.debug("Login internal user: {} by encrypted password: {} doesn't match persist password", login, encryptedPassword);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PASSWORD_NOT_MATCH));
                return mav;
            }
            // clean important fields
            piu.setPassword(null);
            piu.setSalt(null);
            LOGGER.debug("Login internal user: {} with correct password", login);
            // check available
            if (!piu.isAvailable()) {
                LOGGER.debug("Login internal user: {}, but user was set as forbidden. ", login);
                iu.setAvailable(false);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(WARN_USER_STATUS_INVALID));
                return mav;
            }
            LOGGER.debug("Login internal user: {} success", login);
            // update last login date
            piu.setLastLoginDate(new Date());
            piu.setIp(captureIpAddress(pRequest));
            getInternalUserService().updateLastLogin(piu.getId(), piu.getIp());
            // set internal user into session
            pRequest.getSession().setAttribute(INTERNAL_USER, piu);
            if (remember) {
                String encodeInfo = getInternalUserService().encodeRememberInfo(piu.getId());
                Cookie cookie = new Cookie(CookieConstant.INTERNAL_LOGIN_TOKEN, encodeInfo);
                cookie.setMaxAge(getInternalUserService().getRememberExpiration());
                pResponse.addCookie(cookie);
            }
	        LOGGER.debug("The user role is {}.", piu.getRole());
	        mav.setViewName(REDIRECT_DASHBOARD);
            return mav;
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Login internal user: {} occurs exception: ", login, e);
        } finally {
            getTransactionManager().commit(status);
        }
        mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_GENERAL_LOGIN_FAILED));
        return mav;
    }

    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity ajaxAuthorize(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                        @RequestParam(value = "login", required = true) String login,
                                        @RequestParam(value = "password", required = true) String password) {
        // trim login
        login = login.trim();
        // check internal user exist
        InternalUser iu = new InternalUserBuilder().setLogin(login).setPassword(password).createInternalUser();
        // set default success as false
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        TransactionStatus status = ensureTransaction();
        try {
            InternalUser piu = getInternalUserService().findInternalUser(login);
            if (piu == null) {
                LOGGER.debug("Cannot find internal user by login: {}", login);
                rb.addErrorMessage(PROP_LOGIN, ERROR_LOGIN_NOT_EXIST);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            // check password
            String encryptedPassword = DigestUtils.md5Hex(password + piu.getSalt());
            LOGGER.debug("Login internal user: {} by password: {} with salt: {}", login, password, piu.getSalt());
            if (!piu.getPassword().equals(encryptedPassword)) {
                LOGGER.debug("Login internal user: {} by encrypted password: {} doesn't match persist password", login, encryptedPassword);
                rb.addErrorMessage(PROP_PASSWORD, ERROR_PASSWORD_NOT_MATCH);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            // clean important fields
            piu.setPassword(null);
            piu.setSalt(null);
            LOGGER.debug("Login internal user: {} with correct password", login);
            // check available
            if (!piu.isAvailable()) {
                LOGGER.debug("Login internal user: {}, but user was set as forbidden. ", login);
                iu.setAvailable(false);
                rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, WARN_USER_STATUS_INVALID);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            LOGGER.debug("Login internal user: {} success", login);
            // update last login date
            piu.setLastLoginDate(new Date());
            piu.setIp(captureIpAddress(pRequest));
            getInternalUserService().updateLastLogin(piu.getId(), piu.getIp());
            // set internal user into session
            pRequest.getSession().setAttribute(INTERNAL_USER, piu);
            //TODO set cookies to mark as remember me status
            rb.setSuccess(true).setData(piu);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Login internal user: {} occurs exception: ", login, e);
        } finally {
            getTransactionManager().commit(status);
        }
        rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_GENERAL_LOGIN_FAILED);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/internal/register", method = RequestMethod.POST)
    public ModelAndView register(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                 @RequestParam(value = "login", required = true) String login,
                                 @RequestParam(value = "password", required = true) String password,
                                 @RequestParam(value = "passwordConfirm", required = true) String passwordConfirm,
                                 @RequestParam(value = "role", required = true) Role role,
                                 @RequestParam(value = "investType", required = false) InternalUserInvestType investType,
                                 String name, String phone, String email, String available) {
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        InternalUserBuilder iub = new InternalUserBuilder().setLogin(login);
        iub.setName(name).setPhone(phone).setEmail(email).setAvailable(available);
        ModelAndView mav = new ModelAndView("/internal/register");
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        // set default success as false
        // check fields
        if (!getInternalUserValidationService().getLoginRegexValidator().match(login)) {
            LOGGER.debug("Register internal user with invalid login: {}", login);
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_LOGIN_INVALID));
            return mav;
        }
        if (!getInternalUserValidationService().getPasswordRegexValidator().match(password)) {
            LOGGER.debug("Register internal user with login: {} but password: {} is invalid", login, password);
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PASSWORD_INVALID));
            return mav;
        }
        if (!StringUtils.equals(password, passwordConfirm)) {
            LOGGER.debug("Register internal user with login: {} but password: {} dose not equals confirmed password: {}", login,
                    password, passwordConfirm);
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PASSWORD_CONFIRM_INVALID));
            return mav;
        }
        if (!getInternalUserValidationService().checkLoginUniqueness(login, null)) {
            LOGGER.debug("Register internal user with duplicate login: {}", login);
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_LOGIN_DUPLICATE));
            return mav;
        }
        if (StringUtils.isNotBlank(name)) {
            if (!getInternalUserValidationService().checkNameUniqueness(name, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate name: {}", login, name);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_NAME_DUPLICATE));
                return mav;
            }
        }
        if (StringUtils.isNotBlank(email)) {
            if (!getInternalUserValidationService().getEmailRegexValidator().match(email)) {
                LOGGER.debug("Register internal user with login: {} but invalid email: {}", login, email);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_EMAIL_INVALID));
                return mav;
            }
            if (!getInternalUserValidationService().checkEmailUniqueness(email, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate email: {}", login, email);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_EMAIL_DUPLICATE));
                return mav;
            }
        }
        if (StringUtils.isNotBlank(phone)) {
            if (!getInternalUserValidationService().getPhoneRegexValidator().match(phone)) {
                LOGGER.debug("Register internal user with login: {} but invalid phone: {}", login, phone);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PHONE_INVALID));
                return mav;
            }
            if (!getInternalUserValidationService().checkPhoneUniqueness(phone, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate phone: {}", login, phone);
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PHONE_DUPLICATE));
                return mav;
            }
        }

        TransactionStatus status = ensureTransaction();
        try {
            LOGGER.debug("Register internal user with login: {} passed fields validation", login);
            // generate salt and encrypted password
            String salt = String.valueOf(System.currentTimeMillis());
            String encryptedPassword = DigestUtils.md5Hex(password + salt);
            String ipAddress = captureIpAddress(pRequest);
            iub.setSalt(salt).setPassword(encryptedPassword).setIp(ipAddress).setRole(role).setInvestType(investType);
            if (role.equals(Role.ADMINISTRATOR) || role.equals(Role.PROD_ORDER_MANAGER)) {
                iub.setInvestType(InternalUserInvestType.NONEED);
            }
            InternalUser iu = iub.createInternalUser();
            LOGGER.debug("Create internal user: {}", iu);
            boolean result = getInternalUserService().createInternalUser(iu);
            LOGGER.debug("Create internal user with login: {} has result: {}", login, result ? "success" : "failed");
            if (result) {
                // set internal user into session to auto login
                pRequest.getSession().setAttribute(INTERNAL_USER, iu);
                mav.setViewName(REDIRECT_DASHBOARD);
                return mav;
            }
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Register internal user: {} occurs exception: ", login, e);
        } finally {
            getTransactionManager().commit(status);
        }
        mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_GENERAL_REGISTER_FAILED));
        return mav;
    }

    @RequestMapping(value = "/internal/ajaxRegister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity ajaxRegister(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                       @RequestParam(value = "login", required = true) String login,
                                       @RequestParam(value = "password", required = true) String password,
                                       @RequestParam(value = "passwordConfirm", required = true) String passwordConfirm,
                                       @RequestParam(value = "role", required = true) Role role,
                                       @RequestParam(value = "investType", required = false) InternalUserInvestType investType,
                                       String name, String phone, String email, String available) {
        // set default success as false
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
        }
        // main logic
        InternalUserBuilder iub = new InternalUserBuilder().setLogin(login);
        iub.setName(name).setPhone(phone).setEmail(email).setAvailable(available);
        // check fields
        if (!getInternalUserValidationService().getLoginRegexValidator().match(login)) {
            LOGGER.debug("Register internal user with invalid login: {}", login);
            rb.addErrorMessage(PROP_LOGIN, ERROR_LOGIN_INVALID);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        if (!getInternalUserValidationService().getPasswordRegexValidator().match(password)) {
            LOGGER.debug("Register internal user with login: {} but password: {} is invalid", login, password);
            rb.addErrorMessage(PROP_PASSWORD, ERROR_PASSWORD_INVALID);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        if (!StringUtils.equals(password, passwordConfirm)) {
            LOGGER.debug("Register internal user with login: {} but password: {} dose not equals confirmed password: {}", login,
                    password, passwordConfirm);
            rb.addErrorMessage(PROP_PASSWORD_CONFIRM, ERROR_PASSWORD_CONFIRM_INVALID);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        if (!getInternalUserValidationService().checkLoginUniqueness(login, null)) {
            LOGGER.debug("Register internal user with duplicate login: {}", login);
            rb.addErrorMessage(PROP_LOGIN, ERROR_LOGIN_DUPLICATE);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        if (StringUtils.isNotBlank(name)) {
            if (!getInternalUserValidationService().checkNameUniqueness(name, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate name: {}", login, name);
                rb.addErrorMessage(PROP_NAME, ERROR_NAME_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }
        if (StringUtils.isNotBlank(email)) {
            if (!getInternalUserValidationService().getEmailRegexValidator().match(email)) {
                LOGGER.debug("Register internal user with login: {} but invalid email: {}", login, email);
                rb.addErrorMessage(PROP_EMAIL, ERROR_EMAIL_INVALID);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            if (!getInternalUserValidationService().checkEmailUniqueness(email, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate email: {}", login, email);
                rb.addErrorMessage(PROP_EMAIL, ERROR_EMAIL_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }
        if (StringUtils.isNotBlank(phone)) {
            if (!getInternalUserValidationService().getPhoneRegexValidator().match(phone)) {
                LOGGER.debug("Register internal user with login: {} but invalid phone: {}", login, phone);
                rb.addErrorMessage(PROP_PHONE, ERROR_PHONE_INVALID);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            if (!getInternalUserValidationService().checkPhoneUniqueness(phone, null)) {
                LOGGER.debug("Register internal user with login: {} but duplicate phone: {}", login, phone);
                rb.addErrorMessage(PROP_PHONE, ERROR_PHONE_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }

        TransactionStatus status = ensureTransaction();
        try {
            LOGGER.debug("Register internal user with login: {} passed fields validation", login);
            // generate salt and encrypted password
            String salt = String.valueOf(System.currentTimeMillis());
            String encryptedPassword = DigestUtils.md5Hex(password + salt);
            String ipAddress = captureIpAddress(pRequest);
            iub.setSalt(salt).setPassword(encryptedPassword).setIp(ipAddress).setRole(role).setInvestType(investType);
            if (role.equals(Role.ADMINISTRATOR) || role.equals(Role.PROD_ORDER_MANAGER)) {
                iub.setInvestType(InternalUserInvestType.NONEED);
            }
            InternalUser iu = iub.createInternalUser();
            LOGGER.debug("Create internal user: {}", iu);
            boolean result = getInternalUserService().createInternalUser(iu);
            LOGGER.debug("Create internal user with login: {} has result: {}", login, result ? "success" : "failed");
            if (result) {
                // set internal user into session to auto login
                pRequest.getSession().setAttribute(INTERNAL_USER, iu);
                rb.setSuccess(true).setData(iu);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Register internal user: {} occurs exception: ", login, e);
        } finally {
            getTransactionManager().commit(status);
        }
        rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_GENERAL_REGISTER_FAILED);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/internal/iu-list-data")//, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity listInternalUserData(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                               @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
                                               @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
                                               @RequestParam(value = "keyword", required = false) String keyword) {
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        // verify permission
        if (!verifyPermission(pRequest)) {
            return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
        }
        // main logic
        long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
        long caLong = RepositoryUtils.safeParse2LongId(capacity);
        LOGGER.debug("Query internal users with index: {}, capacity: {} and keyword: {}", ciLong, caLong, keyword);
        InternalPaginationBuilder ipb = new InternalPaginationBuilder();
        InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword).createInternalPagination();
        getInternalUserService().queryInternalUserPage(pagination);
        rb = getResponseBuilderFactory().buildResponseBean().setSuccess(true).setData(pagination);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/internal/iu-batch-available-update")//, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity updateBatchInternalUserAvailable(HttpServletRequest pRequest, HttpServletResponse pResponse,
                                                           @RequestParam(value = "ids", required = true) int[] ids,
                                                           @RequestParam(value = "available", required = true) boolean available) {
        // set default success as false
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
        }
        // main logic
        if (ArrayUtils.isEmpty(ids)) {
            LOGGER.debug("No internal user need to update available state.");
            rb.addErrorMessage(PROP_USER_IDS, WARN_USER_IDS_EMPTY);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        TransactionStatus status = ensureTransaction();
        try {
            boolean result = getInternalUserService().updateBatchInternalUserAvailable(ids, available);
            LOGGER.debug("Update batch internal users: {} to available state: {} ", Arrays.toString(ids), result ? "success" : "failed");
            if (result) {
                rb.setSuccess(true);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            status.setRollbackOnly();
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Update batch internal users: {} to available state: {} failed", Arrays.toString(ids), available, e);
        } finally {
            getTransactionManager().commit(status);
        }
        rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_GENERAL_UPDATE_BATCH_AVAILABLE_FAILED);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/internal/iu-modify", method = RequestMethod.POST)
    public ModelAndView internalUserUpdate(HttpServletRequest pRequest, HttpServletResponse pResponse, InternalUser internalUser,
                                           @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm) {
        // verify permission
        if (!verifyPermission(pRequest, Role.ADMINISTRATOR)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        ModelAndView mav = new ModelAndView("/internal/iu-modify");
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        mav.addObject(ResponseConstant.INTERNAL_USER, internalUser);
        if (internalUser == null || !RepositoryUtils.idIsValid(internalUser.getId())) {
            LOGGER.debug("Cannot update internal user information for invalid id");
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_USER_ID_INVALID));
            return mav;
        }
        // trim fields
        internalUser.trimFields();
        // password is empty mean do not update password
        if (StringUtils.isNotBlank(internalUser.getPassword())) {
            if (!getInternalUserValidationService().getPasswordRegexValidator().match(internalUser.getPassword())) {
                LOGGER.debug("Update internal user with id: {} but password: {} is invalid", internalUser.getId(), internalUser.getPassword());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PASSWORD_INVALID));
                return mav;
            }
        }
        // password confirmation should be empty too
        if (!StringUtils.equals(internalUser.getPassword(), passwordConfirm)) {
            LOGGER.debug("Update internal user with id: {} but password: {} dose not equals confirmed password: {}", internalUser.getId(),
                    internalUser.getPassword(), passwordConfirm);
            mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PASSWORD_CONFIRM_INVALID));
            return mav;
        }
        // allow multiple empty name internal user
        if (StringUtils.isNotBlank(internalUser.getName())) {
            if (!getInternalUserValidationService().checkNameUniqueness(internalUser.getName(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate name: {}", internalUser.getId(), internalUser.getName());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_NAME_DUPLICATE));
                return mav;
            }
        }
        // allow multiple email name internal user
        if (StringUtils.isNotBlank(internalUser.getEmail())) {
            if (!getInternalUserValidationService().getEmailRegexValidator().match(internalUser.getEmail())) {
                LOGGER.debug("Update internal user with id: {} but invalid email: {}", internalUser.getId(), internalUser.getEmail());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_EMAIL_INVALID));
                return mav;
            }
            if (!getInternalUserValidationService().checkEmailUniqueness(internalUser.getEmail(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate email: {}", internalUser.getId(), internalUser.getEmail());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_EMAIL_DUPLICATE));
                return mav;
            }
        }
        if (StringUtils.isNotBlank(internalUser.getPhone())) {
            if (!getInternalUserValidationService().getPhoneRegexValidator().match(internalUser.getPhone())) {
                LOGGER.debug("Update internal user with id: {} but invalid phone: {}", internalUser.getId(), internalUser.getPhone());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PHONE_INVALID));
                return mav;
            }
            if (!getInternalUserValidationService().checkPhoneUniqueness(internalUser.getPhone(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate phone: {}", internalUser.getId(), internalUser.getPhone());
                mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_PHONE_DUPLICATE));
                return mav;
            }
        }

        TransactionStatus status = ensureTransaction();
        try {
            if (StringUtils.isNotBlank(internalUser.getPassword())) {
                // re-generate salt and encrypted password
                String salt = String.valueOf(System.currentTimeMillis());
                String encryptedPassword = DigestUtils.md5Hex(internalUser.getPassword() + salt);
                internalUser.setPassword(encryptedPassword);
                internalUser.setSalt(salt);
            }
            boolean result = getInternalUserService().updateInternalUser(internalUser);
            LOGGER.debug("Update internal user with id: {} has result: {}", internalUser.getId(), result ? "success" : "failed");
            if (result) {
                return mav;
            }
            status.setRollbackOnly();
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Update internal user with id: {} occurs exception: ", internalUser.getId(), e);
        } finally {
            getTransactionManager().commit(status);
        }
        mav.addObject(ResponseConstant.ERROR_MSG, getMessageValue(ERROR_GENERAL_UPDATE_USER_FAILED));
        return mav;
    }

    @RequestMapping(value = "/internal/ajax-iu-modify", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity ajaxInternalUserUpdate(HttpServletRequest pRequest, HttpServletResponse pResponse, InternalUser internalUser,
                                                 @RequestParam(value = "passwordConfirm", required = false) String passwordConfirm) {
        // set default success as false
        ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
        // verify permission
        if (!verifyPermission(pRequest)) {
            return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
        }
        // main logic
        if (internalUser == null || !RepositoryUtils.idIsValid(internalUser.getId())) {
            LOGGER.debug("Cannot update internal user information for invalid id");
            rb.addErrorMessage(PROP_USER_ID, ERROR_USER_ID_INVALID);
            new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        // trim fields
        internalUser.trimFields();
        // password is empty mean do not update password
        if (StringUtils.isNotBlank(internalUser.getPassword())) {
            if (!getInternalUserValidationService().getPasswordRegexValidator().match(internalUser.getPassword())) {
                LOGGER.debug("Update internal user with id: {} but password: {} is invalid", internalUser.getId(), internalUser.getPassword());
                rb.addErrorMessage(PROP_PASSWORD, ERROR_PASSWORD_INVALID);
                return new ResponseEntity(internalUser, HttpStatus.OK);
            }
        }
        // password confirmation should be empty too
        if (!StringUtils.equals(internalUser.getPassword(), passwordConfirm)) {
            LOGGER.debug("Update internal user with id: {} but password: {} dose not equals confirmed password: {}", internalUser.getId(),
                    internalUser.getPassword(), passwordConfirm);
            rb.addErrorMessage(PROP_PASSWORD_CONFIRM, ERROR_PASSWORD_CONFIRM_INVALID);
            return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
        }
        // allow multiple empty name internal user
        if (StringUtils.isNotBlank(internalUser.getName())) {
            if (!getInternalUserValidationService().checkNameUniqueness(internalUser.getName(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate name: {}", internalUser.getId(), internalUser.getName());
                rb.addErrorMessage(PROP_NAME, ERROR_NAME_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }
        // allow multiple email name internal user
        if (StringUtils.isNotBlank(internalUser.getEmail())) {
            if (!getInternalUserValidationService().getEmailRegexValidator().match(internalUser.getEmail())) {
                LOGGER.debug("Update internal user with id: {} but invalid email: {}", internalUser.getId(), internalUser.getEmail());
                rb.addErrorMessage(PROP_EMAIL, ERROR_EMAIL_INVALID);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            if (!getInternalUserValidationService().checkEmailUniqueness(internalUser.getEmail(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate email: {}", internalUser.getId(), internalUser.getEmail());
                rb.addErrorMessage(PROP_EMAIL, ERROR_EMAIL_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }
        if (StringUtils.isNotBlank(internalUser.getPhone())) {
            if (!getInternalUserValidationService().getPhoneRegexValidator().match(internalUser.getPhone())) {
                LOGGER.debug("Update internal user with id: {} but invalid phone: {}", internalUser.getId(), internalUser.getPhone());
                rb.addErrorMessage(PROP_PHONE, ERROR_PHONE_INVALID);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            if (!getInternalUserValidationService().checkPhoneUniqueness(internalUser.getPhone(), internalUser.getId())) {
                LOGGER.debug("Update internal user with id: {} but duplicate phone: {}", internalUser.getId(), internalUser.getPhone());
                rb.addErrorMessage(PROP_PHONE, ERROR_PHONE_DUPLICATE);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
        }

        TransactionStatus status = ensureTransaction();
        try {
            if (StringUtils.isNotBlank(internalUser.getPassword())) {
                // re-generate salt and encrypted password
                String salt = String.valueOf(System.currentTimeMillis());
                String encryptedPassword = DigestUtils.md5Hex(internalUser.getPassword() + salt);
                internalUser.setPassword(encryptedPassword);
                internalUser.setSalt(salt);
            }
            boolean result = getInternalUserService().updateInternalUser(internalUser);
            LOGGER.debug("Update internal user with id: {} has result: {}", internalUser.getId(), result ? "success" : "failed");
            if (result) {
                rb.setSuccess(true);
                return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
            }
            status.setRollbackOnly();
        } catch (Exception e) {
            status.setRollbackOnly();
            LOGGER.error("Update internal user with id: {} occurs exception: ", internalUser.getId(), e);
        } finally {
            getTransactionManager().commit(status);
        }
        rb.addErrorMessage(ResponseBean.DEFAULT_PROPERTY, ERROR_GENERAL_UPDATE_USER_FAILED);
        return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
    }

    @RequestMapping(value = "/internal/mine", method = RequestMethod.GET)
    public ModelAndView internalUserOfMineRead(HttpServletRequest pRequest, HttpServletResponse pResponse) {
        // verify permission
        if (!verifyPermission(pRequest)) {
            return new ModelAndView(PERMISSION_DENIED);
        }
        // main logic
        InternalUser iu = getCurrentInternalUser(pRequest);
        if (ObjectUtils.isEmpty(iu)) {
            LOGGER.debug("Current user is empty.");
            ModelAndView mav = new ModelAndView("forward:/internal/iu-modify?uid=" + iu.getId());
            return mav;
        }
        LOGGER.debug("Load internal user with id: {} to display", iu.getId());
        if (getRolePermissionService().validAdministratorRole(iu.getRole())) {
            LOGGER.debug("Current internal user has Administrator role, so he could update his information.");
            ModelAndView mav = new ModelAndView("forward:/internal/iu-modify?uid=" + iu.getId());
            return mav;
        }

	    ModelAndView mav = new ModelAndView("/internal/iu-mine");
	    // current internal user is an p2p user
	    if (iu.getRole() == Role.INVESTOR || iu.getRole() == Role.IVST_ORDER_MANAGER) {
		    List<Pawnshop> pawnshops = getPawnService().queryPawnShopsForInternalUser(iu.getId());
		    mav.addObject(ResponseConstant.PAWN_SHOPS, pawnshops);
	    }
        mav.addObject(ResponseConstant.ROLES, Role.getRoleNameMap());
        return mav;
    }

    protected String captureIpAddress(HttpServletRequest pRequest) {
        String ip = pRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = pRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = pRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = pRequest.getRemoteAddr();
        }
        return ip;
    }

    public InternalUserService getInternalUserService() {
        return mInternalUserService;
    }

    public void setInternalUserService(final InternalUserService pInternalUserService) {
        mInternalUserService = pInternalUserService;
    }

    public InternalUserValidationService getInternalUserValidationService() {
        return mInternalUserValidationService;
    }

    public void setInternalUserValidationService(final InternalUserValidationService pInternalUserValidationService) {
        mInternalUserValidationService = pInternalUserValidationService;
    }

	public PawnService getPawnService() {
		return mPawnService;
	}

	public void setPawnService(final PawnService pPawnService) {
		mPawnService = pPawnService;
	}

	public ResponseBuilderFactory getResponseBuilderFactory() {
		return mResponseBuilderFactory;
    }

    public void setResponseBuilderFactory(final ResponseBuilderFactory pResponseBuilderFactory) {
        mResponseBuilderFactory = pResponseBuilderFactory;
    }
}
