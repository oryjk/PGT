package com.pgt.tender.bean;

/**
 * Created by Yove on 16/2/13.
 */
public interface TenderAuditState {

	int PENDING_APPROVAL = 0;

	int APPROVED = 1;

	int REJECTED = -1;
}
