package com.pgt.media;

import com.pgt.common.bean.Media;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.ProductMedia;

import java.util.List;

/**
 * Created by carlwang on 12/7/15.
 */
public interface MediaService {

    ProductMedia findFrontMediaByProductId(String productId);

    ProductMedia findAdByProductId(String productId);

    ProductMedia findAdByCategoryId(String categoryId);

    ProductMedia findExpertByProductId(String productId);

    ProductMedia findFrontByProductId(String productId);

    List<ProductMedia> findHeroMediasByProductId(String productId);

    List<ProductMedia> findMainMediasByProductId(String productId);

    ProductMedia findThumbnailMediasByProductId(String productId);
    ProductMedia findMobileDetailMediaByProductId(String productId);

    Integer create(ProductMedia productMedia);

    Media findCopyWriterMedia(Integer copyWriterId);

    void deleteMedia(Integer mediaId);

    Media findMedia(Integer mediaId,MediaType mediaType);

    List<Media> findMediaByRefId(Integer referenceId,MediaType mediaType);
    void updateMedia(Media media);

}
