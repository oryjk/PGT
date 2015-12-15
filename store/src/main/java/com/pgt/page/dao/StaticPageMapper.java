package com.pgt.page.dao;

import java.util.List;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.page.bean.StaticPage;
import com.pgt.page.bean.StaticPageBannerRelation;
import com.pgt.page.bean.StaticPageCategoryRelation;

/**
 * Created by ddjunshi 2015年11月14日
 */
public interface StaticPageMapper extends SqlMapper{

	
	void createStaticPage(StaticPage staticPage);

	void updateStaticPage(StaticPage staticPage);

	void deleteStaticPageById(Integer staticPageId);

	StaticPage queryStaticPage(Integer staticPageId);

	List<StaticPage> queryAllStaticPage();

	
	// ********************StcticPage and Banner Relation

	void createStaticPageBannerRelation(StaticPageBannerRelation sBannerRelation);

	void deleteStaticPageBannerRelation(Integer staticPageId);

	List<StaticPageBannerRelation> queryStaticPageBannerRelation(Integer staticPageId);

	
	// *********************StaticPage and Category Realtion

	void createStaticPageCategoryRelation(StaticPageCategoryRelation sCategoryRelation);

	void deleteStaticPageCategoryRelation(Integer staticPageId);

	List<StaticPageCategoryRelation> queryStaticPageCategoryRelation(Integer staticPageId);

}
