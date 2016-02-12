package com.pgt.pawn.service;

import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.dao.PawnDao;
import org.springframework.stereotype.Service;

/**
 * Created by jeniss on 16/2/7.
 */
@Service(value = "pawnService")
public class PawnService {


	private PawnDao mPawnDao;

	public boolean persistPawnShop(final Pawnshop pPawnshop) {
		if (RepositoryUtils.idIsValid(pPawnshop.getPawnshopId())) {
			return getPawnDao().updatePawnShop(pPawnshop);
		} else {
			return getPawnDao().createPawnShop(pPawnshop);
		}
	}

	public void queryPawnTicketPage(final InternalPagination pPagination, final int pOwnerId) {
		int count = getPawnDao().queryPawnTicketCount(pPagination, pOwnerId);
		pPagination.setCapacity(count);
		if (count > 0) {
			getPawnDao().queryPawnTicketPage(pPagination, pOwnerId);
		}
	}

	public Pawnshop loadPawnshop(final int pId) {
		return null;
	}

	public PawnDao getPawnDao() {
		return mPawnDao;
	}


	public void setPawnDao(final PawnDao pPawnDao) {
		mPawnDao = pPawnDao;
	}
}
