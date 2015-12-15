package com.pgt.common.controller;

import java.util.List;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import com.pgt.common.bean.Image;
import com.pgt.common.bean.ImageCustom;
import com.pgt.common.service.ImageService;
import com.pgt.configuration.URLConfiguration;

public class ImageController {

	private ImageService imageService;

	private URLConfiguration urlConfiguration;

	// 1.进入添加图片的页面，需要携带banner的id
	public String createImageUI(Integer bannerId, Model model) {

		
		return null;
	}

	// 2.提交添加图片的操作，需要处理图片上传
	public ModelAndView createIamge(Image image, ModelAndView modelAndView, String path) {

		image.setPath(path);

		imageService.createImage(image);

		modelAndView.setViewName("");

		return modelAndView;
	}

	// 3.提供修改banner的界面,回显
	public String updateUI(Integer imageId, Model model) {

		Image image = imageService.queryImageById(imageId);

		model.addAttribute("image", image);

		return "";
	}

	// 4.提交修改图片的操作，需要图片上传
	public ModelAndView updateImage(ModelAndView modelAndView) {

		return modelAndView;
	}

	// 5.删除一个轮播的展示图片
	public String deleteIamge(Integer imageId) {

		imageService.deleteImageById(imageId);

		return "";
	}

	// 6.进入查看图片的列表页面
	public ModelAndView imageList(ModelAndView modelAndView) {

		ImageCustom imageCustom = new ImageCustom();

		List<Image> images = imageService.queryAllImage(imageCustom);

		modelAndView.addObject("images", images);

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
