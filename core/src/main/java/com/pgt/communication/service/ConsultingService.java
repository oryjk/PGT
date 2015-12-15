package com.pgt.communication.service;

import java.util.List;

import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;

/**
 * Created by ddjunshi 2015年11月17日
 */

public interface ConsultingService {

	/**
	 * @param consulting
	 *            封装咨询信息
	 * @return id
	 */
	Integer createConsulting(Consulting consulting);

	/**
	 * 修改显示状态
	 * 
	 * @param isShow
	 */
	void updateIsShow(Boolean isShow, Integer id);

	// 根据id删除一个咨询
	void deleteConsultingById(Integer id);

	// 根据id查询一个咨询
	Consulting queryConsulting(Integer id);

	/**
	 * 查询所有的咨询列表
	 * 
	 * @param consultingCustom(可以携带查询条件)
	 *            封装了分页的信息
	 * @return
	 */
	List<Consulting> queryAllConsulting(ConsultingCustom consultingCustom);

	// 查询所有咨询的总记录数
	Integer queryAllConsultingCount(ConsultingCustom consultingCustom);

	// 查询某个用户的咨询列表
	List<Consulting> queryUserAllConsulting(Long userId);

	// 查询某个商品下的咨询列表
	List<Consulting> queryAllConsultingByProduct(Integer productId, ConsultingCustom consultingCustom);

	// 查询某个商品下面咨询的总记录数
	Integer queryAllConsultingByProductCount(Integer productId, ConsultingCustom consultingCustom);

	/**
	 * 创建回复的内容
	 * 
	 * @param consulting
	 */
	void replyConsulting(Consulting consulting, Consulting replyConsulting);

	/**
	 * 批量删除
	 * 
	 * @param ids
	 *            接收要删除咨询id的数组集合
	 */
	void deleteConsultingByKes(Integer[] ids);

}
