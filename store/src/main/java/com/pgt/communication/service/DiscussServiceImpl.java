package com.pgt.communication.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;
import com.pgt.communication.dao.DiscussMapper;
import com.pgt.product.bean.Product;
import com.pgt.product.dao.ProductMapper;

/**
 * Created by ddjunshi 2015年11月19日
 */

@Transactional
@Service
public class DiscussServiceImpl implements DiscussService {

	@Autowired
	private DiscussMapper discussMapper;

	@Autowired
	private ProductMapper productMapper;

	public ProductMapper getProductMapper() {
		return productMapper;
	}

	public void setProductMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public Integer createDiscuss(Discuss discuss) {

		if (discuss == null) {

			return null;
		}

		discuss.setCreateDate(new Date());

		return discussMapper.createDiscuss(discuss);
	}

	@Override
	public Integer updateIsShow(Boolean isShow, Integer id) {

		if (isShow == null || id == null) {

			return null;
		}

		Discuss discuss = new Discuss();

		discuss.setIsShow(isShow);

		discuss.setId(id);

		return discussMapper.updateIsShow(discuss);

	}

	@Override
	public void deleteDiscussById(Integer id) {

		discussMapper.deleteDiscussById(id);

	}

	@Override
	public void deleteDiscussByKes(Integer[] ids) {

		discussMapper.deleteDiscussByKes(ids);
	}

	@Override
	public Discuss queryDiscussById(Integer id) {

		return discussMapper.queryDiscussById(id);
	}

	@Override
	public List<Discuss> queryAllDiscuss(DiscussCustom discussCustom) {

		return discussMapper.queryAllDiscuss(discussCustom);
	}

	@Override
	public List<Discuss> queryProductAllDiscuss(Integer productId, DiscussCustom discussCustom) {

		// 查询该商品，看是否存在
		Product product = productMapper.queryProduct(productId);

		if (product == null) {

			return null;
		}

		discussCustom.setProduct(product);

		return discussMapper.queryProductAllDiscuss(discussCustom);
	}

	@Override
	public Integer queryProductAllDiscussCount(Integer productId) {

		// 查询该商品，看是否存在
		Product product = productMapper.queryProduct(productId);

		if (product == null) {

			return null;
		}

		DiscussCustom discussCustom = new DiscussCustom();

		discussCustom.setProduct(product);

		return discussMapper.queryProductAllDiscussCount(discussCustom);
	}

	@Override
	public Integer queryAllDiscussCount() {

		return discussMapper.queryAllDiscussCount();
	}

	public DiscussMapper getDiscussMapper() {
		return discussMapper;
	}

	public void setDiscussMapper(DiscussMapper discussMapper) {
		this.discussMapper = discussMapper;
	}

}
