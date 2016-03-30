package com.pgt.help.bean;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotEmpty;

import com.pgt.category.bean.Category;
import com.pgt.common.bean.Media;

/**
 * @author zhangxiaodong
 *
 *         2015年12月6日
 */
public class HelpCenter implements Serializable {

	private Integer id;
	@NotEmpty(message = "{NotEmpty.helpCenter.title}")
	private String title;
	@NotEmpty(message = "{NotEmpty.helpCenter.content}")
	private String content;
	private Category category;
	private Media frontMedia;
	private String type;
	private HelpCenter parent;
    private String site;

	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public HelpCenter getParent() {
		return parent;
	}

	public void setParent(HelpCenter parent) {
		this.parent = parent;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Media getFrontMedia() {
		return frontMedia;
	}

	public void setFrontMedia(Media frontMedia) {
		this.frontMedia = frontMedia;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
