package com.pgt.help.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.help.bean.HelpCenter;
import com.pgt.utils.PaginationBean;

@Component
public interface HelpCenterMapper extends SqlMapper {

	List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId);

	Integer createHelpCenter(HelpCenter helpCenter);

	Integer updateHelpCenter(HelpCenter helpCenter);

	void deleteHelpCenterById(Integer HelpCenterId);

	HelpCenter findHelpCenterById(Integer HelpCenterId);

	List<HelpCenter> queryHelpCenters(@Param("helpCenter")HelpCenter helpCenter, @Param("paginationBean") PaginationBean paginationBean);

}
