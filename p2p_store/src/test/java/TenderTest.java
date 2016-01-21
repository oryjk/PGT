import com.google.common.collect.Lists;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductService;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.bean.TenderCategory;
import com.pgt.tender.mapper.TenderCategoryMapper;
import com.pgt.tender.service.TenderService;
import org.elasticsearch.action.search.SearchResponse;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 1/19/16.
 */

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载
public class TenderTest {

    @Autowired
    private TenderService tenderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TenderCategoryMapper tenderCategoryMapper;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;

    @Test
    public void createTender() {
        Tender tender = new Tender();
        tender.setCreationDate(new Date());
        tender.setDescription("description");
        tender.setDueDate(new Date());
        tender.setInterestRate(0.08);
        tender.setName("Tender name");
        tender.setPawnShopId(100);
        tender.setPawnTicketId(1000);
        tender.setPostPeriod(10);
        tender.setPrePeriod(10000);
        tender.setTenderQuantity(10000);
        tender.setTenderTotal(new Double(100000));
        tender.setUpdateDate(new Date());
        tender.setPublishDate(new Date());
        Integer tenderId = tenderService.createTender(tender);
        TenderCategory tenderCategory = new TenderCategory();
        tenderCategory.setTenderId(tenderId);
        tenderCategory.setCategoryId(44);
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCategoryId(44 + "");
        searchPaginationBean.setCapacity(100);
        List<Product> products = productService.queryProducts(searchPaginationBean);
        tender.setProducts(products);
        tenderCategoryMapper.createTenderCategory(tenderCategory);
        Assert.assertNotNull(tenderId);
    }


    @Test
    public void createTenders() {
        for (int i = 0; i < 100; i++) {
            createTender();
        }
    }

    @Test
    public void queryTender() {
        SearchPaginationBean searchPaginationBean = new SearchPaginationBean();
        searchPaginationBean.setCategoryId("44");
        searchPaginationBean.setCapacity(100);

        List<Product> productList = productService.queryProducts(searchPaginationBean);
        Assert.assertTrue(productList.size() > 0);
        productList.stream().forEach(product -> {
            product.setTenderId(2);
            productService.updateProduct(product);
        });

        Tender tender = tenderService.queryTender(2, false);
        Assert.assertNotNull(tender);
        Assert.assertNotNull(tender.getName());
        Assert.assertNotNull(tender.getProducts());
        Assert.assertTrue(tender.getProducts().size() > 0);
        Assert.assertNotNull(tender.getTenderId());
        Assert.assertNotNull(tender);
        Assert.assertNotNull(tender);
    }

    @Test
    public void queryTenderByCategoryId() {
        List<Tender> tenders = tenderService.queryTendersByCategoryId(44, false);
        Assert.assertNotNull(tenders);
        Tender tender = tenders.get(0);
        Assert.assertNotNull(tender);
        Assert.assertTrue(tender.getProducts().size() > 0);
    }

    @Test
    public void indexTender() {
        tenderSearchEngineService.initialIndex();
        tenderSearchEngineService.index();
    }

    @Test
    public void queryTenderByEs(){
        ESTerm term = new ESTerm();
        term.setPropertyName("tender.name");
        term.setTermValue("Tender");

        SearchResponse response =  tenderSearchEngineService.findTender(null, Lists.newArrayList(term), null, null, null, null, null);
        Assert.assertNotNull(response);
    }
}

