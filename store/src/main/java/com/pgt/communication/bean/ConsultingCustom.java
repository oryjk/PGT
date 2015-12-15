package com.pgt.communication.bean;

import com.pgt.utils.PaginationBean;

/**
 * Created by ddjunshi 2015年11月20日
 */
public class ConsultingCustom extends Consulting {

	private PaginationBean paginationBean;

	public PaginationBean getPaginationBean() {
		return paginationBean;
	}

	public void setPaginationBean(PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}

}
