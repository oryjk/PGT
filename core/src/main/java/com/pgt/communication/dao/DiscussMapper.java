package com.pgt.communication.dao;

import java.util.List;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;
import com.pgt.utils.PaginationBean;

/**
 * Created by ddjunshi 2015年11月17日
 */
public interface DiscussMapper extends SqlMapper {

	// 创建一个讨论
	Integer createDiscuss(Discuss discuss);

	// 修改显示状态
	Integer updateIsShow(Discuss discuss);

	// 根据id删除一个讨论
	void deleteDiscussById(Integer id);

	// 批量删除id
	void deleteDiscussByKes(Integer[] ids);

	// 根据id查询一个讨论
	Discuss queryDiscussById(Integer id);

	// 查询所有的讨论列表
	List<Discuss> queryAllDiscuss(DiscussCustom discussCustom);

	// 查询所有讨论列表的总记录数查询
	Integer queryAllDiscussCount();

	// 查询某个商品下的讨论列表
	List<Discuss> queryProductAllDiscuss(DiscussCustom discussCustom);

	// 查询某个商品下面的所有的总记录数
	Integer queryProductAllDiscussCount(DiscussCustom discussCustom);

}
