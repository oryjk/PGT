package com.pgt.product.bean;

import com.pgt.shipping.bean.ShippingAddress;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 10/28/15.
 */
public class Product implements Serializable {

    private static long serialVersionUID = 1L;

    public static final int INVALID = 0;

    private Integer productId;
    private String name;
    private String serialNumber;
    private Double salePrice;
    private Double listPrice;
    private Integer status;
    private String description;
    private Double shippingFee;
    private String relatedCategoryId;
    private Integer stock;
    private Date creationDate;
    private Date updateDate;
    //    private List<ProductMedia> medias;
    private List<ProductMedia> mainMedias;
    private List<ProductMedia> heroMedias;
    private ProductMedia thumbnailMedia;
    private ProductMedia advertisementMedia;
    private ProductMedia frontMedia;
    private ProductMedia expertMedia;
    private String isNew;// 新旧程度
    private String title;// 标题
    private String shortDescription;
    private ShippingAddress shippingAddress;
    private boolean isHot;
    private String keyWord;
    private String merchant;
    private ProductMedia mobileDetailMedia;
    private String type;

    private Integer tenderId;

    public List<ProductMedia> getMainMedias() {
        return mainMedias;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMainMedias(List<ProductMedia> mainMedias) {
        this.mainMedias = mainMedias;
    }

    public List<ProductMedia> getHeroMedias() {
        return heroMedias;
    }

    public void setHeroMedias(List<ProductMedia> heroMedias) {
        this.heroMedias = heroMedias;
    }


    public ProductMedia getAdvertisementMedia() {
        return advertisementMedia;
    }

    public void setAdvertisementMedia(ProductMedia advertisementMedia) {
        this.advertisementMedia = advertisementMedia;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public String getRelatedCategoryId() {
        return relatedCategoryId;
    }

    public void setRelatedCategoryId(String relatedCategoryId) {
        this.relatedCategoryId = relatedCategoryId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

//    public List<ProductMedia> getMedias() {
//        return medias;
//    }
//
//    public void setMedias(List<ProductMedia> medias) {
//        this.medias = medias;
//    }

    public ProductMedia getFrontMedia() {
        return frontMedia;
    }

    public void setFrontMedia(ProductMedia frontMedia) {
        this.frontMedia = frontMedia;
    }


    public boolean isHot() {
        return isHot;
    }

    public boolean getIsHot() {
        return isHot();
    }

    public void setIsHot(boolean isHot) {
        this.isHot = isHot;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }


    public ProductMedia getThumbnailMedia() {
        return thumbnailMedia;
    }

    public void setThumbnailMedia(ProductMedia thumbnailMedia) {
        this.thumbnailMedia = thumbnailMedia;
    }


    public ProductMedia getExpertMedia() {
        return expertMedia;
    }

    public void setExpertMedia(ProductMedia exprtMedia) {
        this.expertMedia = exprtMedia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!productId.equals(product.productId)) return false;
        return name.equals(product.name);

    }

    @Override
    public int hashCode() {
        int result = productId.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    public ProductMedia getMobileDetailMedia() {
        return mobileDetailMedia;
    }

    public void setMobileDetailMedia(ProductMedia mobileDetailMedia) {
        this.mobileDetailMedia = mobileDetailMedia;
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }
}
