package data;

import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Lists;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.shipping.bean.ShippingAddress;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 12/9/15.
 */
public class ParseDataTest {


    @Test
    public void parseObjectToJson() {

        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Product product = new Product();
            product.setDescription("abc");
            product.setCreationDate(new Date());
            product.setAdvertisementMedia(new ProductMedia());
            product.setExpertMedia(new ProductMedia());
            product.setFrontMedia(new ProductMedia());
            product.setHeroMedias(new ArrayList<>());
            product.setName("我们的产品 ");
            product.setProductId(1);
            product.setIsNew("全新");
            product.setListPrice(100.00);
            product.setSalePrice(40.00);
            product.setMainMedias(Lists.newArrayList(new ProductMedia()));
            product.setRelatedCategoryId(100 + "");
            product.setSerialNumber(100000000 + "");
            product.setShortDescription("这个产品真心好");
            product.setThumbnailMedia(new ProductMedia());
            product.setTitle("this is title");
            product.setUpdateDate(new Date());
            product.setShippingFee(20.00);
            product.setShippingAddress(new ShippingAddress());
            product.setStatus(10);
            product.setStock(1);
            productList.add(product);
        }

        String json = JSONObject.toJSONString(productList);
        System.out.println(json);

    }
}
