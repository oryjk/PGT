package com.pgt.pawn.service;

import com.pgt.pawn.dao.PawnDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jeniss on 16/2/7.
 */
// FIXME
@Service(value = "pawnRelatedValidationService")
public class PawnRelatedValidationService {

	@Resource(name = "pawnDao")
	private PawnDao mPawnDao;

	public boolean checkPawnShopName(final String pName) {
		return StringUtils.isNoneBlank(pName) && pName.getBytes().length <= 200;
	}

	public boolean checkPawnTicketNumber(final String pNumber) {
		return StringUtils.isNoneBlank(pNumber) && pNumber.getBytes().length <= 100;
	}

	public boolean checkPawnShopNameUniqueness(final String pName) {
		return false;
	}

	public boolean checkPawnShopExistence(final String pShopId) {
		return false;
	}

	public boolean checkPawnTicketNumberUniqueness(final String pNumber) {
		return false;
	}

	public boolean checkPawnTicketsMatchOwner(final int[] pTicketIds, final int pOwnerId) {
		// check is all tickets belongs to owner
		return false;
	}

	public PawnDao getPawnDao() {
		return mPawnDao;
	}

	public void setPawnDao(final PawnDao pPawnDao) {
		mPawnDao = pPawnDao;
	}
}
