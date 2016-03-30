package com.pgt.common.service;

import java.util.List;

import javax.transaction.Transactional;

import com.pgt.common.bean.BannerQuery;
import com.pgt.common.bean.BannerWebSite;
import com.pgt.constant.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.common.bean.Banner;
import com.pgt.common.dao.BannerMapper;
/**
 * Created by ddjunshi 2015年11月13日
 */
@Service
@Transactional
public class BannerServiceImpl implements BannerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BannerService.class);

	@Autowired
	private BannerMapper bannerMapper;

	@Override
	public Integer createBanner(Banner banner) {

		bannerMapper.createBanner(banner);
		return banner.getBannerId();
	}


	public Banner queryBannerByTypeAndWebSite(String type,String webSite){

		LOGGER.debug("The query banner type is {} and website is {}",type,webSite);
		BannerQuery bannerQuery = new BannerQuery();
		bannerQuery.setType(type);
		bannerQuery.setSite( webSite);
		List<Banner> bannerList=bannerMapper.queryBannerByQuery(bannerQuery);
		if(!CollectionUtils.isEmpty(bannerList)){
			LOGGER.debug("The banner query id is ",bannerList.get(0).getBannerId());
			return bannerList.get(0);
		}
        LOGGER.debug("The banner query is empty");
		return null;
	}

	@Override
	public Integer queryBannerCount(BannerQuery bannerQuery) {
		return bannerMapper.queryBannerCount(bannerQuery);
	}

	@Override
	public List<Banner> queryBannerByQuery(BannerQuery bannerQuery) {
		return bannerMapper.queryBannerByQuery(bannerQuery);
	}

	@Override
	public Integer updateBanner(Banner banner) {
		bannerMapper.updateBanner(banner);
		return banner.getBannerId();
	}

	@Override
	public Integer deleteBanner(Integer bannerId) {

		bannerMapper.deleteBannerById(bannerId);
		return bannerId;
	}

	@Override
	public Banner queryBanner(Integer bannerId) {

		Banner banner = bannerMapper.queryBanner(bannerId);
		return banner;
	}

	@Override
	public List<Banner> queryAllBanner() {

		List<Banner> banners = bannerMapper.queryAllBanners();
		return banners;
	}

	@Override
	public Banner queryBannerByType(String type) {
		return bannerMapper.queryBannerByType(type);
	}

	public BannerMapper getBannerMapper() {
		return bannerMapper;
	}

	public void setBannerMapper(BannerMapper bannerMapper) {
		this.bannerMapper = bannerMapper;
	}

}
