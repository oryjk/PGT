package com.pgt.common.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.common.bean.Media;
import com.pgt.media.bean.MediaType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author zhangxiaodong
 *         <p>
 *         2015年12月4日
 */

@Component
public interface MediaMapper extends SqlMapper {


    Media queryMedia(@Param(value = "mediaType") MediaType mediaType, @Param(value = "referenceId") Integer referenceId);

    Integer createMedia(Media media);

    Integer updateMedia(Media media);

    void deleteProductMedia(@Param("mediaType") MediaType mediaType, @Param("productId") Integer productId);

    void deleteAllProductMedia(@Param("productId") Integer productId);

    void deleteMedia(@Param("mediaId") Integer mediaId);
}
