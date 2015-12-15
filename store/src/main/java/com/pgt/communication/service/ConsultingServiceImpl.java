package com.pgt.communication.service;

import java.util.Date;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.dao.ConsultingMapper;
import com.pgt.page.service.StaticPageServiceImpl;
import com.pgt.product.bean.Product;
import com.pgt.product.dao.ProductMapper;

/**
 * Created by ddjunshi 2015年11月17日
 */

@Service
@Transactional
public class ConsultingServiceImpl implements ConsultingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultingServiceImpl.class);

	@Autowired
	private ConsultingMapper consultingMapper;

	@Autowired
	private ProductMapper productMapper;

	public ConsultingMapper getConsultingMapper() {
		return consultingMapper;
	}

	public void setConsultingMapper(ConsultingMapper consultingMapper) {
		this.consultingMapper = consultingMapper;
	}

	public ProductMapper getProductMapper() {
		return productMapper;
	}

	public void setProductMapper(ProductMapper productMapper) {
		this.productMapper = productMapper;
	}

	@Override
	public List<Consulting> queryAllConsulting(ConsultingCustom consultingCustom) {

		return consultingMapper.queryAllConsulting(consultingCustom);
	}

	@Override
	public Integer queryAllConsultingCount(ConsultingCustom consultingCustom) {

		return consultingMapper.queryAllConsultingCount(consultingCustom);
	}

	@Override
	public List<Consulting> queryAllConsultingByProduct(Integer productId, ConsultingCustom consultingCustom) {

		if (consultingCustom == null) {

			return null;
		}

		Product product = productMapper.queryProduct(productId);

		if (product == null) {
			return null;
		}

		consultingCustom.setProduct(product);

		return consultingMapper.queryAllConsultingByProduct(consultingCustom);
	}

	@Override
	public Integer queryAllConsultingByProductCount(Integer productId, ConsultingCustom consultingCustom) {

		if (consultingCustom == null) {

			return null;
		}

		Product product = productMapper.queryProduct(productId);

		if (product == null) {
			return null;
		}

		consultingCustom.setProduct(product);

		return consultingMapper.queryAllConsultingByProductCount(consultingCustom);

	}

	public Integer createConsulting(Consulting consulting) {

		// 创建时间
		Date date = new Date();

		consulting.setCreateDate(date);

		return consultingMapper.createConsulting(consulting);

	}

	@Override
	public void updateIsShow(Boolean isShow, Integer id) {

		if (isShow != null && id != null) {

			Consulting consulting = new Consulting();

			consulting.setIsShow(isShow);
			consulting.setId(id);

			consultingMapper.updateIsShow(consulting);

		}

	}

	@Override
	public void deleteConsultingById(Integer id) {

		consultingMapper.deleteConsultingById(id);
	}

	@Override
	public Consulting queryConsulting(Integer id) {

		return consultingMapper.queryConsulting(id);
	}

	@Override
	public List<Consulting> queryUserAllConsulting(Long userId) {

		return consultingMapper.queryUserAllConsulting(userId);
	}

	@Override
	public void replyConsulting(Consulting consulting, Consulting replyConsulting) {

		if (consulting == null || replyConsulting == null) {
			return;
		}

		consulting.setIsShow(true);

		// 修改咨询现实的状态
		consultingMapper.updateIsShow(consulting);

		replyConsulting.setCreateDate(new Date());
		replyConsulting.setIsShow(true);

		replyConsulting.setProduct(consulting.getProduct());

		replyConsulting.setParent(consulting);

		// 保存回复的信息
		consultingMapper.createConsulting(replyConsulting);
	}

	@Override
	public void deleteConsultingByKes(Integer[] ids) {

		consultingMapper.deleteConsultingByKes(ids);

	}

}
