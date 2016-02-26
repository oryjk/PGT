package com.pgt.product.service;

import com.pgt.hot.bean.HotSearch;
import com.pgt.product.bean.P2PProduct;
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

    Integer createTenderProduct(P2PProduct product);

    void createProduct(Integer categoryId, Product product);

    Integer updateTenderProduct(P2PProduct product);

    Integer updateProduct(Product product);
    Integer updateProductBase(Product product);

    void deleteProduct(String productId);

    void deleteProducts(List<String> productIds);

    List<ProductMedia> queryProductMedias(int pProductId);
    
    List <HotSearch> queryAllHotsearch();
    void buildProductMedias(Product product);

    Integer queryProductTotal(SearchPaginationBean searchPaginationBean);
}
