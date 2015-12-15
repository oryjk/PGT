package page;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pgt.category.bean.Category;
import com.pgt.common.bean.Banner;
import com.pgt.page.bean.StaticPage;
import com.pgt.page.service.StaticPageService;

/**
 * Created by ddjunshi 2015年11月15日
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class TestStaticPage {

	@Autowired
	private StaticPageService staticPageService;

	@Test
	public void testCreateStaticPage() {

		StaticPage staticPage = new StaticPage();
		staticPage.setCss("aaaaaaaaaaaaaaaa");
		staticPage.setJs("bbbbbbbbbbbbbbbbb");
		staticPage.setStatus(1);
		staticPage.setName("哈哈");

		List<Category> categories = new ArrayList<Category>();

		
		
		for (Integer i = 1; i < 6; i++) {

			Category category = new Category();
			category.setId(i);
			categories.add(category);

		}
		staticPage.setCategories(categories);

		List<Banner> banners = new ArrayList<Banner>();
		for (int i = 1; i < 6; i++) {

			Banner banner = new Banner();
			banner.setBannerId(i);
			banners.add(banner);
		}
		staticPage.setBanners(banners);

		int m=staticPageService.cretaeStaticPage(staticPage);

		System.out.println(m);
	}

	@Test
	public void testUpdateStaticPage() {

		StaticPage staticPage = new StaticPage();
		staticPage.setStaticPageId(4);
		staticPage.setCss("cccccccccccccccc");
		staticPage.setJs("sssssssssssss");
		staticPage.setStatus(2);
		staticPage.setName("呵呵");

		List<Category> categories = new ArrayList<Category>();

		for (Integer i = 3; i < 6; i++) {

			Category category = new Category();
			category.setId(i);
			categories.add(category);

		}
		staticPage.setCategories(categories);

		List<Banner> banners = new ArrayList<Banner>();
		for (int i = 3; i < 6; i++) {

			Banner banner = new Banner();
			banner.setBannerId(i);
			banners.add(banner);
		}
		staticPage.setBanners(banners);

		staticPageService.updateStaticPage(staticPage);

	}

	@Test
	public void useStaticPage() {

		StaticPage staticPage = staticPageService.useStaticPageById(4);
		System.out.println(staticPage);

	}

	@Test
	public void testqueryAll() {

		//List<StaticPage> list = staticPageService.queryAllStaticPage();

		staticPageService.deleteStaticPageById(4);
	}

	public StaticPageService getStaticPageService() {
		return staticPageService;
	}

	public void setStaticPageService(StaticPageService staticPageService) {
		this.staticPageService = staticPageService;
	}

}
