package com.pgt.common.bean;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * Created by ddjunshi 2015年11月20日
 */
public class Image {

	private Integer imageId;

	@NotEmpty(message = "{NotEmpty.banner_Image.createDate}")
	private Date createDate;// 开始时间

	@NotEmpty(message = "{NotEmpty.banner_Image.endDate}")
	private Date endDate;// 结束时间

	@NotEmpty(message = "{NotEmpty.banner_Image.location}")
	private String location;//序号

	@NotEmpty(message = "{NotEmpty.banner_Image.url}")
	private String url; // 连接地址

	private String path; // 存储图片的路径

	private String type;// 类型

	private String title;// 标题

	@NotEmpty(message = "{NotEmpty.banner_Image.color}")
	public String color;//背景颜色

	private Banner banner;


	public Banner getBanner() {
		return banner;
	}
	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
