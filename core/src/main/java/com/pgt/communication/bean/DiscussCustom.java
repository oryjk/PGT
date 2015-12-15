package com.pgt.communication.bean;

import com.pgt.utils.PaginationBean;

/**
 * Created by ddjunshi 2015年11月19日
 */
public class DiscussCustom extends Discuss {

	private PaginationBean paginationBean;//封装分页信息

	public PaginationBean getPaginationBean() {
		return paginationBean;
	}

	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

}
