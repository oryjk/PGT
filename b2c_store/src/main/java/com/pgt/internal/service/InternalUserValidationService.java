package com.pgt.internal.service;

import com.pgt.internal.dao.InternalUserDao;
import com.pgt.internal.util.FieldsRegexValidator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 10/26/2015.
 */
@Service(value = "internalUserValidationService")
public class InternalUserValidationService {

	@Resource(name = "internalUserDao")
	private InternalUserDao mInternalUserDao;

	@Resource(name = "loginRegexValidator")
	private FieldsRegexValidator mLoginRegexValidator;

	@Resource(name = "passwordRegexValidator")
	private FieldsRegexValidator mPasswordRegexValidator;

	@Resource(name = "emailRegexValidator")
	private FieldsRegexValidator mEmailRegexValidator;

	@Resource(name = "phoneRegexValidator")
	private FieldsRegexValidator mPhoneRegexValidator;

	public boolean checkLoginUniqueness(String pLogin, Integer pId) {
		return getInternalUserDao().queryInternalUserLoginCountExcludeId(pLogin, pId) <= 0;
	}

	public boolean checkNameUniqueness(String pName, Integer pId) {
		return getInternalUserDao().queryInternalUserNameCountExcludeId(pName, pId) <= 0;
	}

	public boolean checkEmailUniqueness(String pEmail, Integer pId) {
		return getInternalUserDao().queryInternalUserEmailCountExcludeId(pEmail, pId) <= 0;
	}

	public boolean checkPhoneUniqueness(String pPhone, Integer pId) {
		return getInternalUserDao().queryInternalUserPhoneCountExcludeId(pPhone, pId) <= 0;
	}

	public FieldsRegexValidator getLoginRegexValidator() {
		return mLoginRegexValidator;
	}

	public void setLoginRegexValidator(final FieldsRegexValidator pLoginRegexValidator) {
		mLoginRegexValidator = pLoginRegexValidator;
	}

	public FieldsRegexValidator getPasswordRegexValidator() {
		return mPasswordRegexValidator;
	}

	public void setPasswordRegexValidator(final FieldsRegexValidator pPasswordRegexValidator) {
		mPasswordRegexValidator = pPasswordRegexValidator;
	}

	public FieldsRegexValidator getPhoneRegexValidator() {
		return mPhoneRegexValidator;
	}

	public void setPhoneRegexValidator(final FieldsRegexValidator pPhoneRegexValidator) {
		mPhoneRegexValidator = pPhoneRegexValidator;
	}

	public FieldsRegexValidator getEmailRegexValidator() {
		return mEmailRegexValidator;
	}

	public void setEmailRegexValidator(final FieldsRegexValidator pEmailRegexValidator) {
		mEmailRegexValidator = pEmailRegexValidator;
	}

	public InternalUserDao getInternalUserDao() {
		return mInternalUserDao;
	}

	public void setInternalUserDao(final InternalUserDao pInternalUserDao) {
		mInternalUserDao = pInternalUserDao;
	}
}
