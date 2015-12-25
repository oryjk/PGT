package index;

import com.google.common.collect.Lists;
import com.pgt.product.bean.InventoryType;
import com.pgt.search.bean.ESAggregation;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by carlwang on 11/29/15.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class IndexTest {
    @Autowired
    private ESSearchService esSearchService;

    @Test
    public void indexTest() throws IOException {

        esSearchService.initialIndex(true);
        esSearchService.categoryIndex();
        BulkResponse responses = esSearchService.productIndex();
        Assert.assertFalse(responses.hasFailures());

    }

    @Test
    public void findProductTest() {
        ESTerm esTerm = new ESTerm();
        esTerm.setPropertyName("relatedCategoryId");
        esTerm.setTermValue("1");
        ESTerm esMatch = new ESTerm();
        esMatch.setPropertyName("name");
        esMatch.setTermValue("福在");
        ESRange esRange = new ESRange();
        esRange.setPropertyName("salePrice");
        esRange.setFrom(5300);
        esRange.setTo(6000);
        ESAggregation esAggregation = new ESAggregation();
        esAggregation.setPropertyName("parentCategoryId");
        esAggregation.setAggregationName("categoryIds");
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setCapacity(2);
        paginationBean.setCurrentIndex(0);
        SearchResponse response = esSearchService.findProducts(esTerm, Lists.newArrayList(esMatch), esRange, null, paginationBean, null, null);
        SearchHits searchHits = response.getHits();
        SearchHit[] searchHits1 = searchHits.getHits();
        List<SearchHit> searchHitList = Arrays.asList(searchHits1);
        Assert.assertEquals(14, searchHitList.size());
    }

    @Test
    public void findCategoryTest() {
        List<ESTerm> esTerms = new ArrayList<>();
        ESTerm esTerm1 = new ESTerm();
        esTerm1.setPropertyName("id");
        esTerm1.setTermValue("1");
        ESTerm esTerm2 = new ESTerm();
        esTerm2.setPropertyName("id");
        esTerm2.setTermValue("2");
        esTerms.add(esTerm1);
        esTerms.add(esTerm2);
        SearchResponse response = esSearchService.findCategories(esTerms, null);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getHits());
        Assert.assertNotNull(response.getHits().getHits());
    }

    @Test
    public void findProductsByCategoryIdTest() {
        PaginationBean paginationBean = new PaginationBean();
        paginationBean.setCapacity(10000);
        paginationBean.setCurrentIndex(0);
        SearchResponse response = esSearchService.findProductsByCategoryId(9 + "", null, null, paginationBean, null);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getHits());
        Assert.assertNotNull(response.getHits().getHits());
    }


    @Test
    public void updateInventory(){
        List<Integer> ids=new ArrayList<>();
        ids.add(131);
//        esSearchService.modifyProductInventory(ids, InventoryType.DEDUCT);
    }
    public ESSearchService getEsSearchService() {
        return esSearchService;
    }

    public void setEsSearchService(ESSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }
}
