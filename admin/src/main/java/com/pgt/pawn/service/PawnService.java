package com.pgt.pawn.service;

import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.pawn.bean.PawnTicket;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.dao.PawnDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jeniss on 16/2/7.
 */
@Service(value = "pawnService")
public class PawnService {


	private PawnDao mPawnDao;

	public boolean persistPawnShop(final Pawnshop pPawnshop) {
		if (RepositoryUtils.idIsValid(pPawnshop.getPawnshopId())) {
			return getPawnDao().updatePawnShop(pPawnshop) > 0;
		} else {
			return getPawnDao().createPawnShop(pPawnshop) > 0;
		}
	}

	public void queryPawnTicketPage(final InternalPagination pPagination, final int pOwnerId) {
		long count = getPawnDao().queryPawnTicketCount(pPagination, pOwnerId);
		pPagination.setCapacity(count);
		if (count > 0) {
			List<PawnTicket> tickets = getPawnDao().queryPawnTicketPage(pPagination, pOwnerId);
			pPagination.setResult(tickets);
		}
	}

	public Pawnshop loadPawnshop(final int pShopId) {
		return getPawnDao().loadPawnShop(pShopId);
	}

	public boolean persistPawnTicket(final PawnTicket pPawnTicket) {
		if (RepositoryUtils.idIsValid(pPawnTicket.getPawnTicketId())) {
			return getPawnDao().updatePawnTicket(pPawnTicket) > 0;
		} else {
			return getPawnDao().createPawnTicket(pPawnTicket) > 0;
		}
	}


	public PawnTicket loadPawnTicket(final int pTicketId) {
		return getPawnDao().loadPawnTicket(pTicketId);
	}

	public boolean updateBatchPawnTicketStatus(final int[] pTicketIds, final boolean pStatus) {
		return getPawnDao().updateBatchPawnTicketStatus(pTicketIds, pStatus) > 0;
	}

	public PawnDao getPawnDao() {
		return mPawnDao;
	}

	public void setPawnDao(final PawnDao pPawnDao) {
		mPawnDao = pPawnDao;
	}
}
