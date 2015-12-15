package communication;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.bean.DiscussCustom;
import com.pgt.communication.service.DiscussService;
import com.pgt.product.bean.Product;
import com.pgt.user.bean.User;
import com.pgt.utils.PaginationBean;

/**
 * 
 * Created by ddjunshi 2015年11月19日
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class DiscussTest {

	@Autowired
	private DiscussService discussService;

	public DiscussService getDiscussService() {

		return discussService;
	}

	public void setDiscussService(DiscussService discussService) {

		this.discussService = discussService;
	}

	@Test
	public void testCreateDiscuss() {

		for (int i = 0; i < 10; i++) {

			Long uerId = 1L;

			Integer productId = 1;

			User user = new User();
			user.setId(uerId);

			Product product = new Product();
			product.setProductId(productId);

			Discuss discuss = new Discuss();

			discuss.setContent("很好啊，这个宝贝好");
			discuss.setIp("bbbbbbbbbbbbbbb");
			discuss.setProduct(product);
			discuss.setUser(user);

			discussService.createDiscuss(discuss);

		}

	}

	@Test
	public void testUpdateIsShow() {

		discussService.updateIsShow(true, 1);
	}

	@Test
	public void testDeleteByid() {

		// discussService.deleteDiscussById(1);

		// 测试批量删除
		Integer[] ids = { 2, 3 };

		discussService.deleteDiscussByKes(ids);
	}

	@Test
	public void testQueryById() {

		Discuss discuss = discussService.queryDiscussById(1);

		System.out.println(discuss);

	}

	@Test
	public void queryAllDiscuss() {

		int total = discussService.queryAllDiscussCount();

		PaginationBean page = new PaginationBean();
		page.setTotalAmount(total);
		page.setCurrentIndex(5);
		page.setCapacity(5);

		DiscussCustom discussCustom = new DiscussCustom();

		discussCustom.setPaginationBean(page);

		List<Discuss> list = discussService.queryAllDiscuss(discussCustom);

		System.out.println(list);
	}

	@Test
	public void testAllDiscussByProduct() {

		int total = discussService.queryProductAllDiscussCount(1);

		PaginationBean page = new PaginationBean();
		page.setTotalAmount(total);
		page.setCurrentIndex(5);
		page.setCapacity(5);

		DiscussCustom discussCustom = new DiscussCustom();
		discussCustom.setPaginationBean(page);

		List<Discuss> list = discussService.queryProductAllDiscuss(1, discussCustom);

		System.out.println(list);

	}

}
