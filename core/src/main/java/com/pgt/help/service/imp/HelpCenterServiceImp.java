package com.pgt.help.service.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.category.service.CategoryHelper;
import com.pgt.help.bean.HelpCategoryVo;
import com.pgt.help.bean.HelpCenter;
import com.pgt.help.dao.HelpCenterMapper;
import com.pgt.help.service.HelpCenterService;

/**
 * 
 * @author zhangxiaodong
 *
 *         2015年12月7日
 */

@Service
public class HelpCenterServiceImp implements HelpCenterService {

	@Autowired
	private HelpCenterMapper helpCenterMapper;

	@Autowired
	private CategoryHelper categoryHelper;

	@Override
	public List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId) {

		return helpCenterMapper.findHelpCentersByCategoryId(relatedCategoryId);

	}

	@Override
	public List<HelpCategoryVo> findAllHelpCategoryVo() {

		List<Category> categories = categoryHelper.findHelpCenterCategories();
		List<HelpCategoryVo> helpCategoryVos = new ArrayList<HelpCategoryVo>();

		for (Category category : categories) {
			HelpCategoryVo helpCategoryVo = new HelpCategoryVo();
			List<HelpCenter> helpCenters = helpCenterMapper.findHelpCentersByCategoryId(category.getId());
			helpCategoryVo.setCategory(category);
			helpCategoryVo.setHelpCenterList(helpCenters);
			helpCategoryVos.add(helpCategoryVo);
		}
		return helpCategoryVos;
	}

}
