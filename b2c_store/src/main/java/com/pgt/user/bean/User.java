package com.pgt.user.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

import com.pgt.user.validation.group.LoginGroup;
import com.pgt.user.validation.group.RegistrationGroup;

/**
 * Created by cwang7 on 10/18/15. 客户端用户用户信息
 */

public class User implements Serializable {

    private Long id;
    @NotEmpty(message = "{NotEmpty.user.username}", groups = {LoginGroup.class, RegistrationGroup.class})
    private String username;

    @Pattern(regexp = "[0-9a-zA-Z]{6,32}", message = "{Pattern.user.password}", groups = {RegistrationGroup.class})
    @NotEmpty(message = "{NotEmpty.user.password}", groups = {LoginGroup.class, RegistrationGroup.class})
    private String password;

    @NotEmpty(message = "{NotEmpty.user.password2}", groups = {RegistrationGroup.class})
    private String password2;
    // @Pattern(regexp =
    // "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$",
    // message = "{email.format.wrong}")
    private String email;

    @Pattern(regexp = "[0-9]{11}", message = "{Pattern.user.phoneNumber}", groups = {RegistrationGroup.class})
    @NotEmpty(message = "{NotNull.user.phoneNumber}", groups = {RegistrationGroup.class})
    private String phoneNumber;
    // 创建时间
    private Date createDate;
    // 最近一次登录时间
    private Date updateDate;
    // 登录验证次数
    private Integer count = 0;
    private String salt;
    private boolean available;
    @NotEmpty(message = "{NotNull.user.authcode}", groups = {RegistrationGroup.class})
    private String authCode;
    @NotEmpty(message = "{NotNull.user.smsCode}", groups = {RegistrationGroup.class})
    private String smsCode;
    private Boolean autoLogin;
    private Boolean agree;
    private String loginError;
    private String userExist;
    private int yeepayStatus;
    private Date yeepayRegistoredDate;
    //phone serial number
    private String phoneId;
    private String headPortrait;
    private Integer defaultAddressId;
    private String yeepayUserName;
    private String yeepayUserId;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public Boolean getAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Boolean getAgree() {
        return agree;
    }

    public void setAgree(Boolean agree) {
        this.agree = agree;
    }

    public String getLoginError() {
        return loginError;
    }

    public void setLoginError(String loginError) {
        this.loginError = loginError;
    }

    public String getUserExist() {
        return userExist;
    }

    public void setUserExist(String userExist) {
        this.userExist = userExist;
    }

    public Date getYeepayRegistoredDate() {
        return yeepayRegistoredDate;
    }

    public void setYeepayRegistoredDate(Date yeepayRegistoredDate) {
        this.yeepayRegistoredDate = yeepayRegistoredDate;
    }

    public int getYeepayStatus() {
        return yeepayStatus;
    }

    public void setYeepayStatus(int yeepayStatus) {
        this.yeepayStatus = yeepayStatus;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

	public Integer getDefaultAddressId() {
		return defaultAddressId;
	}

	public void setDefaultAddressId(Integer defaultAddressId) {
		this.defaultAddressId = defaultAddressId;
	}

	public String getYeepayUserName() {
		return yeepayUserName;
	}

	public void setYeepayUserName(String yeepayUserName) {
		this.yeepayUserName = yeepayUserName;
	}

	public String getYeepayUserId() {
		return yeepayUserId;
	}

	public void setYeepayUserId(String yeepayUserId) {
		this.yeepayUserId = yeepayUserId;
	}

}
