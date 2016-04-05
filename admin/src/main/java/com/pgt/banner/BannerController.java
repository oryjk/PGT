package com.pgt.banner;

import com.pgt.category.service.CategoryHelper;
import com.pgt.common.bean.*;
import com.pgt.common.service.BannerService;
import com.pgt.common.service.ImageService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
@RequestMapping("/banner")
public class BannerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BannerController.class);

	@Autowired
	private BannerService bannerService;

	@Autowired
	private ImageService imageService;

	@Autowired
	private URLConfiguration urlConfiguration;

	@Autowired
	private Configuration configuration;

	@Autowired
	private CategoryHelper categoryHelper;

	/**
	 * query bannerList
	 * @param modelAndView
	 * @param redirectAttributes
	 * @param currentPage
	 * @param capacity
	 * @param bannerQuery
     * @return
     */
	@RequestMapping(value="/bannerList",method = RequestMethod.GET)
	public ModelAndView bannerList (ModelAndView modelAndView,
	                                @RequestParam(value = "currentIndex", required = false) Integer currentIndex,
	                                @RequestParam(value = "capacity", required = false) Long capacity, BannerQuery bannerQuery) {

		PaginationBean paginationBean = new PaginationBean();
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 1;
		}
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if(!ObjectUtils.isEmpty(capacity)){
			paginationBean.setCapacity(capacity);
			LOGGER.debug("The capacity is {}",capacity);
		}
		BannerQuery bannerCountQuery = new BannerQuery();
		bannerCountQuery.setFields("count(*)");
		Integer total=bannerService.queryBannerCount(bannerCountQuery);
		LOGGER.debug("The banner total is {}",total);
		if (total != 0) {
			paginationBean.setTotalAmount(total);
			paginationBean.setCurrentIndex(currentIndex);
			bannerQuery.setPaginationBean(paginationBean);
			List<Banner> bannerList = bannerService.queryBannerByQuery(bannerQuery);
			paginationBean.setTotalAmount(bannerList.size());
			modelAndView.addObject("bannerList", bannerList);
			modelAndView.addObject("paginationBean", paginationBean);
		}
		modelAndView.setViewName("/banner/listBanner");
		return modelAndView;
	}

	/**
	 * add BannerUI
	 * @param modelAndView
	 * @return
     */
	@RequestMapping(value="/addBanner",method = RequestMethod.GET)
	public ModelAndView addBanner(ModelAndView modelAndView){

		modelAndView.setViewName("/banner/bannerAndModify");
		BannerType[] types=BannerType.values();
		List<String> categoryName=categoryHelper.findRootCategoriesName();
		for (BannerType banner:types) {
			categoryName.add(banner.toString());
		}
		modelAndView.addObject("types",categoryName);
		BannerWebSite[] webSites=   BannerWebSite.values();
		modelAndView.addObject("webSites",webSites);
		return modelAndView;
	}

	/**
	 * add BannerSubmit
	 * @param modelAndView
	 * @param banner
     * @return
     */
	@RequestMapping(value="/addBannerSubmit",method = RequestMethod.POST)
    public ModelAndView addBannerSubmit(ModelAndView modelAndView, Banner banner){

		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is Empty");
			modelAndView.clear();
			modelAndView.setViewName("redirect:/banner/addBanner");
			return modelAndView;
		}
		if(StringUtils.isEmpty(banner.getType())){
			LOGGER.debug("The banner Type is Empty");
			modelAndView.clear();
			modelAndView.setViewName("redirect:/banner/addBanner");
			return modelAndView;
		}
		if(StringUtils.isEmpty(banner.getStatus())){
            LOGGER.debug("The banner status is Empty");
			modelAndView.clear();
			modelAndView.setViewName("redirect:/banner/addBanner");
			return modelAndView;
		}

		Banner oldBanner=bannerService.queryBannerByTypeAndWebSite(banner.getType(),banner.getSite());
        if(!ObjectUtils.isEmpty(oldBanner)){
			modelAndView.addObject("error","已经有该位置的Banner,无法添加");

			BannerType[] types=BannerType.values();
			List<String> categoryName=categoryHelper.findRootCategoriesName();
			for (BannerType type:types) {
				categoryName.add(type.toString());
			}
			modelAndView.addObject("types",categoryName);
			LOGGER.debug("The banner status is extis");
			BannerWebSite[] webSites=   BannerWebSite.values();
			modelAndView.addObject("webSites",webSites);
			modelAndView.setViewName("/banner/bannerAndModify");
			return modelAndView;
		}
		bannerService.createBanner(banner);
		modelAndView.setViewName("redirect:/banner/bannerList");
		return modelAndView;
	}

	/**
	 * update bannerUI
	 * @param bannerId
	 * @param modelAndView
     * @return
     */
    @RequestMapping(value="/updateBanner/{bannerId}",method = RequestMethod.GET)
	public ModelAndView updateBanner(@PathVariable("bannerId") Integer bannerId, ModelAndView modelAndView) {

		Banner banner = bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is Empty");
			return modelAndView;
		}
		modelAndView.addObject("banner",banner);

		BannerType[] types=BannerType.values();
		List<String> categoryName=categoryHelper.findRootCategoriesName();
		for (BannerType type:types) {
			categoryName.add(type.toString());
		}
		modelAndView.addObject("types",categoryName);
		BannerWebSite[] webSites= BannerWebSite.values();
		modelAndView.addObject("webSites",webSites);
		modelAndView.setViewName("/banner/bannerAndModify");
		return modelAndView;
	}

	/**
	 * update BannerSubmit
	 * @param modelAndView
	 * @param banner
     * @return
     */
	@RequestMapping(value="/updateBannerSubmit",method = RequestMethod.POST)
	public ModelAndView updateBannerSubmit(ModelAndView modelAndView, Banner banner) {

		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is Empty");
			modelAndView.setViewName("redirect:/banner/updateBanner");
			return modelAndView;
		}
		if(StringUtils.isEmpty(banner.getType())){
			LOGGER.debug("The banner Type is Empty");
			modelAndView.setViewName("redirect:/banner/updateBanner");
			return modelAndView;
		}
		if(StringUtils.isEmpty(banner.getStatus())){
			LOGGER.debug("The banner status is Empty");
			modelAndView.setViewName("redirect:/banner/updateBanner");
			return modelAndView;
		}

		Banner oldBanner=bannerService.queryBannerByTypeAndWebSite(banner.getType(),banner.getSite());
		if(!ObjectUtils.isEmpty(oldBanner)&&oldBanner.getBannerId()!=banner.getBannerId()){
			modelAndView.addObject("error","已经有该位置的Banner,无法修改位置");

			BannerType[] types=BannerType.values();
			List<String> categoryName=categoryHelper.findRootCategoriesName();
			for (BannerType type:types) {
				categoryName.add(type.toString());
			}
			modelAndView.addObject("types",categoryName);
			BannerWebSite[] webSites=   BannerWebSite.values();
			modelAndView.addObject("webSites",webSites);

			LOGGER.debug("The banner type is extis");
			modelAndView.setViewName("/banner/bannerAndModify");
			return modelAndView;
		}

		bannerService.updateBanner(banner);
		modelAndView.setViewName("redirect:/banner/bannerList");
		return modelAndView;
	}

	/**
	 * delete BannerById
	 * @param bannerId
	 * @param modelAndView
     * @return
     */
	@RequestMapping(value="/deleteBannerById/{bannerId}",method= RequestMethod.GET)
	public ModelAndView deleteBannerById(@PathVariable("bannerId") Integer bannerId,ModelAndView modelAndView) {

		if(bannerId==null){
			LOGGER.debug("The bannerId Empty");
			modelAndView.setViewName("redirect:/banner/bannerList");
			return modelAndView;
		}
		bannerService.deleteBanner(bannerId);
		modelAndView.setViewName("redirect:/banner/bannerList");
		return modelAndView;
	}


	/**
	 * query Banner
	 * @param bannerId
	 * @param currentIndex
	 * @param capacity
	 * @param modelAndView
     * @return
     */
	@RequestMapping(value ="/queryBanner",method=RequestMethod.GET)
	public ModelAndView queryBanner(@RequestParam(value = "bannerId", required = true) Integer bannerId,@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
									@RequestParam(value = "capacity", required = false) Long capacity,ModelAndView modelAndView){

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is Empty");
			return modelAndView;
		}

		PaginationBean paginationBean= new PaginationBean();
		if (ObjectUtils.isEmpty(currentIndex)) {
			currentIndex = 0;
		}
		paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
		if (!ObjectUtils.isEmpty(capacity)) {
			paginationBean.setCapacity(capacity);
		}
		paginationBean.setCurrentIndex(currentIndex);
		int total=imageService.queryImageByBannerCount(bannerId);
		Banner banner = bannerService.queryBanner(bannerId);
		if(total!=0) {
			paginationBean.setTotalAmount(total);

			ImageCustom imageCustom = new ImageCustom();
			imageCustom.setBanner(banner);
			imageCustom.setPaginationBean(paginationBean);
			List<Image> imageList = imageService.queryImageByBanner(imageCustom);
			modelAndView.addObject("imageList",imageList);
			modelAndView.addObject("paginationBean",paginationBean);
		}
		modelAndView.addObject("banner",banner);

		modelAndView.setViewName("/banner/bannerImagelist");
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
