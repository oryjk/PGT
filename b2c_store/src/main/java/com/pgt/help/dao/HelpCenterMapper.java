package com.pgt.help.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.pgt.base.mapper.SqlMapper;
import com.pgt.help.bean.HelpCenter;

@Component
public interface HelpCenterMapper extends SqlMapper {

	List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId);

}
