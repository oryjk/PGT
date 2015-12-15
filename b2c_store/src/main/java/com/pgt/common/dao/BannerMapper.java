package com.pgt.common.dao;

import java.util.List;
import com.pgt.base.mapper.SqlMapper;
import com.pgt.common.bean.Banner;

/**
 * 
 * Created by ddjunshi 2015年11月13日
 */
public interface BannerMapper extends SqlMapper {

	void createBanner(Banner banner);

	void updateBanner(Banner banner);

	void deleteBannerById(Integer bannerId);

	Banner queryBanner(Integer bannerId);

	List<Banner> queryAllBanners();
	
}
