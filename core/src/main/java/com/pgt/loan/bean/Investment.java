package com.pgt.loan.bean;

import java.math.BigDecimal;
import java.util.Date;

import com.pgt.user.bean.User;


/**
 * 
 * @author Samli
 *
 */
public class Investment {
	
	private int investId;
	private User investor;
	private Loan loan;
	private BigDecimal investAmount;
	private int status;
	private BigDecimal incomingAmount;
	private Date creationDate;
	private Date updateDate;
	private Date settleStartTime;

	public int getInvestId() {
		return investId;
	}

	public void setInvestId(int investId) {
		this.investId = investId;
	}

	public User getInvestor() {
		return investor;
	}

	public void setInvestor(User investor) {
		this.investor = investor;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigDecimal getIncomingAmount() {
		return incomingAmount;
	}

	public void setIncomingAmount(BigDecimal incomingAmount) {
		this.incomingAmount = incomingAmount;
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

	public Date getSettleStartTime() {
		return settleStartTime;
	}

	public void setSettleStartTime(Date settleStartTime) {
		this.settleStartTime = settleStartTime;
	}

}
