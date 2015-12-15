package com.pgt.common.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.URLConfiguration;

public class BannerController {

	private BannerService bannerService;

	private URLConfiguration urlConfiguration;

	/********************* 后台 **********************/

	// 1.进入banner列表
	public ModelAndView bannerList(ModelAndView modelAndView) {

		List<Banner> banners = bannerService.queryAllBanner();

		modelAndView.addObject("banners", banners);

		return modelAndView;
	}

	// 2.进入修改banner的页面
	public String updateBannerUI(Integer bannerId, Model model) {

		Banner banner = bannerService.queryBanner(bannerId);

		model.addAttribute("banner", banner);

		return "";
	}

	// 3.修改banner提交
	public ModelAndView updateBanner(ModelAndView modelAndView, Banner banner) {

		bannerService.updateBanner(banner);

		return modelAndView;
	}

	// 4.根据id删除一个banner
	public String deleteBannerById(Integer bannerId) {

		bannerService.deleteBanner(bannerId);

		return "";
	}

	public BannerService getBannerService() {
		return bannerService;
	}

	public void setBannerService(BannerService bannerService) {
		this.bannerService = bannerService;
	}

	public URLConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}

	public void setUrlConfiguration(URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

}
