package com.pgt.media;

import com.pgt.common.bean.Media;
import com.pgt.common.dao.MediaMapper;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.ProductMedia;
import com.pgt.product.dao.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by carlwang on 12/7/15.
 */
@Service
public class MediaServiceImp implements MediaService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private MediaMapper mediaMapper;

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
    public List<ProductMedia> findThumbnailMediasByProductId(String productId) {
        return productMapper.queryProductThumbnailMedias(Integer.valueOf(productId));
    }

    @Override
    public Media findCopyWriterMedia(Integer copyWriterId) {
        return mediaMapper.queryMedia(MediaType.copy_write,copyWriterId);

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
