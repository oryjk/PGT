package com.pgt.help.bean;

import java.util.List;

import com.pgt.category.bean.Category;

/**
 * @author zhangxiaodong
 *         2015年12月7日
 */


public class HelpCategoryVo {

	private Category category;
	private List<HelpCenter> helpCenterList;

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<HelpCenter> getHelpCenterList() {
		return helpCenterList;
	}

	public void setHelpCenterList(List<HelpCenter> helpCenterList) {
		this.helpCenterList = helpCenterList;
	}

}
