package com.pgt.loan.bean;

import com.pgt.category.bean.Category;
import com.pgt.user.bean.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author Samli
 *
 */
public class Loan {

	private Long loanId;
	private User loaner;
	private LoanPeriod loanPeriod;
	private SettlementCycle settlementCycle;
	private String name;
	private BigDecimal earingsRate;
	private BigDecimal serviceFeeRate;
	private Date collectionEndDate;
	private BigDecimal amount;
	private BigDecimal investAmountMin;
	private BigDecimal investAmountMax;
	private int counterPurchase;
//	private InternalUser publisher;
	private int status;
	private BigDecimal refundAmount;
	private Date createionDate;
	private Date updateDate;
	private String description;
	private boolean isMockData;
	private Category category;
	private boolean isPopulate;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public User getLoaner() {
		return loaner;
	}

	public void setLoaner(User loaner) {
		this.loaner = loaner;
	}

	public LoanPeriod getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(LoanPeriod loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public SettlementCycle getSettlementCycle() {
		return settlementCycle;
	}

	public void setSettlementCycle(SettlementCycle settlementCycle) {
		this.settlementCycle = settlementCycle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getEaringsRate() {
		return earingsRate;
	}

	public void setEaringsRate(BigDecimal earingsRate) {
		this.earingsRate = earingsRate;
	}

	public BigDecimal getServiceFeeRate() {
		return serviceFeeRate;
	}

	public void setServiceFeeRate(BigDecimal serviceFeeRate) {
		this.serviceFeeRate = serviceFeeRate;
	}

	public Date getCollectionEndDate() {
		return collectionEndDate;
	}

	public void setCollectionEndDate(Date collectionEndDate) {
		this.collectionEndDate = collectionEndDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getInvestAmountMin() {
		return investAmountMin;
	}

	public void setInvestAmountMin(BigDecimal investAmountMin) {
		this.investAmountMin = investAmountMin;
	}

	public BigDecimal getInvestAmountMax() {
		return investAmountMax;
	}

	public void setInvestAmountMax(BigDecimal investAmountMax) {
		this.investAmountMax = investAmountMax;
	}

	public int getCounterPurchase() {
		return counterPurchase;
	}

	public void setCounterPurchase(int counterPurchase) {
		this.counterPurchase = counterPurchase;
	}

//	public InternalUser getPublisher() {
//		return publisher;
//	}
//
//	public void setPublisher(InternalUser publisher) {
//		this.publisher = publisher;
//	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Date getCreateionDate() {
		return createionDate;
	}

	public void setCreateionDate(Date createionDate) {
		this.createionDate = createionDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isMockData() {
		return isMockData;
	}

	public void setMockData(boolean isMockData) {
		this.isMockData = isMockData;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isPopulate() {
		return isPopulate;
	}

	public void setPopulate(boolean isPopulate) {
		this.isPopulate = isPopulate;
	}

}
