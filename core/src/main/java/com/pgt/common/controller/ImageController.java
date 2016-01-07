package com.pgt.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.pgt.common.bean.Image;
import com.pgt.common.service.ImageService;
import com.pgt.configuration.URLConfiguration;


@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@Autowired
	private URLConfiguration urlConfiguration;


	@RequestMapping(value="/createImageUI",method = RequestMethod.GET)
	public ModelAndView createImageUI(Integer bannerId, ModelAndView modelAndView) {

		return modelAndView;
	}


	@RequestMapping(value="/createIamge",method = RequestMethod.POST)
	public ModelAndView createIamge(Image image, ModelAndView modelAndView) {
		if(StringUtils.isEmpty(image.getPath())){
			return modelAndView;
		}
		if(StringUtils.isEmpty(image.getUrl())){
			return modelAndView;
		}

		imageService.createImage(image);
		modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/updateUI",method = RequestMethod.GET)
	public ModelAndView updateUI(Integer imageId, ModelAndView modelAndView) {

		Image image = imageService.queryImageById(imageId);
		modelAndView.setViewName("");
		return modelAndView;
	}

	@RequestMapping(value="/updateImage",method = RequestMethod.POST)
	public ModelAndView updateImage(ModelAndView modelAndView,Image image) {

		if(StringUtils.isEmpty(image.getPath())){
			return modelAndView;
		}
		if(StringUtils.isEmpty(image.getUrl())){
			return modelAndView;
		}
		imageService.updateImage(image);
		return modelAndView;
	}

	@RequestMapping(value="/deleteImage",method = RequestMethod.GET)
	public ModelAndView deleteImage(Integer imageId,ModelAndView modelAndView) {

		if(StringUtils.isEmpty(imageId)){
			return modelAndView;
		}
		imageService.deleteImageById(imageId);
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
