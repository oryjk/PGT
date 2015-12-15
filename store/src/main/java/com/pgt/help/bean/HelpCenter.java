package com.pgt.help.bean;

import java.io.Serializable;
import java.util.List;

import com.pgt.common.bean.Media;

/**
 * @author zhangxiaodong
 *
 *         2015年12月6日
 */
public class HelpCenter implements Serializable {

	private Integer id;
	private String title;
	private String content;
	private Integer relatedCategoryId;
	private Media frontMedia;
	private String type;
	private List<HelpCenter> childrens;
	private HelpCenter parent;

	public HelpCenter getParent() {
		return parent;
	}

	public void setParent(HelpCenter parent) {
		this.parent = parent;
	}

	public List<HelpCenter> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<HelpCenter> childrens) {
		this.childrens = childrens;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelatedCategoryId() {
		return relatedCategoryId;
	}

	public void setRelatedCategoryId(Integer relatedCategoryId) {
		this.relatedCategoryId = relatedCategoryId;
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
