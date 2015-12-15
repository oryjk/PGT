package com.pgt.product.service;

import com.pgt.base.service.TransactionService;
import com.pgt.common.dao.MediaMapper;
import com.pgt.hot.bean.HotSearch;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.dao.ProductMapper;
import com.pgt.search.bean.SearchPaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 10/28/15.
 */

@Service
public class ProductServiceImp extends TransactionService implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp.class);

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MediaMapper mediaMapper;

    @Override
    public Product queryProduct(int productId) {
        return productMapper.queryProduct(productId);
    }

    @Override
    public List<Product> queryProducts(SearchPaginationBean searchPaginationBean) {
        return productMapper.queryProducts(searchPaginationBean);
    }

    @Override
    public List<Product> queryAllProducts(Integer stock) {
        return productMapper.queryAllProducts(stock);
    }

    @Override
    public Integer createProduct(Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            Integer productId = productMapper.createProduct(product);
            getTransactionManager().commit(transactionStatus);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when create a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        }

        return product.getProductId();
    }

    @Override
    public void createProduct(Integer categoryId, Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            Integer productId = productMapper.createProduct(product);
            if (!ObjectUtils.isEmpty(product.getExpertMedia())) {
                product.getExpertMedia().setReferenceId(productId);
            }
            if (!ObjectUtils.isEmpty(product.getAdvertisementMedia())) {

                product.getAdvertisementMedia().setReferenceId(productId);
            }
            if (!ObjectUtils.isEmpty(product.getFrontMedia())){
                product.getFrontMedia().setReferenceId(productId);

            }

            mediaMapper.createMedia(product.getExpertMedia());
            mediaMapper.createMedia(product.getAdvertisementMedia());
            mediaMapper.createMedia(product.getFrontMedia());
            if (!ObjectUtils.isEmpty(product.getHeroMedias())) {
                product.getHeroMedias().stream().forEach(productMedia -> {
                    productMedia.setReferenceId(productId);
                    mediaMapper.createMedia(productMedia);
                });
            }
            if (!ObjectUtils.isEmpty(product.getMainMedias())) {
                product.getHeroMedias().stream().forEach(productMedia -> {
                    productMedia.setReferenceId(productId);
                    mediaMapper.createMedia(productMedia);
                });
            }
            if (!ObjectUtils.isEmpty(product.getThumbnailMedias())) {
                product.getHeroMedias().stream().forEach(productMedia -> {
                    productMedia.setReferenceId(productId);
                    mediaMapper.createMedia(productMedia);
                });
            }
            ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
            productCategoryRelation.setProductId(product.getProductId());
            productCategoryRelation.setCategoryId(categoryId);
            productMapper.createProductCategoryRelation(productCategoryRelation);
            getTransactionManager().commit(transactionStatus);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when create a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        }
    }

    @Override
    public Integer updateProduct(Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            productMapper.updateProduct(product);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when update a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        }
        return product.getProductId();
    }

    @Override
    public void deleteProduct(String productId) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            productMapper.deleteProduct(productId);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when delete a product with product is is {productId}", productId);
            getTransactionManager().rollback(transactionStatus);
        }
    }

    @Override
    public void deleteProducts(List<String> productIds) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            productMapper.deleteProducts(productIds);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when delete products,products ids are:");
            productIds.stream().forEach(s -> LOGGER.error(s));
            getTransactionManager().rollback(transactionStatus);
        }
    }

    @Override
    public List<ProductMedia> queryProductMedias(final int pProductId) {
        return getProductMapper().queryProductMedias(pProductId);
    }

    @Override
    public List<HotSearch> queryAllHotsearch() {

        return productMapper.queryAllHotsearch();
    }

}
