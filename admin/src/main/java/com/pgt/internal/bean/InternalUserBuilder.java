package com.pgt.internal.bean;

import com.pgt.cart.util.RepositoryUtils;

import java.util.Date;

public class InternalUserBuilder implements Trimable {
	private int mId;
	private String mLogin;
	private String mPassword;
	private String mSalt;
	private String mName;
	private String mPhone;
	private String mEmail;
	private String mIp;
	private boolean mAvailable;
	private Role mRole;
	private InternalUserInvestType mInvestType;
	private Date mCreationDate;
	private Date mLastLoginDate;
	private Date mUpdateDate;

	public InternalUserBuilder setId(final int pId) {
		mId = pId;
		return this;
	}

	public InternalUserBuilder setLogin(final String pLogin) {
		mLogin = pLogin;
		return this;
	}

	public InternalUserBuilder setPassword(final String pPassword) {
		mPassword = pPassword;
		return this;
	}

	public InternalUserBuilder setSalt(final String pSalt) {
		mSalt = pSalt;
		return this;
	}

	public InternalUserBuilder setName(final String pName) {
		mName = pName;
		return this;
	}

	public InternalUserBuilder setPhone(final String pPhone) {
		mPhone = pPhone;
		return this;
	}

	public InternalUserBuilder setEmail(final String pEmail) {
		mEmail = pEmail;
		return this;
	}

	public InternalUserBuilder setIp(final String pIp) {
		mIp = pIp;
		return this;
	}

	public InternalUserBuilder setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
		return this;
	}
	public InternalUserBuilder setAvailable(final String pAvailable) {
		mAvailable = RepositoryUtils.idIsValid(pAvailable);
		return this;
	}

	public InternalUserBuilder setRole(final Role pRole) {
		mRole = pRole;
		return this;
	}

	public InternalUserBuilder setInvestType(final InternalUserInvestType pInvestType) {
		mInvestType = pInvestType;
		return this;
	}

	public InternalUserBuilder setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
		return this;
	}

	public InternalUserBuilder setLastLoginDate(final Date pLastLoginDate) {
		mLastLoginDate = pLastLoginDate;
		return this;
	}

	public InternalUserBuilder setUpdateDate(final Date pUpdateDate) {
		mUpdateDate = pUpdateDate;
		return this;
	}

	public InternalUser createInternalUser() {
		return new InternalUser(mId, mLogin, mPassword, mSalt, mName, mPhone, mEmail, mIp, mAvailable, mRole, mInvestType, mCreationDate,
				mLastLoginDate, mUpdateDate);
	}

	@Override
	public void trimFields() {
		if (mLogin != null) {
			mLogin = mLogin.trim();
		}
		if (mName != null) {
			mName = mName.trim();
		}
		if (mPhone != null) {
			mPhone = mPhone.trim();
		}
		if (mEmail != null) {
			mEmail = mEmail.trim();
		}
	}
}