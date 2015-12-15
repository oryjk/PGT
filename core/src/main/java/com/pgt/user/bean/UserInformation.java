package com.pgt.user.bean;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.pgt.common.bean.Media;
import com.pgt.user.validation.group.AddUserInformationGroup;

/**
 * 
 * @author zhangxiaodong
 *
 *         2015年12月4日
 */
public class UserInformation implements Serializable {

	private Integer id;

	private User user;

	@NotEmpty(message = "{NotNull.user.nickname}", groups = { AddUserInformationGroup.class })
	@Size(min=3,max=12,message="{}")
	private String nickname;

	@Pattern(regexp = "[0-9]{11}", message = "{Pattern.user.phoneNumber}", groups = { AddUserInformationGroup.class })
	@NotEmpty(message = "{NotNull.user.phoneNumber}", groups = { AddUserInformationGroup.class })
	private String phoneNumber;

	private String gender;

	@Pattern(regexp = "(^\\d{18}$)|(^\\d{15}$)", message = "{Error.userInformation.idCard.invalid}", groups = {
			AddUserInformationGroup.class })
	@NotEmpty(message = "{NotEmpty.userInformation.idCard}", groups = { AddUserInformationGroup.class })
	private String idCard;

	private String merrage;

	private String industry;// 行业

	
	private String income;

	private Media frontMedia;// 图片

	private String payBinding;// 支付绑定

	@Email(message="{Error.internalUser.email.invalid}",groups={AddUserInformationGroup.class})
	private String psersonEmail;// 个人邮箱

	private String type;

	public String getMerrage() {
		return merrage;
	}

	public void setMerrage(String merrage) {
		this.merrage = merrage;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getPayBinding() {
		return payBinding;
	}

	public void setPayBinding(String payBinding) {
		this.payBinding = payBinding;
	}

	public String getPsersonEmail() {
		return psersonEmail;
	}

	public void setPsersonEmail(String psersonEmail) {
		this.psersonEmail = psersonEmail;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Media getFrontMedia() {
		return frontMedia;
	}

	public void setFrontMedia(Media frontMedia) {
		this.frontMedia = frontMedia;
	}

}
