package com.pgt.common.bean;

import com.pgt.utils.PaginationBean;

/**
 * image的包装类，用于封装分页等 Created by ddjunshi 2015年11月20日
 */
public class ImageCustom extends Image {

	private PaginationBean paginationBean;

	public PaginationBean getPaginationBean() {
		return paginationBean;
	}

	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

}
