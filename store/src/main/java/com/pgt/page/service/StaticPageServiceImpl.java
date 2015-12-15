package com.pgt.page.service;

import java.util.ArrayList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.pgt.base.service.TransactionService;
import com.pgt.category.bean.Category;
import com.pgt.category.dao.CategoryMapper;
import com.pgt.common.bean.Banner;
import com.pgt.common.dao.BannerMapper;
import com.pgt.page.bean.StaticPage;
import com.pgt.page.bean.StaticPageBannerRelation;
import com.pgt.page.bean.StaticPageCategoryRelation;
import com.pgt.page.dao.StaticPageMapper;

/**
 * Created by ddjunshi 2015年11月14日
 */

@Service
public class StaticPageServiceImpl extends TransactionService implements StaticPageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StaticPageServiceImpl.class);

	@Autowired
	private StaticPageMapper staticPageMapper;

	@Autowired
	private BannerMapper bannerMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Override
	public Integer cretaeStaticPage(StaticPage staticPage) {

		TransactionStatus transactionStatus = ensureTransaction();
		try {

			// 1.Save the js,css path
			staticPageMapper.createStaticPage(staticPage);

			Integer staticPageId = staticPage.getStaticPageId();

			List<Banner> banners = staticPage.getBanners();

			// 2.save the bannerId and staticPageId in staticpage_banner
			for (Banner banner : banners) {

				StaticPageBannerRelation sBannerRelation = new StaticPageBannerRelation();

				sBannerRelation.setBannerId(banner.getBannerId());
				sBannerRelation.setStaticPageId(staticPageId);

				staticPageMapper.createStaticPageBannerRelation(sBannerRelation);

			}

			List<Category> categories = staticPage.getCategories();

			// save the categoryId and staticPageId in staticpage_category
			for (Category category : categories) {

				StaticPageCategoryRelation sCategoryRelation = new StaticPageCategoryRelation();

				Integer categoryId = category.getId().intValue();
				sCategoryRelation.setCategoryId(categoryId);
				sCategoryRelation.setStaticPageId(staticPageId);

				staticPageMapper.createStaticPageCategoryRelation(sCategoryRelation);

			}

			getTransactionManager().commit(transactionStatus);
		} catch (Exception e) {

			LOGGER.error("Some thing wrong when create a staticPage and id=", staticPage.getStaticPageId());
			getTransactionManager().rollback(transactionStatus);

		}

		return staticPage.getStaticPageId();
	}

	@Override
	public Integer updateStaticPage(StaticPage staticPage) {

		TransactionStatus transactionStatus = ensureTransaction();
		try {

			StaticPage staticPage_db = staticPageMapper.queryStaticPage(staticPage.getStaticPageId());

			Integer staticPageId = staticPage.getStaticPageId();

			if (staticPage_db != null) {

				staticPageMapper.updateStaticPage(staticPage);

				// 1.delete relation
				staticPageMapper.deleteStaticPageBannerRelation(staticPageId);
				staticPageMapper.deleteStaticPageCategoryRelation(staticPageId);

				List<Banner> banners = staticPage.getBanners();

				// 2.save the bannerId and staticPageId in staticpage_banner
				for (Banner banner : banners) {

					StaticPageBannerRelation sBannerRelation = new StaticPageBannerRelation();

					sBannerRelation.setBannerId(banner.getBannerId());
					sBannerRelation.setStaticPageId(staticPageId);

					staticPageMapper.createStaticPageBannerRelation(sBannerRelation);

				}

				List<Category> categories = staticPage.getCategories();

				// save the categoryId and staticPageId in staticpage_category
				for (Category category : categories) {

					StaticPageCategoryRelation sCategoryRelation = new StaticPageCategoryRelation();

					Integer categoryId = category.getId().intValue();
					sCategoryRelation.setCategoryId(categoryId);
					sCategoryRelation.setStaticPageId(staticPageId);

					staticPageMapper.createStaticPageCategoryRelation(sCategoryRelation);
				}
			}

			getTransactionManager().commit(transactionStatus);
		} catch (Exception e) {

			LOGGER.error("Some thing wrong when Update a staticPage and id=", staticPage.getStaticPageId());
			getTransactionManager().rollback(transactionStatus);

		}

		return staticPage.getStaticPageId();
	}

	@Override
	public StaticPage useStaticPageById(Integer staticPageId) {

		StaticPage staticPage = staticPageMapper.queryStaticPage(staticPageId);

		List<StaticPageBannerRelation> sBannerRelations = staticPageMapper.queryStaticPageBannerRelation(staticPageId);

		List<Banner> banners = new ArrayList<Banner>();

		for (StaticPageBannerRelation s : sBannerRelations) {

			Integer bannerId = s.getBannerId();
			Banner banner = bannerMapper.queryBanner(bannerId);
			banners.add(banner);
		}

		staticPage.setBanners(banners);

		List<Category> categories = new ArrayList<Category>();

		List<StaticPageCategoryRelation> sCategoryRelations = staticPageMapper
				.queryStaticPageCategoryRelation(staticPageId);

		for (StaticPageCategoryRelation s : sCategoryRelations) {

			Integer categoryId = s.getCategoryId();
			Category category = categoryMapper.queryCategory(categoryId);
			categories.add(category);
		}

		staticPage.setCategories(categories);

		return staticPage;
	}

	@Override
	public Integer deleteStaticPageById(Integer staticPageId) {

		TransactionStatus transactionStatus = ensureTransaction();
		try {

			staticPageMapper.deleteStaticPageById(staticPageId);
			staticPageMapper.deleteStaticPageBannerRelation(staticPageId);
			staticPageMapper.deleteStaticPageCategoryRelation(staticPageId);

			getTransactionManager().commit(transactionStatus);
		} catch (Exception e) {
			LOGGER.error("Some thing wrong when delete a staticPage and id=", staticPageId);
			getTransactionManager().rollback(transactionStatus);
		}

		return staticPageId;
	}

	@Override
	public List<StaticPage> queryAllStaticPage() {

		return staticPageMapper.queryAllStaticPage();
	}

	public StaticPageMapper getStaticPageMapper() {
		return staticPageMapper;
	}

	public void setStaticPageMapper(StaticPageMapper staticPageMapper) {
		this.staticPageMapper = staticPageMapper;
	}

	public BannerMapper getBannerMapper() {
		return bannerMapper;
	}

	public void setBannerMapper(BannerMapper bannerMapper) {
		this.bannerMapper = bannerMapper;
	}

	public CategoryMapper getCategoryMapper() {
		return categoryMapper;
	}

	public void setCategoryMapper(CategoryMapper categoryMapper) {
		this.categoryMapper = categoryMapper;
	}

}
