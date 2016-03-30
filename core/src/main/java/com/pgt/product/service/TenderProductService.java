package com.pgt.product.service;

import com.pgt.product.bean.P2PProduct;
import com.pgt.product.bean.P2PProductStatus;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductType;
import com.pgt.product.dao.ProductMapper;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.mapper.TenderMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 3/10/16.
 */
@Service
public class TenderProductService extends ProductServiceImp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImp.class);

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private TenderMapper tenderMapper;

    public P2PProduct queryTenderProduct(Integer productId) {
        return productMapper.queryTenderProduct(productId);
    }

    public Integer createTenderProduct(P2PProduct product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            if (ObjectUtils.isEmpty(product.getCreationDate())) {
                product.setCreationDate(new Date());
            }
            if (ObjectUtils.isEmpty(product.getUpdateDate())) {
                product.setUpdateDate(new Date());
            }
            product.setType(ProductType.LIVE_PAWNAGE.toString());
            product.setOriginStock(product.getStock());
            productMapper.createTenderProduct(product);
            Tender tender = tenderMapper.queryTenderById(product.getTenderId(), false);
            tender.setTenderTotal(tender.getTenderTotal() + product.getSalePrice() * product.getStock());
            buildTenderStatus(tender);
            tenderMapper.updateTender(tender);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when create a product with product is is {}",
                    product.getProductId());
            transactionStatus.setRollbackOnly();
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
        return product.getProductId();
    }

    public Integer updateTenderProduct(P2PProduct product) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            Product old_product = productMapper.queryProduct(product.getProductId());
            product.setType(ProductType.LIVE_PAWNAGE.toString());
            productMapper.updateTenderProduct(product);
            Tender tender = tenderMapper.queryTenderById(product.getTenderId(), false);
            tender.setTenderTotal(tender.getTenderTotal() - old_product.getSalePrice() * old_product.getStock());
            tender.setTenderTotal(tender.getTenderTotal() + product.getSalePrice() * product.getStock());
            buildTenderStatus(tender);
            tenderMapper.updateTender(tender);
        } catch (Exception e) {
            LOGGER.error("Some thing wrong when update a product with product is is {productId}",
                    product.getProductId());
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
        return product.getProductId();
    }

    private void buildTenderStatus(Tender tender) {
        if (ObjectUtils.isEmpty(tender)) {
            LOGGER.debug("The tender is empty.");
            return;
        }
        List<P2PProduct> products = tender.getProducts();
        if (CollectionUtils.isEmpty(products)) {
            tender.setTenderStatus(P2PProductStatus.REVIEW_PAWNAGE);
        }
        final int[] livePawnage = new int[1];
        products.stream().forEach(p2PProduct -> {
            if (P2PProductStatus.LIVE_PAWNAGE == p2PProduct.getPawnageStatus()) {
                tender.setTenderStatus(P2PProductStatus.LIVE_PAWNAGE);
                return;
            }
        });
        if (livePawnage.length == 0) {
            if (((new Date().getTime() / 1000) - (tender.getDueDate().getTime() / 1000)) / (1000 * 60 * 60 * 24) <= 0) {
                tender.setTenderStatus(P2PProductStatus.DEAD_PAWNAGE);
                return;
            }
            tender.setTenderStatus(P2PProductStatus.REDEEM_PAWNAGE);
            return;
        }
    }

}
