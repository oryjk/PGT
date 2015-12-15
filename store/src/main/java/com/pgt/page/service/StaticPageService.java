package com.pgt.page.service;

import java.util.List;

import com.pgt.page.bean.StaticPage;

/**
 * Created by ddjunshi 2015年11月14日
 */

public interface StaticPageService {

	/**
	 * @param staticPage
	 *            封装js，css路径，选中banner的id和category的id
	 * @return staticPageId
	 */
	Integer cretaeStaticPage(StaticPage staticPage);

	/**
	 * @param staticPage
	 *            封装js，css路径，选中banner的id和category的id
	 * @return staticPageId
	 */
	Integer updateStaticPage(StaticPage staticPage);

	/**
	 * @param staticPageId
	 * @return StaticPage 封装了，js，css，category和banner
	 */
	StaticPage useStaticPageById(Integer staticPageId);

	/**
	 * @return 所有的静态页面列表，根据名称选中具体的模板使用
	 */
	List<StaticPage> queryAllStaticPage();

	Integer deleteStaticPageById(Integer staticPageId);

}
