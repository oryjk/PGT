package search;

import java.util.ArrayList;

import java.util.List;

import com.pgt.constant.Constants;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;
import com.pgt.search.bean.ESProduct;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml") // 加载配
public class ESSearchServiceTest {

    @Autowired
    private ESSearchService eSSearchService;

    // 建立商品的索引
    @Test
    public void productIndex() {

        eSSearchService.initialIndex(true);
        eSSearchService.categoryIndex();
        BulkResponse responses = eSSearchService.productIndex();
        eSSearchService.hotProductIndex();
        Assert.assertFalse(responses.hasFailures());

    }


    @Test
    public void hotSalesTest() {
        ESSort esSort = new ESSort();
        esSort.setPropertyName(Constants.SORT);
        esSort.setSortOrder(SortOrder.ASC);
        SearchResponse response = eSSearchService.findHotSales(esSort);
        Assert.assertNotNull(response);
    }

    // 取出数据
    @Test
    public void getQuery() {

        ESTerm esterm = new ESTerm();

        esterm.setPropertyName("name");
        esterm.setTermValue("血珀手串");

        ESSort esSort = new ESSort();
        esSort.setPropertyName("productId");
        esSort.setSortOrder(SortOrder.DESC);

        ESTerm esterm2 = new ESTerm();

        esterm2.setPropertyName("parentCategoryId");
        esterm2.setTermValue("1");

        ESRange esRange = new ESRange();


        esRange.setPropertyName("listPrice");
        esRange.setFrom(200);
        esRange.setTo(2000);

        SearchResponse searchResponse = eSSearchService.findProducts(esterm2, esterm, esRange, esSort, null, null, null, null);

        SearchHits hits = searchResponse.getHits();

        System.out.println("查询到的记录数!" + hits.getTotalHits());

        SearchHit[] searchHists = hits.getHits();

        List<ESProduct> esproductList = new ArrayList<ESProduct>();

        Gson gson = new Gson();

        System.out.println(searchResponse.toString());

        // System.out.println(list);

        if (searchHists.length > 0) {

            for (SearchHit searchHit : searchHists) {

                ESProduct esProduct = null;

                // ModelMapper modelMapper = new ModelMapper();

                // modelMapper.map(searchHit.getSource(), ESProduct.class);

                // System.out.println(esProduct);

				/*
                 *
				 * Integer productId = (Integer)
				 * searchHit.getSource().get("productId");
				 * 
				 * String name = (String) searchHit.getSource().get("name");
				 * 
				 * String serialNumber = (String)
				 * searchHit.getSource().get("seriaNumber");
				 * 
				 * Double salePrice = (Double)
				 * searchHit.getSource().get("salePrice");
				 * 
				 * Double listPrice = (Double)
				 * searchHit.getSource().get("listPrice");
				 * 
				 * Integer status = (Integer) searchHit.getSource().get(
				 * " status");
				 * 
				 * String description = (String)
				 * searchHit.getSource().get("description");
				 * 
				 * Double shippingFee = (Double)
				 * searchHit.getSource().get("shippingFee");
				 * 
				 * String relatedCategoryId = (String)
				 * searchHit.getSource().get("relatedCategoryId");
				 * 
				 * Integer stock = (Integer) searchHit.getSource().get("stock");
				 * 
				 * String isNew = (String) searchHit.getSource().get("isNew");
				 * 
				 * String title = (String) searchHit.getSource().get("title");//
				 * 标题
				 * 
				 * String shortDescripyion = (String)
				 * searchHit.getSource().get("shortDescripyion");
				 * 
				 * String parentCategoryId = (String)
				 * searchHit.getSource().get("parentCategoryId");
				 * 
				 * String parentCategoryName = (String)
				 * searchHit.getSource().get("parentCategoryName");
				 * 
				 * String rootCategoryId = (String)
				 * searchHit.getSource().get("rootCategoryId");
				 * 
				 * String rootCategoryName = (String)
				 * searchHit.getSource().get("rootCategoryName");
				 * 
				 * String path =(String) searchHit.getSource().get("path");
				 * 
				 * esProduct.setDescription(description);
				 * esProduct.setIsNew(isNew); esProduct.setListPrice(listPrice);
				 * esProduct.setName(name);
				 * esProduct.setParentCategoryId(parentCategoryId);
				 * esProduct.setParentCategoryName(parentCategoryName);
				 * esProduct.setProductId(productId);
				 * esProduct.setRelatedCategoryId(relatedCategoryId);
				 * esProduct.setRootCategoryId(rootCategoryId);
				 * esProduct.setRootCategoryName(rootCategoryName);
				 * esProduct.setSalePrice(salePrice);
				 * esProduct.setSerialNumber(serialNumber);
				 * esProduct.setShippingFee(shippingFee);
				 * esProduct.getShortDescripyion(); esProduct.setStatus(status);
				 * esProduct.setStock(stock); esProduct.setTitle(title);
				 * esProduct.setShortDescripyion(shortDescripyion);
				 * 
				 * 
				 * 
				 * esproductList.add(esProduct);
				 * 
				 */

            }

            System.out.println("查询到的记录数--------------------------" + esproductList.size());

        }
    }
}
