package com.pgt.communication.bean;

import java.util.Date;

import com.pgt.product.bean.Product;
import com.pgt.share.bean.ShareOrder;
import com.pgt.user.bean.User;

/**
 * Created by ddjunshi 2015年11月17日
 */
public class Discuss {

	private Integer id;

	private Date createDate;

	private String content;

	private Boolean isShow;

	private String ip;

	private User user;

	private Product product;

	private ShareOrder shareOrder;

	public ShareOrder getShareOrder() {
		return shareOrder;
	}

	public void setShareOrder(ShareOrder shareOrder) {
		this.shareOrder = shareOrder;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

}
