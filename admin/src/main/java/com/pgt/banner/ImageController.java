package com.pgt.banner;

import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.pgt.common.bean.Image;
import com.pgt.common.service.ImageService;
import com.pgt.configuration.URLConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/bannerImage")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private BannerService bannerService;

	@Autowired
	private URLConfiguration urlConfiguration;

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	@RequestMapping(value="/createImageUI/{bannerId}",method = RequestMethod.GET)
	public ModelAndView createImageUI(@PathVariable("bannerId") Integer bannerId, ModelAndView modelAndView) {

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty for createBannerImage");
			modelAndView.setViewName("redirect:/banner/bannerList");
			return modelAndView;
		}
		Banner banner= bannerService.queryBanner(bannerId);
        if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is empty for createBannerImage");
			modelAndView.setViewName("redirect:/banner/bannerList");
			return modelAndView;
		}
		modelAndView.addObject("banner",banner);
		modelAndView.setViewName("/banner/bannerImageAddAndModify");
		return modelAndView;
	}


	@RequestMapping(value="/createIamge",method = RequestMethod.POST)
	public ModelAndView createIamge(Image image, ModelAndView modelAndView, Integer bannerId) {

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		if(ObjectUtils.isEmpty(image.getPath())){
			LOGGER.debug("The imagePath is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}
		if(ObjectUtils.isEmpty(image.getLocation())){
			LOGGER.debug("The location is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		if(ObjectUtils.isEmpty(image.getUrl())){
			LOGGER.debug("The location is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		Banner banner =bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("banner is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}
		image.setBanner(banner);
		imageService.createImage(image);
		modelAndView.setViewName("redirect:/banner/queryBanner?bannerId="+banner.getBannerId());
		return modelAndView;
	}

	@RequestMapping(value="/updateUI/{bannerId}/{imageId}",method = RequestMethod.GET)
	public ModelAndView updateUI(@PathVariable("bannerId") Integer bannerId,@PathVariable("imageId")Integer imageId, ModelAndView modelAndView) {

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty for createBannerImage");
			modelAndView.setViewName("redirect:/banner/bannerList");
			return modelAndView;
		}
		Banner banner= bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("The banner is empty for createBannerImage");
			modelAndView.setViewName("redirect:/banner/bannerList");
			return modelAndView;
		}

		Image image = imageService.queryImageById(imageId);
		if(ObjectUtils.isEmpty(image)){
			LOGGER.debug("The image is empty");
			return modelAndView;
		}
		modelAndView.addObject("banner",banner);
		modelAndView.addObject("image",image);
		modelAndView.setViewName("/banner/bannerImageAddAndModify");
		return modelAndView;
	}

	@RequestMapping(value="/updateImage",method = RequestMethod.POST)
	public ModelAndView updateImage(ModelAndView modelAndView, Image image,Integer bannerId) {

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		if(ObjectUtils.isEmpty(image.getPath())){
			LOGGER.debug("The imagePath is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}
		if(ObjectUtils.isEmpty(image.getLocation())){
			LOGGER.debug("The location is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		if(ObjectUtils.isEmpty(image.getUrl())){
			LOGGER.debug("The location is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
			return modelAndView;
		}

		Banner banner =bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("banner is empty");
			modelAndView.setViewName("/banner/bannerImageAddAndModify");
		}
		image.setBanner(banner);
		imageService.updateImage(image);
		modelAndView.setViewName("redirect:/banner/queryBanner?bannerId="+banner.getBannerId());
		return modelAndView;
	}

	@RequestMapping(value="/deleteImage/{bannerId}/{imageId}",method = RequestMethod.GET)
	public ModelAndView deleteImage(@PathVariable("bannerId") Integer bannerId,@PathVariable("imageId") Integer imageId,ModelAndView modelAndView) {

		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty");
			modelAndView.setViewName("redirect:/banner/queryBanner/"+bannerId);
			return modelAndView;
		}
		if(StringUtils.isEmpty(imageId)){
			LOGGER.debug("The imageId is null");
			modelAndView.setViewName("redirect:/banner/queryBanner/"+bannerId);
			return modelAndView;
		}
		imageService.deleteImageById(imageId);
		modelAndView.setViewName("redirect:/banner/queryBanner?bannerId="+bannerId);
		return modelAndView;
	}


	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	public URLConfiguration getUrlConfiguration() {
		return urlConfiguration;
	}

	public void setUrlConfiguration(URLConfiguration urlConfiguration) {
		this.urlConfiguration = urlConfiguration;
	}

}
