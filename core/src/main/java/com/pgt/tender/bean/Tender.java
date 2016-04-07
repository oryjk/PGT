package com.pgt.tender.bean;

import com.pgt.category.bean.Category;
import com.pgt.common.bean.Media;
import com.pgt.pawn.bean.PawnTicket;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.validation.group.PawnGroup;
import com.pgt.product.bean.P2PProduct;
import com.pgt.product.bean.P2PProductStatus;
import com.pgt.product.bean.Product;
import com.pgt.product.bean.ProductMedia;
import com.pgt.user.validation.group.LoginGroup;
import com.pgt.user.validation.group.RegistrationGroup;
import com.pgt.utils.TenderDateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.elasticsearch.common.collect.HppcMaps;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
    @NotNull(message = "{NotEmpty.tender.pawnShopId}", groups = {CreateTender.class})
    // @Pattern(regexp = "[0-9]{0,30}", message = "{Pattern.tender.pawnShopId}", groups = {CreateTender.class})
    private Integer pawnShopId;
    /**
     * 当铺所有者的id
     */
    @NotNull(message = "{NotEmpty.tender.pawnShopOwnerId}", groups = {CreateTender.class})
    //@Pattern(regexp = "[0-9]{0,30}", message = "{Pattern.tender.pawnShopOwnerId}", groups = {CreateTender.class})
    private Integer pawnShopOwnerId;
    /**
     * 当票编号
     */
    @NotNull(message = "{NotEmpty.tender.pawnTicketId}", groups = {CreateTender.class})
    //@Pattern(regexp = "[0-9]{0,30}", message = "{Pattern.tender.pawnTicketId}", groups = {CreateTender.class})
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
    @NotNull(message = "{NotEmpty.tender.publishDate}", groups = {CreateTender.class})
    private Date publishDate;
    /**
     * 截至时间
     */
    @NotNull(message = "{NotEmpty.tender.dueDate}", groups = {CreateTender.class})
    private Date dueDate;
    /**
     * 收益率
     */
    @NotNull(message = "{NotEmpty.tender.interestRate}", groups = {CreateTender.class})
    private Double interestRate;
    /**
     * 投资名称
     */
    @NotEmpty(message = "{NotEmpty.tender.name}", groups = {CreateTender.class})
    @Length(max = 40, message = "{Length.tender.name}", groups = {CreateTender.class})
    private String name;
    /**
     * 投资的详情
     */
    @NotEmpty(message = "{NotEmpty.tender.description}", groups = {CreateTender.class})
    @Length(max = 40, message = "{Length.tender.description}", groups = {CreateTender.class})
    private String description;
    /**
     * 投资后多久天后开始算收益
     */
    @NotNull(message = "{NotEmpty.tender.prePeriod}", groups = {CreateTender.class})
    private Integer prePeriod;
    /**
     * 无息天数
     */
    @NotNull(message = "{NotEmpty.tender.postPeriod}", groups = {CreateTender.class})
    private Integer postPeriod;
    /**
     * 被投资的产品
     */
    private List<P2PProduct> products;
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
    @NotNull(message = "{NotEmpty.tender.handlingFeeRate}", groups = {CreateTender.class})
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


    private Integer deadline;

    private String imageDesc;

    public PawnTicket getPawnTicket() {
        return pawnTicket;
    }

    public void setPawnTicket(PawnTicket pawnTicket) {
        this.pawnTicket = pawnTicket;
    }

    private PawnTicket pawnTicket;


    public Double getCompleteRate() {
        if (ObjectUtils.isEmpty(productQuantity) || productQuantity == 0) {
            return 0.00;
        }
        return Double.valueOf((1 - getProductResidue() / productQuantity) * 100);
    }

    public void setCompleteRate(Double completeRate) {
        this.completeRate = completeRate;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public Integer getDeadline() {
        Integer end = 0;
        if (!ObjectUtils.isEmpty(dueDate)) {
            end = TenderDateUtils.getDaySub(new Date(), dueDate);
        }
        return end;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    /**
     * 进行了的百分比
     */
    private Double completeRate;

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

    /**
     * 产品总数量
     */
    private Integer productQuantity;


    /**
     * 剩余产品数量
     */
    private Integer productResidue;

    /**
     * 剩余多少天
     */
    private Long residueDate;

    public Long getResidueDate() {
        Date date = DateUtils.addDays(new Date(), getPostPeriod());
        Long day = (dueDate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
        if (day > 0) {
            return day;
        }
        return 0L;
    }


    public Integer getTenderStatus() {
        if (tenderStatus != null) {
            return tenderStatus;
        }
        if (CollectionUtils.isEmpty(this.products)) {
            return P2PProductStatus.REVIEW_PAWNAGE;
        }
        final int[] livePawnage = new int[1];
        this.products.stream().forEach(p2PProduct -> {
            if (P2PProductStatus.LIVE_PAWNAGE == p2PProduct.getPawnageStatus()) {
                livePawnage[0] = P2PProductStatus.LIVE_PAWNAGE;
                return;
            }
        });
        if (livePawnage.length == 0) {
            if (((new Date().getTime() / 1000) - (dueDate.getTime() / 1000)) / (1000 * 60 * 60 * 24) <= 0) {
                return P2PProductStatus.DEAD_PAWNAGE;
            }
            return P2PProductStatus.REDEEM_PAWNAGE;
        }
        return livePawnage[0];
    }

    public void setTenderStatus(Integer tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    private Integer tenderStatus;

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

    public Integer getProductQuantity() {
        Integer total = 0;
        if (!ObjectUtils.isEmpty(this.products)) {
            for (P2PProduct product : products) {
                total = total + product.getOriginStock();
            }
        }
        return total;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
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
        List<P2PProduct> products = this.products;
        Double tenderTotal = 0.0;
        if (!ObjectUtils.isEmpty(products)) {
            for (P2PProduct product : products) {
                tenderTotal = tenderTotal + product.getOriginStock() * product.getSalePrice();
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

    public List<P2PProduct> getProducts() {
        return products;
    }

    public void setProducts(List<P2PProduct> products) {
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

    public Integer getProductResidue() {

        Integer residue = 0;
        if (!ObjectUtils.isEmpty(this.products)) {
            for (Product product : products) {
                residue = residue + product.getStock();
            }
        }
        return residue;
    }

    public void setProductResidue(Integer productResidue) {
        this.productResidue = productResidue;
    }
}
