package com.pgt.communication.service;

import java.util.List;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;

/**
 * 
 * Created by ddjunshi 2015年11月19日
 */
public interface DiscussService {

	// 创建一个讨论
	Integer createDiscuss(Discuss discuss);

	// 修改显示状态
	Integer updateIsShow(Boolean isShow, Integer id);

	// 根据id删除一个讨论
	void deleteDiscussById(Integer id);

	// 批量删除id
	void deleteDiscussByKes(Integer[] ids);

	// 根据id查询一个讨论
	Discuss queryDiscussById(Integer id);

	// 查询所有的讨论列表
	/**
	 * @param discussCustom   paginationBean属性封装分页信息
	 * @return
	 */
	List<Discuss> queryAllDiscuss(DiscussCustom discussCustom);

	//查询素有的讨论列表的总记录数
	Integer queryAllDiscussCount();
	
	// 查询某个商品下的讨论列表
	List<Discuss> queryProductAllDiscuss(Integer productId,DiscussCustom discussCustom);

    //查询某个商品下面的总记录数
	Integer queryProductAllDiscussCount(Integer productId);
	
}
