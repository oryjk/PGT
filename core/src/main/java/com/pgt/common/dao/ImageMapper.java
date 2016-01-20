package com.pgt.common.dao;

import java.util.List;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.common.bean.Image;
import com.pgt.common.bean.ImageCustom;

/**
 * 
 * Created by ddjunshi 2015年11月20日
 */
public interface ImageMapper extends SqlMapper {

	/**
	 * 创建一个banner一个展示图片
	 * 
	 * @param image
	 * @return
	 */
	Integer createImage(Image image);

	/**
	 * 修改banner的一个展示图片
	 * 
	 * @param image
	 * @return
	 */
	Integer updateImage(Image image);

	/**
	 * 删除一张展示图片
	 * 
	 * @param imageId
	 */
	void deleteImageById(Integer imageId);

	/**
	 * 批量删除展示的图片
	 */
	void deleteImageByKes(Integer[] ids);

	/**
	 * @param imageId
	 *            封装image的信息
	 * @return
	 */
	Image queryImageById(Integer imageId);

	/**
	 * 查询所有的展示图片信息
	 * 
	 * @param imageCustom
	 * @return
	 */
	List<Image> queryAllImage(ImageCustom imageCustom);

	/**
	 * 查询总记录数
	 * @param imageCustom
	 * @return
	 */
	Integer queryAllImageCount(ImageCustom imageCustom);

	/**
	 * 查询在一个位置的图片交给页面显示
	 * @param imageCustom
	 * @return
	 */
	List<Image> queryAllImageByLocation(ImageCustom imageCustom);

	List<Image> queryImageByBanner(ImageCustom imageCustom);

	Integer queryImageByBannerCount(Integer bannerId);
	
	
}
