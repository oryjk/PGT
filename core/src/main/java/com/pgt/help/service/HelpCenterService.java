package com.pgt.help.service;

import java.util.List;

import com.pgt.category.bean.Category;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.utils.PaginationBean;

/**
 * 
 * @author zhangxiaodong
 *
 *         2015年12月7日
 */
public interface HelpCenterService {

	List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId);

	List<HelpCategoryVo> findAllHelpCategoryVo();


	List<HelpCategoryVo> findAllHelpByQuery(HelpCenter helpCenter);

	Integer createHelpCenter(HelpCenter helpCenter);


	Integer updateHelpCenter(HelpCenter helpCenter);


	void deleteHelpCenterById(Integer helpCenterId);


	HelpCenter findHelpCenterById(Integer helpCenterId);

	List<HelpCenter> queryHelpCenters(HelpCenter category, PaginationBean paginationBean);
	   
	List<HelpCategoryVo> buildCategoryVoByCategories(List<Category> categories);
}
