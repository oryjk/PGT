package com.pgt.common.controller;

import java.util.List;

import com.pgt.configuration.Configuration;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import com.pgt.configuration.URLConfiguration;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@RestController
@RequestMapping("/banner")
public class BannerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	private BannerService bannerService;

	@Autowired
	private URLConfiguration urlConfiguration;

	@Autowired
	private Configuration configuration;

	@RequestMapping(value="/bannerList",method = RequestMethod.GET)
	public ModelAndView bannerList(ModelAndView modelAndView, RedirectAttributes redirectAttributes,
								   @RequestParam(value = "currentPage", required = false) Integer currentPage,
								   @RequestParam(value = "capacity", required = false) Long capacity) {

		PaginationBean paginationBean = new PaginationBean();
        if(ObjectUtils.isEmpty(currentPage)){
           currentPage=1;
		}
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if(!ObjectUtils.isEmpty(capacity)){
			paginationBean.setCapacity(capacity);
		}
        paginationBean.setCurrentIndex((currentPage-1)*paginationBean.getCapacity());
		//paginationBean.setTotalAmount();

		List<Banner> banners = bannerService.queryAllBanner();
		modelAndView.addObject("banners", banners);
        modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/addBanner",method = RequestMethod.GET)
	public ModelAndView addBanner(ModelAndView modelAndView){

		modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/addBannerSubmit",method = RequestMethod.POST)
    public ModelAndView addBannerSubmit(ModelAndView modelAndView,Banner banner){

		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is Empty");
		}
		if(StringUtils.isEmpty(banner.getType())){
			LOGGER.debug("The banner Type is Empty");
		}
		bannerService.createBanner(banner);
		return modelAndView;
	}

    @RequestMapping(value="/updateBanner",method = RequestMethod.GET)
	public ModelAndView updateBanner(Integer bannerId,ModelAndView modelAndView) {

		Banner banner = bannerService.queryBanner(bannerId);
		modelAndView.addObject("banner",banner);
		return modelAndView;
	}

	@RequestMapping(value="/updateBannerSubmit",method = RequestMethod.POST)
	public ModelAndView updateBannerSubmit(ModelAndView modelAndView, Banner banner) {

		if(ObjectUtils.isEmpty(banner)) {
			LOGGER.debug("The banner is Empty");
		}
		if(StringUtils.isEmpty(banner.getType())){
			LOGGER.debug("The banner Type is Empty");
		}
		bannerService.updateBanner(banner);
		return modelAndView;
	}

	@RequestMapping(value="/deleteBannerById",method= RequestMethod.GET)
	public ModelAndView deleteBannerById(Integer bannerId,ModelAndView modelAndView) {

		if(bannerId==null){
			LOGGER.debug("The bannerId Empty");
		}
		bannerService.deleteBanner(bannerId);
		return modelAndView;
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
