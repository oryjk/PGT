package com.pgt.pawn.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.pawn.bean.PawnTicket;
import com.pgt.pawn.bean.Pawnshop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yove on 16/2/10.
 */
@Repository(value = "pawnDao")
public interface PawnDao extends SqlMapper {

	int createPawnShop(Pawnshop pPawnshop);

	int updatePawnShop(Pawnshop pPawnshop);

	Pawnshop loadPawnShop(int pShopId);

	long queryPawnTicketCount(@Param("pagination") InternalPagination pPagination, @Param("queryUserId") int pQueryUserId);

	List<PawnTicket> queryPawnTicketPage(@Param("pagination") InternalPagination pPagination, @Param("queryUserId") int pQueryUserId);

	int createPawnTicket(PawnTicket pTicket);

	int updatePawnTicket(PawnTicket pTicket);

	PawnTicket loadPawnTicket(int pTicketId);

	int updateBatchPawnTicketStatus(@Param("ticketIds") int[] pTicketIds, @Param("status") boolean pStatus);

	int queryPawnShopCountForName(@Param("name") String pName, @Param("excludeShopId") Integer pExcludeShopId);

	int queryPawnTicketCountForNumber(@Param("number") String pNumber, @Param("excludeTicketId") Integer pExcludeTicketId);

	int queryTicketCountForOwnerWithIds(@Param("ticketIds") int[] pTicketIds, @Param("queryUserId") int pQueryUserId);

	List<Pawnshop> queryPawnShopForInternalUser(int pQueryUserId);

	long queryPawnShopCount(@Param("pagination") InternalPagination pPagination, @Param("queryUserId") int pQueryUserId);

	List<PawnTicket> queryPawnShopPage(@Param("pagination") InternalPagination pPagination, @Param("queryUserId") int pQueryUserId);
}
