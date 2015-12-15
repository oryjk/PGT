package com.pgt.common.service;

import java.util.List;
import com.pgt.common.bean.Banner;
/**
 * Created by ddjunshi 2015年11月13日
 */
public interface BannerService {
	
	Integer createBanner(Banner banner);

	Integer updateBanner(Banner banner);

    Integer deleteBanner(Integer bannerId);

    Banner queryBanner(Integer bannerId);

    List<Banner> queryAllBanner();
	
}