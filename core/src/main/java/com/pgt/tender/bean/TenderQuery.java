package com.pgt.tender.bean;

import com.pgt.base.bean.OrderField;
import com.pgt.utils.PaginationBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaodong on 16-1-27.
 */
public class TenderQuery extends Tender {

	private PaginationBean paginationBean;

	/**
	 * 是否启用模糊查询
	 */
	private boolean nameLike;

	/**
	 * 封装排序条件
	 */
	private List<OrderField> orderFields = new ArrayList<OrderField>();


	public boolean isNameLike () {
		return nameLike;
	}

	public void setNameLike (boolean nameLike) {
		this.nameLike = nameLike;
	}


	/**
	 * 任意排序条件
	 *
	 * @param isAsc 是否升序
	 * @return
	 */
	public TenderQuery orderbyCondition (boolean isAsc, String condition) {
		orderFields.add(new OrderField(condition, isAsc ? "ASC" : "DESC"));
		return this;
	}


	/**
	 * 序号
	 *
	 * @param isAsc 是否升序
	 * @return
	 */
	public TenderQuery orderbyId (boolean isAsc) {
		orderFields.add(new OrderField("id", isAsc ? "ASC" : "DESC"));
		return this;
	}

	/**
	 * 当铺ID
	 *
	 * @param isAsc 是否升序
	 * @return
	 */
	public TenderQuery orderbyPawnShopId (boolean isAsc) {
		orderFields.add(new OrderField("pawnShopId", isAsc ? "ASC" : "DESC"));
		return this;
	}

	/**
	 * 当铺编号
	 *
	 * @param isAsc 是否升序
	 * @return
	 */
	public TenderQuery orderbyPawnTicketId (boolean isAsc) {
		orderFields.add(new OrderField("pawnTicketId", isAsc ? "ASC" : "DESC"));
		return this;
	}

	/**
	 * 开标时间
	 *
	 * @param isAsc 是否升序
	 * @return
	 */
	public TenderQuery orderbyPublishDate (boolean isAsc) {
		orderFields.add(new OrderField("publishDate", isAsc ? "ASC" : "DESC"));
		return this;
	}

	public PaginationBean getPaginationBean () {
		return paginationBean;
	}

	public void setPaginationBean (PaginationBean paginationBean) {
		this.paginationBean = paginationBean;
	}
}
