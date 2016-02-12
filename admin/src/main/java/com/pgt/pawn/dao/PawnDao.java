package com.pgt.pawn.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.pawn.bean.PawnTicket;
import com.pgt.pawn.bean.Pawnshop;
import org.springframework.stereotype.Repository;

/**
 * Created by jeniss on 16/2/10.
 */
@Repository(value = "pawnDao")
public interface PawnDao extends SqlMapper {

	int updatePawnShop(Pawnshop pPawnshop);

	int createPawnShop(Pawnshop pPawnshop);

	int queryPawnTicketCount(InternalPagination pPagination, int pUserId);

	void queryPawnTicketPage(InternalPagination pPagination, int pUserId);

	int updatePawnTicket(PawnTicket pTicket);

	int createPawnTicket(PawnTicket pTicket);

	Pawnshop loadPawnShop(int pShopId);

	PawnTicket loadPawnTicket(int pTicketId);
}
