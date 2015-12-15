package com.pgt.common.bean;

import java.io.Serializable;

/**
 * @author zhangxiaodong 2015年12月4日
 */

public class Media implements Serializable {

	private int mId;
	private int mReferenceId;
	private String mTitle;
	private String mPath;
	private int mIndex;
	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return mId;
	}

	public void setId(final int pId) {
		mId = pId;
	}

	public int getReferenceId() {
		return mReferenceId;
	}

	public void setReferenceId(final int pReferenceId) {
		mReferenceId = pReferenceId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(final String pTitle) {
		mTitle = pTitle;
	}

	public String getPath() {
		return mPath;
	}

	public void setPath(final String pPath) {
		mPath = pPath;
	}

	public int getIndex() {
		return mIndex;
	}

	public void setIndex(final int pIndex) {
		mIndex = pIndex;
	}

}
