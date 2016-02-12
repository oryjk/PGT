package com.pgt.pawn.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.pawn.bean.Pawnshop;
import org.springframework.stereotype.Repository;

/**
 * Created by jeniss on 16/2/10.
 */
@Repository(value = "pawnDao")
public interface PawnDao extends SqlMapper {

	boolean updatePawnShop(Pawnshop pPawnshop);

	boolean createPawnShop(Pawnshop pPawnshop);

	int queryPawnTicketCount(InternalPagination pPagination, int pUserId);

	void queryPawnTicketPage(InternalPagination pPagination, int pUserId);
}
