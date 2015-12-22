package com.pgt.common.bean;

import java.io.Serializable;

/**
 * @author zhangxiaodong 2015年12月4日
 */

public class Media implements Serializable {

	private int id;
	private int referenceId;
	private String title;
	private String path;
	private int index;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(final int pId) {
		id = pId;
	}

	public int getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(final int pReferenceId) {
		referenceId = pReferenceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String pTitle) {
		title = pTitle;
	}

	public String getPath() {
		return path;
	}

	public void setPath(final String pPath) {
		path = pPath;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(final int pIndex) {
		index = pIndex;
	}

}
