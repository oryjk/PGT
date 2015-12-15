package common;

import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pgt.common.bean.Image;
import com.pgt.common.bean.ImageCustom;
import com.pgt.common.service.ImageService;
import com.pgt.utils.PaginationBean;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class ImageTest {

	@Autowired
	private ImageService imageService;

	@Test
	public void testeAdd() {

		for (int i = 0; i < 12; i++) {
			Image image = new Image();
			image.setCreateDate(new Date());
			image.setEndDate(new Date());
			image.setLocation("cccccccccccccccccccc");
			image.setPath("ooooooooooooooooo");
			image.setTitle("标题");
			image.setType("bb");
			image.setUrl("pppppppppppppppp");

			Integer id = imageService.createImage(image);
			System.out.println(id);

		}
	}

	@Test
	public void testeUpdate() {

		Image image = new Image();
		image.setImageId(1);
		image.setCreateDate(new Date());
		image.setEndDate(new Date());
		image.setLocation("位置");
		image.setPath("路径");
		image.setTitle("标题修改");
		image.setType("bb类型");
		image.setUrl("url连接");

		imageService.updateImage(image);

	}

	@Test
	public void testQueryById() {

		Image image = imageService.queryImageById(1);

		System.out.println(image);

	}

	@Test
	public void testQueryAll() {

		PaginationBean paginationBean = new PaginationBean();
		paginationBean.setCurrentIndex(0);
		paginationBean.setTotalAmount(13);
		paginationBean.setCapacity(3);

		ImageCustom imageCustom = new ImageCustom();

		imageCustom.setPaginationBean(paginationBean);

		List<Image> list = imageService.queryAllImage(imageCustom);

		System.out.println(list);

	}

	@Test
	public void testQueryByLocation() {

		ImageCustom image = new ImageCustom();

		image.setLocation("cccccccccccccccccccc");

		List<Image> list = imageService.queryAllImageByLocation(image);

		System.out.println(list);
	}

	public ImageService getImageService() {
		return imageService;
	}

	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

}
