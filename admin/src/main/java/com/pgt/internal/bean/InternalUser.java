package com.pgt.internal.bean;

import java.util.Date;

/**
 * Created by Yove on 10/19/2015.
 */
public class InternalUser implements Trimable {

	private int mId;
	private String mLogin;
	private transient String mPassword;
	private transient String mSalt;
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

	public InternalUser() {
	}

	public InternalUser(final int pId, final String pLogin, final String pPassword, final String pSalt, final String pName, final String
			pPhone, final String pEmail, final String pIp, final boolean pAvailable, final Role pRole, final InternalUserInvestType
			pInvestType, final Date pCreationDate, final Date pLastLoginDate, final Date pUpdateDate) {
		mId = pId;
		mLogin = pLogin;
		mPassword = pPassword;
		mSalt = pSalt;
		mName = pName;
		mPhone = pPhone;
		mEmail = pEmail;
		mIp = pIp;
		mAvailable = pAvailable;
		mRole = pRole;
		mInvestType = pInvestType;
		mCreationDate = pCreationDate;
		mLastLoginDate = pLastLoginDate;
		mUpdateDate = pUpdateDate;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("InternalUser{").append("mId=").append(mId).append(", mLogin='").append(mLogin).append('\'').append(", mAvailable=").append(mAvailable).append(", mRole=").append(mRole).append(", mInvestType=").append(mInvestType).append(", mName='").append(mName).append('\'').append(", mPhone='").append(mPhone).append('\'').append(", mEmail='").append(mEmail).append('\'').append('}').toString();
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

	public boolean isAdministrator() {
		return mRole == Role.ADMINISTRATOR;
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public String getLogin() {
		return mLogin;
	}

	public void setLogin(final String pLogin) {
		mLogin = pLogin;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(final String pPassword) {
		mPassword = pPassword;
	}

	public String getSalt() {
		return mSalt;
	}

	public void setSalt(final String pSalt) {
		mSalt = pSalt;
	}

	public String getName() {
		return mName;
	}

	public void setName(final String pName) {
		mName = pName;
	}

	public String getPhone() {
		return mPhone;
	}

	public void setPhone(final String pPhone) {
		mPhone = pPhone;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(final String pEmail) {
		mEmail = pEmail;
	}

	public String getIp() {
		return mIp;
	}

	public void setIp(final String pIp) {
		mIp = pIp;
	}

	public boolean isAvailable() {
		return mAvailable;
	}

	public void setAvailable(final boolean pAvailable) {
		mAvailable = pAvailable;
	}

	public Role getRole() {
		return mRole;
	}

	public void setRole(final Role pRole) {
		mRole = pRole;
	}

	public InternalUserInvestType getInvestType() {
		return mInvestType;
	}

	public void setInvestType(final InternalUserInvestType pInvestType) {
		mInvestType = pInvestType;
	}

	public Date getCreationDate() {
		return mCreationDate;
	}

	public void setCreationDate(final Date pCreationDate) {
		mCreationDate = pCreationDate;
	}

	public Date getLastLoginDate() {
		return mLastLoginDate;
	}

	public void setLastLoginDate(final Date pLastLoginDate) {
		mLastLoginDate = pLastLoginDate;
	}

	public Date getUpdateDate() {
		return mUpdateDate;
	}

	public void setUpdateDate(final Date pUpdateDate) {
		mUpdateDate = pUpdateDate;
	}
}
