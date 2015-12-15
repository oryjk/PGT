package product;

import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by carlwang on 10/28/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配置
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Test
    public void createManyTest() {
        int n = 10;
        while (n > 0) {
            n--;
            createProductTest();
        }
    }

    @Test
    public void createProductTest() {

        Random random = new Random();

        for (int i = 0; i < 1; i++) {
            Product product = new Product();
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
            ProductMedia media = new ProductMedia();
            media.setIndex(1);
            media.setPath("12312/1/2/12/1/21/2/");
            media.setTitle("呵呵");
            media.setType(MediaType.copy_write.toString());
            product.setFrontMedia(media);
            productService.createProduct(2, product);
        }

    }

    @Test
    public void queryProductTest() {
        Product product = productService.queryProduct(1);
        Assert.assertNotNull(product);
    }

    @Test
    public void queryProductsTest() {
        SearchPaginationBean paginationBean = new SearchPaginationBean();
        paginationBean.setAsc(true);
        paginationBean.setCapacity(10);
        paginationBean.setCurrentIndex(0);
        paginationBean.setSortFiledName("creationDate");
        paginationBean.setTotalAmount(100);
        List<Product> productList = productService.queryProducts(paginationBean);
        Assert.assertNotNull(productList);
    }

    @Test
    public void queryAllProductsTest() {
        List<Product> products = productService.queryAllProducts(-1);
        Assert.assertNotNull(products);

    }

    @Test
    public void updateProductTest() {
        Product product = productService.queryProduct(1);
        product.setDescription("update");
        product.setName("update name");
        productService.updateProduct(product);
    }

    @Test
    public void deleteProductTest() {
        productService.deleteProduct("3b7957c0-a790-44ce-a360-29f5d1f4bcbc");
    }

    @Test
    public void deleteProductsTest() {
        List<String> deleteList = new ArrayList<>();
        deleteList.add("352d1098-4c63-49fb-bf1c-42d325bccd9e");
        deleteList.add("35982a44-2a1c-47a8-9f6c-b5c8fa1b3c28");
        deleteList.add("3e8181b8-adcf-439b-bb87-f0bec6d0469d");
        deleteList.add("4b337656-7e98-4e3a-b002-cfe53523a106");
        productService.deleteProducts(deleteList);

    }

    @Test
    public void testSearch() {

        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();

        List<SortBean> sort = new ArrayList<SortBean>();

        SortBean s1 = new SortBean();

        s1.setPropertyName("CREATION_DATE");

        sort.add(s1);

        searchPaginationBean.setSortBeans(sort);

        searchPaginationBean.setCapacity(10);

        searchPaginationBean.setTerm("金");

        List<Product> list = productService.queryProducts(searchPaginationBean);

        System.out.println(list.size());

    }

}
