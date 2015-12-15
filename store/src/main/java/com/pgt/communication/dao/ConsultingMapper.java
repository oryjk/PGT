package com.pgt.communication.dao;

import java.util.List;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;

/**
 * 
 * Created by ddjunshi 2015年11月17日
 */
public interface ConsultingMapper extends SqlMapper {

	// 创建一个咨询或回复
	Integer createConsulting(Consulting consulting);

	// 修改显示状态
	Integer updateIsShow(Consulting consulting);

	// 根据id删除一个咨询
	void deleteConsultingById(Integer id);

	// 批量删除id
	void deleteConsultingByKes(Integer[] ids);

	// 根据id查询一个咨询
	Consulting queryConsulting(Integer id);

	// 查询所有的咨询列表 带分页
	List<Consulting> queryAllConsulting(ConsultingCustom consultingCustom);

	// 查询所有咨询的总记录数
	Integer queryAllConsultingCount(ConsultingCustom consultingCustom);

	// 查询某个用户的咨询列表
	List<Consulting> queryUserAllConsulting(Long userId);

	// 查询某个商品下的咨询列表
	List<Consulting> queryAllConsultingByProduct(ConsultingCustom consultingCustom);

	// 查询某个商品下面咨询的总记录数
	Integer queryAllConsultingByProductCount(ConsultingCustom consultingCustom);

}
