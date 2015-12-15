package product;

import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.hot.service.HotProductService;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by carlwang on 11/3/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class HotProductTest {

    @Autowired
    private HotProductService hotProductService;

    @Test
    public void createTest() {
        Random random = new Random();
        Product product = new Product();
        Category category = new Category();
        product.setUpdateDate(new Date());
        product.setCreationDate(new Date());
        product.setDescription("This is a computer");
        product.setListPrice(random.nextDouble());
        product.setName("This is a product");
        product.setRelatedCategoryId(Math.abs(random.nextInt() % 10) + "");
        product.setSalePrice(random.nextDouble());
        product.setSerialNumber("100000001");
        product.setShippingFee(random.nextDouble());
        product.setStatus(1);
        product.setStock(100);
        product.setProductId(1000);

        category.setName("戒子");
        category.setType(CategoryType.HOME_PAGE_HOT);
        ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
        productCategoryRelation.setCategoryId(2);
        productCategoryRelation.setProductId(2);
        hotProductService.createProductCategoryRelation(productCategoryRelation);
    }

    @Test
    public void deleteHotProduct() {
        ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
        productCategoryRelation.setCategoryId(1);
        productCategoryRelation.setProductId(1);
        hotProductService.deleteProductCategoryRelation(productCategoryRelation);
    }


}
