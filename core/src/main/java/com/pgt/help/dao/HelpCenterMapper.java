package com.pgt.help.dao;
import java.util.List;
import com.pgt.base.mapper.SqlMapper;
import com.pgt.help.bean.HelpCenter;
import org.springframework.stereotype.Component;

@Component
public interface HelpCenterMapper extends SqlMapper {

	List<HelpCenter> findHelpCentersByCategoryId(Integer relatedCategoryId);

	Integer createHelpCenter(HelpCenter helpCenter);

	Integer updateHelpCenter(HelpCenter helpCenter);

	void deleteHelpCenterById(Integer HelpCenterId);

	HelpCenter findHelpCenterById(Integer HelpCenterId);

}
