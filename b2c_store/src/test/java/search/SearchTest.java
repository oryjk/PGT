package search;

import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.bean.SortBean;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 10/31/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配
public class SearchTest {
    @Autowired
    private ProductServiceImp productServiceImp;
    @Autowired
    private ProductHelper     productHelper;

    @Test
    public void searchTest() {


        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCurrentIndex(0);
        searchPaginationBean.setTotalAmount(100);
        searchPaginationBean.setCapacity(20);
        searchPaginationBean.setCategoryId("100");
        searchPaginationBean.setTerm("pro");
        searchPaginationBean.setStock(0);
        SortBean sortBean = new SortBean();
        sortBean.setPropertyName("LIST_PRICE");
        sortBean.setSort("ASC");
        SortBean sortBean2 = new SortBean();
        sortBean2.setPropertyName("CREATION_DATE");
        sortBean2.setSort("ASC");
        List<SortBean> sortBeanList = new ArrayList<>();
        sortBeanList.add(sortBean);
        sortBeanList.add(sortBean2);
        searchPaginationBean.setSortBeans(sortBeanList);
        List<Product> products = productServiceImp.queryProducts(searchPaginationBean);
        if (!ObjectUtils.isEmpty(products)) {
            products.stream().forEach(product -> System.out.println(product.getProductId()));
        }


    }

    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }
}
