package com.pgt.help.service;

import java.util.List;

import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;

/**
 * 
 * @author zhangxiaodong
 *
 *         2015年12月7日
 */
public interface HelpCenterService {

	List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId);

	List<HelpCategoryVo> findAllHelpCategoryVo();


	Integer createHelpCenter(HelpCenter helpCenter);


	Integer updateHelpCenter(HelpCenter helpCenter);


	void deleteHelpCenterById(Integer helpCenterId);


	HelpCenter findHelpCenterById(Integer helpCenterId);


}
