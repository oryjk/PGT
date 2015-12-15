package com.pgt.common.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pgt.common.bean.Image;
import com.pgt.common.bean.ImageCustom;
import com.pgt.common.dao.ImageMapper;

@Service
@Transactional
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageMapper imageMapper;

	@Override
	public Integer createImage(Image image) {

		return imageMapper.createImage(image);
	}

	@Override
	public List<Image> queryAllImageByLocation(ImageCustom imageCustom) {

		return imageMapper.queryAllImageByLocation(imageCustom);
	}

	@Override
	public Integer updateImage(Image image) {

		return imageMapper.updateImage(image);
	}

	@Override
	public void deleteImageById(Integer imageId) {

		imageMapper.deleteImageById(imageId);
	}

	@Override
	public void deleteImageByKes(Integer[] ids) {

		imageMapper.deleteImageByKes(ids);
	}

	@Override
	public Image queryImageById(Integer imageId) {

		return imageMapper.queryImageById(imageId);
	}

	@Override
	public List<Image> queryAllImage(ImageCustom imageCustom) {

		return imageMapper.queryAllImage(imageCustom);
	}

	@Override
	public Integer queryAllImageCount(ImageCustom imageCustom) {

		return imageMapper.queryAllImageCount(imageCustom);
	}

	public ImageMapper getImageMapper() {
		return imageMapper;
	}

	public void setImageMapper(ImageMapper imageMapper) {
		this.imageMapper = imageMapper;
	}

}
