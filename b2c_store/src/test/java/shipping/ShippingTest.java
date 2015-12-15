package shipping;
import java.util.Date;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pgt.shipping.bean.ShippingAddress;
import com.pgt.shipping.bean.ShippingVO;
import com.pgt.shipping.service.ShippingService;

/**
 * Created by ethanli
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ShippingTest {

	@Autowired
	private ShippingService shippingService;

	@Test
	public void testAddAddress() {
		ShippingVO shippingvo = new ShippingVO();
		shippingvo.setOrderId("testO0001");
		shippingvo.setStatus("status1");
		shippingvo.setTrackingNumber("track001");
		shippingvo.setUpdateDate(new Date());
		shippingvo.setAmount(2.1D);
		ShippingAddress address = new ShippingAddress();
		address.setCity("city1");
		address.setDistrict("district1");
		address.setName("name1");
		address.setPhone("110110110");
		address.setProvince("province1");
		address.setStatus(1);
//		shippingService.addAddress(shippingvo);
	}

	@Test
	public void findAddress() {
		ShippingAddress address = shippingService.findAddress(1);
		System.out.println(address);
	}

	@Test
	public void update() {
		ShippingAddress address = new ShippingAddress();
		address.setCity("cityup");
		address.setDistrict("districtup");
		address.setName("nameup");
		address.setPhone("110110110");
		address.setProvince("provinceup");
		address.setStatus(0);
		shippingService.updateAddress(address);
	}
}
