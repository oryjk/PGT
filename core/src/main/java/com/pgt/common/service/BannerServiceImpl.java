package com.pgt.common.service;

import java.util.List;

import javax.transaction.Transactional;

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

	@Autowired
	private BannerMapper bannerMapper;

	@Override
	public Integer createBanner(Banner banner) {

		bannerMapper.createBanner(banner);
		return banner.getBannerId();
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

	public BannerMapper getBannerMapper() {
		return bannerMapper;
	}

	public void setBannerMapper(BannerMapper bannerMapper) {
		this.bannerMapper = bannerMapper;
	}

}
