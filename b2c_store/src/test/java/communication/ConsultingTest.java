package communication;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.pgt.communication.bean.Consulting;
import com.pgt.communication.bean.ConsultingCustom;
import com.pgt.communication.service.ConsultingService;
import com.pgt.product.bean.Product;
import com.pgt.user.bean.User;
import com.pgt.utils.PaginationBean;

/**
 * Created by ddjunshi 2015年11月18日
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ConsultingTest {

	@Autowired
	private ConsultingService consultingService;

	@Test
	public void testCreateConsulting() {

		for (int i = 0; i < 30; i++) {

			Long uerId = 1L;

			Integer productId = 1;

			User user = new User();
			user.setId(uerId);

			Product product = new Product();
			product.setProductId(productId);

			Consulting consulting = new Consulting();

			consulting.setUser(user);
			consulting.setProduct(product);
			consulting.setContent("好的");
			consulting.setIp("aaaaaaaaaaaaaaaa");
			consulting.setIsShow(true);

			consultingService.createConsulting(consulting);

		}

	}

	@Test
	public void testUpdateIsShow() {

		consultingService.updateIsShow(false, 1);

	}

	@Test
	public void testDeleteByid() {

		consultingService.deleteConsultingById(1);

	}

	@Test
	public void testQueryById() {

		Consulting consulting = consultingService.queryConsulting(2);

		System.out.println(consulting);

	}

	@Test
	public void testQueryAllConsulting() {

		ConsultingCustom consultingCustom = new ConsultingCustom();

		int total = consultingService.queryAllConsultingCount(consultingCustom);

		PaginationBean page = new PaginationBean();
		page.setTotalAmount(total);
		page.setCurrentIndex(5);
		page.setCapacity(5);

		consultingCustom.setPaginationBean(page);

		List<Consulting> list = consultingService.queryAllConsulting(consultingCustom);

		System.out.println(list);

	}

	// 查询某个用户下的咨询列表
	@Test
	public void testQueryByUser() {

		List<Consulting> list = consultingService.queryUserAllConsulting(1L);

		System.out.println(list);

	}

	// 查询某个商品下的咨询列表

	@Test
	public void testQueryByProduct() {

		ConsultingCustom consultingCustom = new ConsultingCustom();

		int total = consultingService.queryAllConsultingByProductCount(1, consultingCustom);

		PaginationBean page = new PaginationBean();
		page.setTotalAmount(total);
		page.setCurrentIndex(0);
		page.setCapacity(5);

		consultingCustom.setPaginationBean(page);

		List<Consulting> list = consultingService.queryAllConsultingByProduct(1, consultingCustom);

		System.out.println(list);

	}

	@Test
	public void testeReply() {

		Long uerId = 1L;

		Integer productId = 1;

		User user = new User();
		user.setId(uerId);

		Product product = new Product();
		product.setProductId(productId);

		Consulting consulting = new Consulting();

		consulting.setId(2);
		consulting.setUser(user);
		consulting.setProduct(product);
		consulting.setContent("好的");
		consulting.setIp("aaaaaaaaaaaaaaaa");

		// 测试回复
		Consulting replyConsulting = new Consulting();
		replyConsulting.setUser(user);
		replyConsulting.setIp("bbbbbbbbbbbbb");
		replyConsulting.setContent("亲，你好，我回复你的咨询");

		// 3
		consultingService.replyConsulting(consulting, replyConsulting);

	}

	@Test
	public void testeDelete() {

		Integer[] ids = { 2, 3, 4, 5 };

		consultingService.deleteConsultingByKes(ids);

	}

	public ConsultingService getConsultingService() {
		return consultingService;
	}

	public void setConsultingService(ConsultingService consultingService) {
		this.consultingService = consultingService;
	}

}
