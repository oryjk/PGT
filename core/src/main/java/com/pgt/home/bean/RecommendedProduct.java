package com.pgt.home.bean;

import com.pgt.common.bean.Media;
import com.pgt.constant.Constants;
import com.pgt.product.bean.ProductType;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by carlwang on 3/29/16.
 */
@Component
public class RecommendedProduct implements Serializable {
    static final long serialVersionUID = 42L;
    private Integer recommendedProductId;
    private String name;
    private String url;
    private ProductType productType;
    private Integer sort;
    private Integer mediaId;
    private Media media;
    private Integer type;
    private Integer price;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getRecommendedProductId() {
        return recommendedProductId;
    }

    public void setRecommendedProductId(Integer recommendedProductId) {
        this.recommendedProductId = recommendedProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Integer getMediaId() {
        return mediaId;
    }

    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }
}
