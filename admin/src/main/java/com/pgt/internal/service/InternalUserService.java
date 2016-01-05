package com.pgt.internal.service;

import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.dao.InternalUserDao;
import com.pgt.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yove on 10/20/2015.
 */
@Service(value = "internalUserService")
public class InternalUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(InternalUserService.class);

	public static final String SPLIT = "#";

	@Resource(name = "internalUserDao")
	private InternalUserDao mInternalUserDao;

	private int mRememberExpirationDay = 7;

	public InternalUser findInternalUser(final String pLogin) {
		return getInternalUserDao().queryUserByLogin(pLogin);
	}

	public InternalUser findInternalUser(final int pId) {
		return getInternalUserDao().queryUserById(pId);
	}

	public void updateLastLogin(int pId, final String pIp) {
		getInternalUserDao().updateLastLogin(pId, pIp);
	}

	public boolean createInternalUser(final InternalUser pInternalUser) {
		return getInternalUserDao().createInternalUser(pInternalUser) > 0;
	}

	public List<InternalUser> queryInternalUserPage(final InternalPagination pPagination) {
		long count = getInternalUserDao().queryInternalUserCount(pPagination);
		pPagination.setCount(count);
		LOGGER.debug("Get internal user of count: {} with keyword: {}", count, pPagination.getKeyword());
		if (count > 0) {
			List<InternalUser> internalUsers = getInternalUserDao().queryInternalUserPage(pPagination);
			pPagination.setResult(internalUsers);
		} else {
			pPagination.setResult(Collections.EMPTY_LIST);
		}
		return (List<InternalUser>) pPagination.getResult();
	}

	public boolean updateInternalUser(final InternalUser pInternalUser) {
		if (!RepositoryUtils.idIsValid(pInternalUser.getId())) {
			LOGGER.debug("Cannot update internal user with invalid id: {}", pInternalUser);
			return false;
		}
		return getInternalUserDao().updateInternalUser(pInternalUser) > 0;
	}

	public boolean updateBatchInternalUserAvailable(final int[] pIds, final boolean pAvailable) {
		return getInternalUserDao().updateBatchInternalUserAvailable(pIds, pAvailable);
	}

	public String encodeRememberInfo(int pUserId) {
		return CookieUtils.encodeBase64(new StringBuilder().append(pUserId).append(SPLIT).append(System.currentTimeMillis() + getRememberExpiration()).toString());
	}

	public int getRememberExpiration() {
		return getRememberExpirationDay() * 24 * 60 * 60 * 1000;
	}

	public InternalUserDao getInternalUserDao() {
		return mInternalUserDao;
	}

	public void setInternalUserDao(final InternalUserDao pInternalUserDao) {
		mInternalUserDao = pInternalUserDao;
	}

	public int getRememberExpirationDay() {
		return mRememberExpirationDay;
	}

	public void setRememberExpirationDay(final int pRememberExpirationDay) {
		mRememberExpirationDay = pRememberExpirationDay;
	}
}
