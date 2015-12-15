package com.pgt.hot.bean;

import java.io.Serializable;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
/**
 * 
 * Created by ddjunshi 2015年11月30日
 */
public class HotSearch implements Serializable {

	private int hotSearchId;//热门搜索id
	private String term;// 搜索关键字
	private ProductMedia frontMedia;//热门搜索的图片信息

	public int getHotSearchId() {
		return hotSearchId;
	}

	public void setHotSearchId(int hotSearchId) {
		this.hotSearchId = hotSearchId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public ProductMedia getFrontMedia() {
		return frontMedia;
	}

	public void setFrontMedia(ProductMedia frontMedia) {
		this.frontMedia = frontMedia;
	}

}
