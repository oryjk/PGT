package com.pgt.communication.bean;

import java.util.Date;
import java.util.List;

import com.pgt.product.bean.Product;
import com.pgt.user.bean.User;

/**
 * 
 * Created by ddjunshi 2015年11月17日
 */

public class Consulting {

	private Integer id;

	private Date createDate;

	private String content;

	private Boolean isShow;

	private String ip;

	private User user;

	private Product product;

	private Consulting parent;

	private List<Consulting> childrens;

	private String phoneNumber;

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Consulting getParent() {
		return parent;
	}

	public void setParent(Consulting parent) {
		this.parent = parent;
	}

	public List<Consulting> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<Consulting> childrens) {
		this.childrens = childrens;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
