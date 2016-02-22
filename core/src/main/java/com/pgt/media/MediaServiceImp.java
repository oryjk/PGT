package com.pgt.media;

import com.pgt.base.service.TransactionService;
import com.pgt.common.bean.Media;
import com.pgt.common.dao.HotSearchMapper;
import com.pgt.common.dao.MediaMapper;
import com.pgt.common.service.HotSearchService;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.dao.ProductMapper;
import com.pgt.share.dao.ShareOrderMapper;
import com.pgt.style.dao.PageBackgroundMapper;
import com.pgt.tender.bean.TenderMedia;
import com.pgt.tender.mapper.TenderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 12/7/15.
 */
@Service
public class MediaServiceImp extends TransactionService implements MediaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImp.class);
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private MediaMapper mediaMapper;
    @Autowired
    private PageBackgroundMapper pageBackgroundMapper;
    @Autowired
    private HotSearchMapper hotSearchMapper;

    @Autowired
    private TenderMapper tenderMapper;

    @Override
    public ProductMedia findFrontMediaByProductId(String productId) {
        return productMapper.queryProductFrontMedia(Integer.valueOf(productId));
    }

    @Override
    public ProductMedia findAdByProductId(String productId) {
        return productMapper.queryProductAdvertisementMedia(Integer.valueOf(productId));
    }

    @Override
    public ProductMedia findAdByCategoryId(String categoryId) {
        //TODO
        return null;
    }

    @Override
    public ProductMedia findExpertByProductId(String productId) {
        return productMapper.queryProductExpertMedia(Integer.valueOf(productId));
    }

    @Override
    public ProductMedia findFrontByProductId(String productId) {
        return productMapper.queryProductFrontMedia(Integer.valueOf(productId));
    }

    @Override
    public List<ProductMedia> findHeroMediasByProductId(String productId) {
        return productMapper.queryProductHeroMedias(Integer.valueOf(productId));
    }

    @Override
    public List<ProductMedia> findMainMediasByProductId(String productId) {
        return productMapper.queryProductMainMedias(Integer.valueOf(productId));
    }

    @Override
    public ProductMedia findThumbnailMediasByProductId(String productId) {
        return productMapper.queryProductThumbnailMedias(Integer.valueOf(productId));
    }

    @Override
    public ProductMedia findMobileDetailMediaByProductId(String productId) {
        return productMapper.queryProductMobileDetailMedias(Integer.valueOf(productId));
    }

    @Override
    public Integer create(ProductMedia productMedia) {
        return createMedia(productMedia);
    }

    @Override
    public Integer createMedia(Media media) {
        TransactionStatus transactionStatus = ensureTransaction();
        try {
            mediaMapper.createMedia(media);
        } catch (Exception e) {
            LOGGER.error("Can not create media");
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
        return media.getId();
    }

    @Override
    public Media findCopyWriterMedia(Integer copyWriterId) {
        List<Media> medias = mediaMapper.queryMediaByRefId(MediaType.copy_write, copyWriterId);
        if (!ObjectUtils.isEmpty(medias)) {
            return medias.get(0);
        }
        return null;
    }

    @Override
    public void deleteMedia(Integer mediaId) {

        TransactionStatus transactionStatus = ensureTransaction();
        try {
            mediaMapper.deleteMedia(mediaId);
        } catch (Exception e) {
            LOGGER.error("Can not delete product media with id is {}.", mediaId);
            getTransactionManager().rollback(transactionStatus);
        } finally {
            getTransactionManager().commit(transactionStatus);
        }
    }

    @Override
    public Media findMedia(Integer mediaId, MediaType mediaType) {

        return mediaMapper.queryMedia(mediaId);
    }

    @Override
    public List<Media> findMediaByRefId(Integer referenceId, MediaType mediaType) {
        return mediaMapper.queryMediaByRefId(mediaType, referenceId);

    }


    @Override
    public Media queryPageBackgroundFooterMedia(Integer pageBackgroundId) {
        return pageBackgroundMapper.queryPageBackgroundFooterMedia(pageBackgroundId);
    }

    @Override
    public Media queryPageBackgroundMiddleMedia(Integer pageBackgroundId) {
        return pageBackgroundMapper.queryPageBackgroundMiddleMedia(pageBackgroundId);
    }

    @Override
    public Media queryPageBackgroundHeaderMedia(Integer pageBackgroundId) {
        return pageBackgroundMapper.queryPageBackgroundHeaderMedia(pageBackgroundId);
    }

    @Override
    public Media queryHotProductFrontMedia(Integer hotSearchId) {
        return hotSearchMapper.queryHotProductFrontMedia(hotSearchId);
    }


    @Override
    public List<TenderMedia> queryTenderP2PExpertMedias(Integer tenderId) {
        return tenderMapper.queryTenderP2PExpertMedias(tenderId);
    }

    @Override
    public TenderMedia queryTenderP2PMainMedia(Integer tenderId) {
        return tenderMapper.queryTenderP2PMainMedia(tenderId);
    }

    @Override
    public TenderMedia queryTenderP2PAdvertisement(Integer tenderId) {
        return tenderMapper.queryTenderP2PAdvertisement(tenderId);
    }

    @Override
    public TenderMedia queryTenderP2PListMedia(Integer tenderId) {
        return tenderMapper.queryTenderP2PListMedia(tenderId);
    }

    @Override
    public List<TenderMedia> queryTenderP2PDetailMedias(Integer tenderId) {
        return tenderMapper.queryTenderP2PDetailMedias(tenderId);
    }

    @Override
    public void updateMedia(Media media) {
        mediaMapper.updateMedia(media);
    }

    public ProductMapper getProductMapper() {
        return productMapper;
    }

    public void setProductMapper(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    public MediaMapper getMediaMapper() {
        return mediaMapper;
    }

    public void setMediaMapper(MediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
    }
}
