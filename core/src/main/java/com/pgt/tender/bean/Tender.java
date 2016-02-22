package com.pgt.tender.bean;

import com.pgt.category.bean.Category;
import com.pgt.common.bean.Media;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.user.validation.group.LoginGroup;
import com.pgt.user.validation.group.RegistrationGroup;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by carlwang on 1/16/16.
 */
public class Tender implements TenderState, TenderAuditState, Serializable {

    private Integer tenderId;
    /**
     * 当铺的id
     */
    private Integer pawnShopId;
    /**
     * 当铺所有者的id
     */
    private Integer pawnShopOwnerId;
    /**
     * 当票编号
     */
    private Integer pawnTicketId;
    /**
     * 投资总金额
     */
    private Double tenderTotal;
    /**
     * 可以投资的份数
     */
    @Deprecated
    private Integer tenderQuantity;
    /**
     * 最小投资金额
     */
    @Deprecated
    private Double smallMoney;

    /**
     * 开标时间
     */
    private Date publishDate;
    /**
     * 截至时间
     */
    private Date dueDate;
    /**
     * 收益率
     */
    private Double interestRate;
    /**
     * 投资名称
     */
    @NotEmpty(message = "{NotEmpty.tender.name}", groups = {CreateTender.class})
    private String name;
    /**
     * 投资的详情
     */
    private String description;
    /**
     * 投资后多久天后开始算收益
     */
    private Integer prePeriod;
    /**
     * 无息天数
     */
    private Integer postPeriod;
    /**
     * 被投资的产品
     */
    private List<Product> products;
    /**
     * 投资创建日期
     */
    private Date creationDate;
    /**
     * 投资更新日期
     */
    private Date updateDate;
    /**
     * 投资所属分类
     */
    private Category category;
    /**
     * 手续费费率
     */
    private Double handlingFeeRate;
    /**
     * 是否是分类的热门
     */
    private Boolean categoryHot = false;
    /**
     * 是否是网站的热门
     */
    private Boolean siteHot = false;

    private Integer categoryId;

    /**
     * 状态
     */
    private int state = INITIAL;
    /**
     * 审核状态
     */
    private int auditState = PENDING_APPROVAL;

    private Pawnshop mPawnshop;

    /**
     * 首页图
     */
    private TenderMedia p2pAdvertisement;

    /**
     * 列表图
     */
    private TenderMedia p2pFrontMedia;

    /**
     * 详情图
     */
    private List<TenderMedia> p2pMainMedia;

    /**
     * 主图
     */
    private List<TenderMedia> p2pHeroMedias;

    /**
     * 专家图
     */
    private TenderMedia p2pExpertMedia;

    /**
     * 手机详情图
     */
    private TenderMedia mobileDetailMedia;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tenderId:").append(tenderId);
        stringBuilder.append(",");
        stringBuilder.append("pawnshopId:").append(pawnShopId);
        stringBuilder.append(",");
        stringBuilder.append("pawnTicketId:").append(pawnTicketId);
        stringBuilder.append(",");
        stringBuilder.append("tenderTotal:").append(tenderTotal);
        stringBuilder.append(",");
        stringBuilder.append("tenderQuantity:").append(tenderQuantity);
        stringBuilder.append(",");
        stringBuilder.append("publishDate:").append(publishDate);
        stringBuilder.append(",");
        stringBuilder.append("dueDate:").append(dueDate);
        stringBuilder.append(",");
        stringBuilder.append("interestRate:").append(interestRate);
        stringBuilder.append(",");
        stringBuilder.append("name:").append(name);
        stringBuilder.append(",");
        stringBuilder.append("description:").append(description);
        stringBuilder.append(",");
        stringBuilder.append("prePeriod:").append(prePeriod);
        stringBuilder.append(",");
        stringBuilder.append("postPeriod:").append(postPeriod);
        stringBuilder.append(",");
        stringBuilder.append("creationDate:").append(creationDate);
        stringBuilder.append(",");
        stringBuilder.append("updateDate:").append(updateDate);
        stringBuilder.append(",");
        stringBuilder.append("categoryId:").append(category == null ? null : category.getId());
        stringBuilder.append(",");
        stringBuilder.append("creationDate:");
        if (!ObjectUtils.isEmpty(products)) {
            products.stream().forEach(product -> stringBuilder.append(product.getProductId()).append(","));
        }
        return stringBuilder.toString();
    }

    public double getUnitPrice() {

        return 0;
    }


    public TenderMedia getMobileDetailMedia() {
        return mobileDetailMedia;
    }

    public void setMobileDetailMedia(TenderMedia mobileDetailMedia) {
        this.mobileDetailMedia = mobileDetailMedia;
    }

    public TenderMedia getP2pExpertMedia() {
        return p2pExpertMedia;
    }

    public void setP2pExpertMedia(TenderMedia p2pExpertMedia) {
        this.p2pExpertMedia = p2pExpertMedia;
    }

    public List<TenderMedia> getP2pHeroMedias() {
        return p2pHeroMedias;
    }

    public void setP2pHeroMedias(List<TenderMedia> p2pHeroMedias) {
        this.p2pHeroMedias = p2pHeroMedias;
    }

    public TenderMedia getP2pFrontMedia() {
        return p2pFrontMedia;
    }

    public void setP2pFrontMedia(TenderMedia p2pFrontMedia) {
        this.p2pFrontMedia = p2pFrontMedia;
    }

    public List<TenderMedia> getP2pMainMedia() {
        return p2pMainMedia;
    }

    public void setP2pMainMedia(List<TenderMedia> p2pMainMedia) {
        this.p2pMainMedia = p2pMainMedia;
    }

    public TenderMedia getP2pAdvertisement() {
        return p2pAdvertisement;
    }

    public void setP2pAdvertisement(TenderMedia p2pAdvertisement) {
        this.p2pAdvertisement = p2pAdvertisement;
    }


    public Double getSmallMoney() {
        return smallMoney;
    }

    public void setSmallMoney(Double smallMoney) {
        this.smallMoney = smallMoney;
    }

    public Integer getPawnShopId() {
        return pawnShopId;
    }

    public void setPawnShopId(Integer pawnShopId) {
        this.pawnShopId = pawnShopId;
    }

    public Integer getPawnShopOwnerId() {
        return pawnShopOwnerId;
    }

    public void setPawnShopOwnerId(Integer pawnShopOwnerId) {
        this.pawnShopOwnerId = pawnShopOwnerId;
    }

    public Integer getPawnTicketId() {
        return pawnTicketId;
    }

    public void setPawnTicketId(Integer pawnTicketId) {
        this.pawnTicketId = pawnTicketId;
    }

    public Double getTenderTotal() {
        List<Product> products = this.products;
        Double tenderTotal = 0.0;
        if (!ObjectUtils.isEmpty(products)) {
            for (Product product : products) {
                tenderTotal = tenderTotal + product.getStock() * product.getSalePrice();
            }
        }
        return tenderTotal;
    }

    public void setTenderTotal(Double tenderTotal) {
        this.tenderTotal = tenderTotal;
    }

    public Integer getTenderQuantity() {
        return tenderQuantity;
    }

    public void setTenderQuantity(Integer tenderQuantity) {
        this.tenderQuantity = tenderQuantity;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrePeriod() {
        return prePeriod;
    }

    public void setPrePeriod(Integer prePeriod) {
        this.prePeriod = prePeriod;
    }

    public Integer getPostPeriod() {
        return postPeriod;
    }

    public void setPostPeriod(Integer postPeriod) {
        this.postPeriod = postPeriod;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
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

    public Integer getTenderId() {
        return this.tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getHandlingFeeRate() {
        return handlingFeeRate;
    }

    public void setHandlingFeeRate(Double handlingFeeRate) {
        this.handlingFeeRate = handlingFeeRate;
    }

    public Boolean getCategoryHot() {
        return categoryHot;
    }

    public void setCategoryHot(Boolean categoryHot) {
        this.categoryHot = categoryHot;
    }

    public Boolean getSiteHot() {
        return siteHot;
    }

    public void setSiteHot(Boolean siteHot) {
        this.siteHot = siteHot;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public int getState() {
        return state;
    }

    public void setState(final int pState) {
        state = pState;
    }

    public int getAuditState() {
        return auditState;
    }

    public void setAuditState(final int pAuditState) {
        auditState = pAuditState;
    }

    public Pawnshop getPawnshop() {
        return mPawnshop;
    }

    public void setPawnshop(final Pawnshop pPawnshop) {
        mPawnshop = pPawnshop;
    }
}
