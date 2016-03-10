package com.pgt.product.service;

import com.pgt.base.service.TransactionService;
import com.pgt.common.dao.MediaMapper;
import com.pgt.hot.bean.HotSearch;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductCategoryRelation;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.dao.ProductMapper;
import com.pgt.search.bean.SearchPaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 10/28/15.
 */

@Service
public class ProductServiceImp extends TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private MediaMapper mediaMapper;


    public Product queryProduct(int productId) {
        return productMapper.queryProduct(productId);
    }


    public List<Product> queryProducts(SearchPaginationBean searchPaginationBean) {
        return productMapper.queryProducts(searchPaginationBean);
    }


    public List<Product> queryAllProducts(Integer stock) {
        return productMapper.queryAllProducts(stock);
    }


    public Integer createProduct(Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            if (ObjectUtils.isEmpty(product.getCreationDate())) {
                product.setCreationDate(new Date());

            }
            if (ObjectUtils.isEmpty(product.getUpdateDate())) {
                product.setUpdateDate(new Date());
            }
            productMapper.createProduct(product);
            ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
            productCategoryRelation.setProductId(product.getProductId());
            productCategoryRelation.setCategoryId(Integer.valueOf(product.getRelatedCategoryId()));
            productMapper.createProductCategoryRelation(productCategoryRelation);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when create a product with product is is {productId}",
                    product.getProductId());
            transactionStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
        return product.getProductId();
    }


    public void createProduct(Integer categoryId, Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            if (ObjectUtils.isEmpty(product.getCreationDate())) {
                product.setCreationDate(new Date());

            }
            if (ObjectUtils.isEmpty(product.getUpdateDate())) {
                product.setUpdateDate(new Date());
            }
            productMapper.createProduct(product);
            createProductMedias(product);
            ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
            productCategoryRelation.setProductId(product.getProductId());
            productCategoryRelation.setCategoryId(categoryId);
            productMapper.createProductCategoryRelation(productCategoryRelation);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when create a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }

    public void createProductMedias(Product product) {

        TransactionStatus transactionStatus = ensureTransaction();
        try {
            Integer productId = product.getProductId();
            if (!ObjectUtils.isEmpty(product.getThumbnailMedia())) {
                product.getThumbnailMedia().setReferenceId(productId);
            }
            if (!ObjectUtils.isEmpty(product.getAdvertisementMedia())) {

                product.getAdvertisementMedia().setReferenceId(productId);
            }
            if (!ObjectUtils.isEmpty(product.getFrontMedia())) {
                product.getFrontMedia().setReferenceId(productId);

            }

            mediaMapper.createMedia(product.getThumbnailMedia());
            mediaMapper.createMedia(product.getAdvertisementMedia());
            mediaMapper.createMedia(product.getFrontMedia());
            mediaMapper.createMedia(product.getExpertMedia());
            if (!ObjectUtils.isEmpty(product.getHeroMedias())) {
                product.getHeroMedias().stream().forEach(productMedia -> {
                    productMedia.setReferenceId(productId);
                    mediaMapper.createMedia(productMedia);
                });
            }
            if (!ObjectUtils.isEmpty(product.getMainMedias())) {
                product.getMainMedias().stream().forEach(productMedia -> {
                    productMedia.setReferenceId(productId);
                    mediaMapper.createMedia(productMedia);
                });
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }


    }


    public Integer updateProduct(Product product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {

            productMapper.updateProduct(product);
            mediaMapper.deleteAllProductMedia(product.getProductId());
            mediaMapper.createMedia(product.getThumbnailMedia());
            mediaMapper.createMedia(product.getAdvertisementMedia());
            mediaMapper.createMedia(product.getFrontMedia());
            mediaMapper.createMedia(product.getExpertMedia());
            if (!ObjectUtils.isEmpty(product.getHeroMedias())) {
                product.getHeroMedias().stream().forEach(productMedia -> {
                    mediaMapper.createMedia(productMedia);
                });
            }
            if (!ObjectUtils.isEmpty(product.getMainMedias())) {
                product.getMainMedias().stream().forEach(productMedia -> {
                    mediaMapper.createMedia(productMedia);
                });
            }

        } catch (Exception e) {
            LOGGER.error("Some thing wrong when update a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
        return product.getProductId();
    }


    public Integer updateProductBase(Product product) {
        productMapper.updateProduct(product);
        String referenceId = product.getRelatedCategoryId();
        if (!StringUtils.isBlank(referenceId)) {
            ProductCategoryRelation productCategoryRelation = new ProductCategoryRelation();
            productCategoryRelation.setProductId(product.getProductId());
            productCategoryRelation.setCategoryId(Integer.valueOf(product.getRelatedCategoryId()));
            productMapper.updateProductCategoryRelation(productCategoryRelation);

        }

        return product.getProductId();
    }


    public void deleteProduct(String productId) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            productMapper.deleteProduct(Integer.valueOf(productId));
            productMapper.deleteProductCategoryRelationByProductId(Integer.valueOf(productId));

        } catch (Exception e) {
            LOGGER.error("Some thing wrong when delete a product with product is is {}.", productId);
            LOGGER.error(e.getMessage());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }


    public void deleteProducts(List<String> productIds) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            productMapper.deleteProducts(productIds);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when delete products,products ids are:");
            productIds.stream().forEach(s -> LOGGER.error(s));
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }


    public List<ProductMedia> queryProductMedias(final int pProductId) {
        return productMapper.queryProductMedias(pProductId);
    }


    public List<HotSearch> queryAllHotsearch() {

        return productMapper.queryAllHotsearch();
    }


    public void buildProductMedias(Product product) {
        createProductMedias(product);
    }


    public Integer queryProductTotal(SearchPaginationBean searchPaginationBean) {
        return productMapper.queryProductTotal(searchPaginationBean);
    }

}
