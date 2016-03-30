package com.pgt.home.service;

import com.pgt.base.service.TransactionService;
import com.pgt.home.bean.RecommendedProduct;
import com.pgt.home.mapper.RecommendedProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.List;

/**
 * Created by carlwang on 3/30/16.
 */
@Service
public class RecommendedProductService extends TransactionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendedProductService.class);
    @Autowired
    private RecommendedProductMapper recommendedProductMapper;

    public void createRecommendedProductService(RecommendedProduct recommendedProduct) {
        TransactionStatus transactionStatus = ensureTransaction();
        LOGGER.debug("Begin to create.");
        try {
            recommendedProductMapper.createRecommendedProduct(recommendedProduct);
            LOGGER.debug("End to create.");
        } catch (Exception e) {
            transactionStatus.setRollbackOnly();
            LOGGER.debug("some error occured.");
        } finally {
            getTransactionManager().commit(transactionStatus);

        }
    }

    public void updateRecommendedProduct(RecommendedProduct recommendedProduct) {
        TransactionStatus transactionStatus = ensureTransaction();
        LOGGER.debug("Begin to update.");
        try {
            recommendedProductMapper.updateRecommendedProduct(recommendedProduct);
            LOGGER.debug("End to update.");
        } catch (Exception e) {
            transactionStatus.setRollbackOnly();
            LOGGER.debug("some error occured.");
        } finally {
            getTransactionManager().commit(transactionStatus);

        }
    }

    public void deleteRecommendedProduct(Integer recommendedProductId) {
        TransactionStatus transactionStatus = ensureTransaction();
        LOGGER.debug("Begin to delete.");
        try {
            recommendedProductMapper.deleteRecommendedProduct(recommendedProductId);
            LOGGER.debug("End to delete.");
        } catch (Exception e) {
            transactionStatus.setRollbackOnly();
            LOGGER.debug("some error occured.");
        } finally {
            getTransactionManager().commit(transactionStatus);

        }
    }

    public List<RecommendedProduct> queryAllRecommendedProduct(RecommendedProduct recommendedProduct) {
        TransactionStatus transactionStatus = ensureTransaction();
        List<RecommendedProduct> recommendedProducts = null;
        LOGGER.debug("Begin to query.");
        try {
            recommendedProducts = recommendedProductMapper.queryAllRecommendedProduct(recommendedProduct);
            LOGGER.debug("End to query.");
        } catch (Exception e) {
            transactionStatus.setRollbackOnly();
            LOGGER.debug("some error occured.");
        } finally {
            getTransactionManager().commit(transactionStatus);

        }
        return recommendedProducts;
    }
}
