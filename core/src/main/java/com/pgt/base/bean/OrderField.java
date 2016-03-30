package com.pgt.base.bean;

/**
 * Created by xiaodong on 16-1-28.
 */
public class OrderField {

	private String fieldName;

	private String order;

	public OrderField (String fieldName, String order) {
		super();
		this.fieldName = fieldName;
		this.order = order;
	}

	public String getFieldName () {
		return fieldName;
	}

	public void setFieldName (String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOrder () {
		return order;
	}

	public void setOrder (String order) {
		this.order = order;
	}


}
