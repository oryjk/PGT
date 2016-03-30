package com.pgt.pawn.service;

import com.pgt.pawn.dao.PawnDao;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Yove on 16/2/7.
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

	public boolean checkPawnShopNameUniqueness(final String pName, Integer pCurrentShopId) {
		return getPawnDao().queryPawnShopCountForName(pName, pCurrentShopId) <= 0;
	}

	public boolean checkPawnShopExistence(final int pShopId) {
		return getPawnDao().loadPawnShop(pShopId) != null;
	}

	public boolean checkPawnTicketNumberUniqueness(final String pNumber, Integer pCurrentTicketId) {
		return getPawnDao().queryPawnTicketCountForNumber(pNumber, pCurrentTicketId) <= 0;
	}

	public boolean checkPawnTicketsMatchOwner(final int[] pTicketIds, final int pOwnerId) {
		if (ArrayUtils.isEmpty(pTicketIds)) {
			return false;
		}
		// check is all tickets belongs to owner
		int matchedTicketCount = getPawnDao().queryTicketCountForOwnerWithIds(pTicketIds, pOwnerId);
		return matchedTicketCount == pTicketIds.length;
	}

	public PawnDao getPawnDao() {
		return mPawnDao;
	}

	public void setPawnDao(final PawnDao pPawnDao) {
		mPawnDao = pPawnDao;
	}
}
