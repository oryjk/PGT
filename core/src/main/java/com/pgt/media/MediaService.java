package com.pgt.media;

import com.pgt.common.bean.Media;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.ProductMedia;
import com.pgt.tender.bean.TenderMedia;
import org.apache.ibatis.annotations.Param;

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

    Media queryPageBackgroundHeaderMedia(Integer pageBackgroundId);

    Media queryPageBackgroundMiddleMedia(Integer pageBackgroundId);

    Media queryPageBackgroundFooterMedia(Integer pageBackgroundId);

    Media queryHotProductFrontMedia(Integer hotSearchId);



    TenderMedia queryTenderP2PFrontMedia(@Param("tenderId") Integer tenderId);


    TenderMedia queryTenderP2PAdvertisement(@Param("tenderId") Integer tenderId);


    List<TenderMedia> queryTenderP2PHeroMedia(@Param("tenderId") Integer tenderId);

    List<TenderMedia> queryTenderP2PMainMedia(@Param("tenderId") Integer tenderId);


    TenderMedia queryTenderP2PExpertMedia(@Param("tenderId") Integer tenderId);

    TenderMedia queryTenderMobileDetailMedia(@Param("tenderId") Integer tenderId);

    Integer createMedia(Media media);
}
