package com.pgt.banner;

import java.util.List;

import com.pgt.common.bean.BannerQuery;
import com.pgt.configuration.Configuration;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
								   @RequestParam(value = "capacity", required = false) Long capacity,BannerQuery bannerQuery) {

		PaginationBean paginationBean = new PaginationBean();
        if(ObjectUtils.isEmpty(currentPage)){
           currentPage=1;
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
		paginationBean.setTotalAmount(total);

		paginationBean.setCurrentIndex((currentPage-1)*paginationBean.getCapacity());
		bannerQuery.setPaginationBean(paginationBean);
		List<Banner> bannerList= bannerService.queryBannerByQuery(bannerQuery);
		paginationBean.setTotalAmount(bannerList.size());

		modelAndView.addObject("bannerList",bannerList);
        modelAndView.addObject("paginationBean",paginationBean);
        modelAndView.setViewName("/banner/listBanner");
		return modelAndView;
	}

	@RequestMapping(value="/addBanner",method = RequestMethod.GET)
	public ModelAndView addBanner(ModelAndView modelAndView){

		modelAndView.setViewName("/banner/bannerAndModify");
		return modelAndView;
	}

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

		Banner oldBanner=bannerService.queryBannerByType(banner.getType());
        if(!ObjectUtils.isEmpty(oldBanner)){
			modelAndView.addObject("error","已经有该位置的Banner,无法添加");
			LOGGER.debug("The banner status is extis");
			modelAndView.setViewName("/banner/bannerAndModify");
			return modelAndView;
		}
		bannerService.createBanner(banner);
		modelAndView.setViewName("redirect:/banner/bannerList");
		return modelAndView;
	}

    @RequestMapping(value="/updateBanner/{bannerId}",method = RequestMethod.GET)
	public ModelAndView updateBanner(@PathVariable("bannerId") Integer bannerId, ModelAndView modelAndView) {

		Banner banner = bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is Empty");
			return modelAndView;
		}
		modelAndView.addObject("banner",banner);
		modelAndView.setViewName("/banner/bannerAndModify");
		return modelAndView;
	}

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
		bannerService.updateBanner(banner);
		modelAndView.setViewName("redirect:/banner/bannerList");
		return modelAndView;
	}

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


	@RequestMapping(value ="/queryBanner/{bannerId}",method=RequestMethod.GET)
	public ModelAndView queryBanner(@PathVariable("bannerId") Integer bannerId,ModelAndView modelAndView){

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is Empty");
			return modelAndView;
		}
		Banner banner =bannerService.queryBanner(bannerId);
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
