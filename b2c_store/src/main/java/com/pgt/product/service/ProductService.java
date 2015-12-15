package com.pgt.product.service;

import com.pgt.hot.bean.HotSearch;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.search.bean.SearchPaginationBean;
import com.pgt.utils.PaginationBean;

import java.util.List;

/**
 * Created by carlwang on 10/28/15.
 */
public interface ProductService {

    Product queryProduct(int productId);

    List<Product> queryProducts(SearchPaginationBean searchPaginationBean);

    List<Product> queryAllProducts(Integer stock);

    Integer createProduct(Product product);
    void createProduct(Integer categoryId,Product product);

    Integer updateProduct(Product product);

    void deleteProduct(String productId);

    void deleteProducts(List<String> productIds);

    List<ProductMedia> queryProductMedias(int pProductId);
    
    List <HotSearch> queryAllHotsearch();

}