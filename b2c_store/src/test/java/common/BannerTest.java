package common;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.pgt.common.bean.Banner;
import com.pgt.common.service.BannerService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class BannerTest {

	@Autowired
	private BannerService bannerService;

	@Test
	public void createBanner() {

		for (int i = 0; i < 10; i++) {

			Banner banner = new Banner();

			banner.setImagePath("2323dfgdsgfvsdvsddvs");
			banner.setImageUrl("asdsadsa878394324232");
			banner.setStatus(23);
			banner.setType("code");

			int m = bannerService.createBanner(banner);

			System.out.println(m);
		}

	}

	@Test
	public void updateBanner() {

		Banner banner = new Banner();

		banner.setBannerId(2);
		banner.setImagePath("aaaa");
		banner.setImageUrl("bbb");
		banner.setStatus(33);
		banner.setType("ww");

		int m = bannerService.updateBanner(banner);

		System.out.println(m);

	}

	@Test
	public void deleteBanner() {

		for (int i = 3; i <= 20; i++) {

			int id = bannerService.deleteBanner(i);

			System.out.println(id);

		}
	}

	@Test
	public void queryAllBanner() {

		List<Banner> list = bannerService.queryAllBanner();

		String m = DigestUtils.md5Hex("ddjunshi" + "aa");
		
		System.out.println(m);

		System.out.println("debug Test");
	}

	@Test
	public void queryBanner() {

		bannerService.queryBanner(1);
		System.out.println("debug Test");
	}

	public BannerService getBannerService() {
		return bannerService;
	}

	public void setBannerService(BannerService bannerService) {
		this.bannerService = bannerService;
	}
}
