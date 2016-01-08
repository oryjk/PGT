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
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private BannerService bannerService;

	@Autowired
	private URLConfiguration urlConfiguration;

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);

	@RequestMapping(value="/createImageUI",method = RequestMethod.GET)
	public ModelAndView createImageUI(Integer bannerId, ModelAndView modelAndView) {

		modelAndView.setViewName("");
		return modelAndView;
	}


	@RequestMapping(value="/createIamge",method = RequestMethod.POST)
	public ModelAndView createIamge(@Validated Image image, ModelAndView modelAndView, BindingResult bindingResult,Integer bannerId) {

		if(bindingResult.hasErrors()){
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			modelAndView.addObject("errors",errors);
			LOGGER.debug("create image has some errors");
			return modelAndView;
		}
		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty");
		}
		Banner banner =bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("banner is empty");
		}
		image.setBanner(banner);
		imageService.createImage(image);

		modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/updateUI",method = RequestMethod.GET)
	public ModelAndView updateUI(Integer imageId, ModelAndView modelAndView) {

		Image image = imageService.queryImageById(imageId);
		if(ObjectUtils.isEmpty(image)){
			LOGGER.debug("The image is empty");
			return modelAndView;
		}
		modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/updateImage",method = RequestMethod.POST)
	public ModelAndView updateImage(ModelAndView modelAndView,@Validated Image image,BindingResult bindingResult,Integer bannerId) {

		if(bindingResult.hasErrors()){
			List<FieldError> fieldErrors = bindingResult.getFieldErrors();
			Map<String, String> errors = new HashMap<String, String>();
			for (FieldError err : fieldErrors) {
				errors.put(err.getField(), err.getDefaultMessage());
			}
			modelAndView.addObject("errors",errors);
			LOGGER.debug("update image has some errors");
			return modelAndView;
		}
		if(ObjectUtils.isEmpty(bannerId)){
			LOGGER.debug("The bannerId is empty");
		}
		Banner banner =bannerService.queryBanner(bannerId);
		if(ObjectUtils.isEmpty(banner)){
			LOGGER.debug("banner is empty");
		}
		image.setBanner(banner);
		imageService.updateImage(image);
		return modelAndView;
	}

	@RequestMapping(value="/deleteImage",method = RequestMethod.GET)
	public ModelAndView deleteImage(Integer imageId,ModelAndView modelAndView) {

		if(StringUtils.isEmpty(imageId)){
			LOGGER.debug("The imageId is null");
			return modelAndView;
		}
		imageService.deleteImageById(imageId);
		modelAndView.setViewName("");
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
