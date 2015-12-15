package com.pgt.page.bean;

import java.util.List;
import com.pgt.category.bean.Category;
import com.pgt.common.bean.Banner;

/**
 * Created by ddjunshi 2015年11月14日
 */
public class StaticPage {

	private Integer staticPageId;

	private String name;

	private List<Banner> banners;

	private List<Category> categories;

	private String js;

	private String css;

	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStaticPageId() {
		return staticPageId;
	}

	public void setStaticPageId(Integer staticPageId) {
		this.staticPageId = staticPageId;
	}

	public List<Banner> getBanners() {
		return banners;
	}

	public void setBanners(List<Banner> banners) {
		this.banners = banners;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getJs() {
		return js;
	}

	public void setJs(String js) {
		this.js = js;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
