package com.pgt.pawn.controller;

import com.pgt.cart.bean.ResponseBuilder;
import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.service.ResponseBuilderFactory;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.bean.Role;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.pawn.bean.PawnTicket;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.service.PawnRelatedValidationService;
import com.pgt.pawn.service.PawnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * Created by jeniss on 16/2/7.
 */
@RestController
@RequestMapping("/pawn")
public class PawnController extends InternalTransactionBaseController implements PawnProperties, PawnMessage {

	public static final String PERMISSION_DENIED_INVEST = "permission-denied-invest";
	public static final String VIEW_PAWN_SHOP = "/pawn/pawn-shop";
	public static final String VIEW_PAWN_SHOPS = "/pawn/pawn-shops";
	public static final String REDIRECT_PAWN_SHOP_WITHOUT_ID = "redirect:/pawn/update-pawn-shop?shopId=";
	public static final String VIEW_PAWN_TICKET = "/pawn/pawn-ticket";
	public static final String VIEW_PAWN_TICKETS = "/pawn/pawn-tickets";
	public static final String REDIRECT_PAWN_TICKET_WITHOUT_ID = "redirect:/pawn/update-pawn-ticket?ticketId=";

	private static final Logger LOGGER = LoggerFactory.getLogger(PawnController.class);

	@Resource(name = "pawnService")
	private PawnService mPawnService;

	@Resource(name = "pawnRelatedValidationService")
	private PawnRelatedValidationService mPawnRelatedValidationService;

	@Resource(name = "responseBuilderFactory")
	private ResponseBuilderFactory mResponseBuilderFactory;

	@RequestMapping(value = "/pawn-shop-list", method = RequestMethod.GET)
	public ModelAndView listPawnshops(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                  @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
	                                  @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
	                                  @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
	                                  @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc,
	                                  @RequestParam(value = "keyword", required = false) String keyword) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		// main logic
		long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
		long caLong = RepositoryUtils.safeParse2LongId(capacity);
		LOGGER.debug("Query pawn shops for user: {} at index: {} with capacity: {}, keyword: {} by sort filed: {} and asc: {}", iu.getId(),
				ciLong, caLong, keyword, sortFieldName, asc);

		InternalPaginationBuilder ipb = new InternalPaginationBuilder();
		InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword).createInternalPagination();

		int queryUserId = iu.getId();
		// override user id to query all result set for admin
		if (verifyPermissionForAdmin(pRequest)) {
			LOGGER.debug("Query all pawn shops for admin internal user: {}", iu.getId());
			queryUserId = ADMIN_QUERY_IU_ID;
		}
		getPawnService().queryPawnShopPage(pagination, queryUserId);

		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOPS);
		mav.addObject(ResponseConstant.PAWN_SHOPS_PAGE, pagination);
		return mav;
	}

	@RequestMapping(value = "/update-pawn-shop", method = RequestMethod.POST)
	public ModelAndView persistPawnShop(HttpServletRequest pRequest, HttpServletResponse pResponse, Pawnshop pPawnshop) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOP);
		mav.addObject(ResponseConstant.PAWN_SHOP, pPawnshop);

		// InternalUser iu = getCurrentInternalUser(pRequest);
		// pPawnshop.setManagerId(iu.getId());

		// pre validation
		if (!RepositoryUtils.idIsValid(pPawnshop.getOwnerId())) {
			LOGGER.debug("Cannot find pawn shop with valid owner id: {}", pPawnshop.getOwnerId());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_SHOP_OWNER_ID_INVALID);
			return mav;
		}
		if (!getPawnRelatedValidationService().checkPawnShopName(pPawnshop.getName())) {
			LOGGER.debug("Persist pawn shop with an invalid shop name: {}", pPawnshop.getName());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_SHOP_NAME_INVALID);
			return mav;
		}
		if (!getPawnRelatedValidationService().checkPawnShopNameUniqueness(pPawnshop.getName(), pPawnshop.getPawnshopId())) {
			LOGGER.debug("Persist pawn shop with an duplicate shop name: {}", pPawnshop.getName());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_SHOP_NAME_DUPLICATE);
			return mav;
		}
		// persist
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getPawnService().persistPawnShop(pPawnshop);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOGGER.error("Cannot persist pawn shop and rollback transaction", e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
			if (!status.isRollbackOnly()) {
				mav.setViewName(REDIRECT_PAWN_SHOP_WITHOUT_ID + pPawnshop.getPawnshopId());
				mav.addObject(ResponseConstant.SUCCESS, 1);
			}
		}
		return mav;
	}

	@RequestMapping(value = "/update-pawn-shop", method = RequestMethod.GET)
	public ModelAndView updatePawnShop(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                   @RequestParam(value = "shopId", required = false, defaultValue = "0") int pShopId) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOP);

		if (RepositoryUtils.idIsValid(pShopId)) {
			// update pawn shop with valid id
			Pawnshop pawnshop = getPawnService().loadPawnshop(pShopId);
			if (iu.getId() != pawnshop.getOwnerId()) {
				LOGGER.debug("The pawn shop: {}({}) is not belongs to current user: {}", pawnshop.getName(), pawnshop.getPawnshopId(),
						iu.getId());
				// redirect page to investment
				mav.setViewName(PERMISSION_DENIED_INVEST);
				mav.addObject(ResponseConstant.ERROR_MSG, ERROR_INVEST_PERMISSION_DENIED);
				return mav;
			}
			mav.addObject(ResponseConstant.PAWN_SHOP, pawnshop);
		}
		return mav;
	}

	@RequestMapping(value = "/pawn-ticket-list", method = RequestMethod.GET)
	public ModelAndView listPawnTickets(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                    @RequestParam(value = "currentIndex", required = false, defaultValue = "0") String currentIndex,
	                                    @RequestParam(value = "capacity", required = false, defaultValue = "5") String capacity,
	                                    @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
	                                    @RequestParam(value = "asc", required = false, defaultValue = "true") boolean asc,
	                                    @RequestParam(value = "keyword", required = false) String keyword) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		// main logic
		long ciLong = RepositoryUtils.safeParse2LongId(currentIndex);
		long caLong = RepositoryUtils.safeParse2LongId(capacity);
		LOGGER.debug("Query pawn tickets for user: {} at index: {} with capacity: {}, keyword: {} by sort filed: {} and asc: {}",
				iu.getId(), ciLong, caLong, keyword, sortFieldName, asc);

		InternalPaginationBuilder ipb = new InternalPaginationBuilder();
		InternalPagination pagination = ipb.setCurrentIndex(ciLong).setCapacity(caLong).setKeyword(keyword).createInternalPagination();
		getPawnService().queryPawnTicketPage(pagination, iu.getId());

		ModelAndView mav = new ModelAndView(VIEW_PAWN_TICKETS);
		mav.addObject(ResponseConstant.PAWN_TICKET_PAGE, pagination);
		return mav;
	}

	@RequestMapping(value = "/pawn-ticket-update", method = RequestMethod.GET)
	public ModelAndView updatePawnTicket(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                     @RequestParam(value = "ticketId", required = false, defaultValue = "0") int pTicketId) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOP);
		if (RepositoryUtils.idIsValid(pTicketId)) {
			// update pawn ticket with valid id
			PawnTicket pawnTicket = getPawnService().loadPawnTicket(pTicketId);
			if (iu.getId() != pawnTicket.getPawnshop().getOwnerId()) {
				LOGGER.debug("Current request pawn ticket: {} is owned by pawn shop: {}({}) is not belongs to current user: {}", pTicketId,
						pawnTicket.getPawnshop().getName(), pawnTicket.getPawnshop().getPawnshopId(), iu.getId());
				// redirect page to investment
				mav.setViewName(PERMISSION_DENIED_INVEST);
				mav.addObject(ResponseConstant.ERROR_MSG, ERROR_INVEST_PERMISSION_DENIED);
				return mav;
			}
			mav.addObject(ResponseConstant.PAWN_TICKET, pawnTicket);
		}
		return mav;
	}

	@RequestMapping(value = "/pawn-ticket-update", method = RequestMethod.POST)
	public ModelAndView persistPawnTicket(HttpServletRequest pRequest, HttpServletResponse pResponse, PawnTicket pPawnTicket) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		ModelAndView mav = new ModelAndView(VIEW_PAWN_TICKET);
		mav.addObject(ResponseConstant.PAWN_TICKET, pPawnTicket);
		// pre validation
		if (!getPawnRelatedValidationService().checkPawnTicketNumber(pPawnTicket.getNumber())) {
			LOGGER.debug("Persist pawn ticket with an invalid ticket number: {}", pPawnTicket.getNumber());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_TICKET_NUMBER_INVALID);
			return mav;
		}
		if (!getPawnRelatedValidationService().checkPawnTicketNumberUniqueness(pPawnTicket.getNumber(), pPawnTicket.getPawnTicketId())) {
			LOGGER.debug("Persist pawn ticket with an duplicate ticket number: {}", pPawnTicket.getNumber());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_TICKET_NUMBER_DUPLICATE);
			return mav;
		}
		if (!getPawnRelatedValidationService().checkPawnShopExistence(pPawnTicket.getPawnshopId())) {
			LOGGER.debug("Persist pawn ticket with an invalid pawn shop: {}", pPawnTicket.getPawnshopId());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_TICKET_SHOP_INVALID);
			return mav;
		}
		// persist
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getPawnService().persistPawnTicket(pPawnTicket);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOGGER.error("Cannot persist pawn ticket and roll back transaction.", e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
			if (!status.isRollbackOnly()) {
				mav.setViewName(REDIRECT_PAWN_TICKET_WITHOUT_ID + pPawnTicket.getPawnTicketId());
			}
		}
		return mav;
	}

	@RequestMapping(value = "/pawn-tickets-status-update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity updatePawnTicketsStatus(HttpServletRequest pRequest, HttpServletResponse pResponse,
	                                              @RequestParam(value = "ticketIds", required = true) int[] pTicketIds,
	                                              @RequestParam(value = "ticketStatue", required = true) boolean pStatus) {
		ResponseBuilder rb = getResponseBuilderFactory().buildResponseBean().setSuccess(false);
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ResponseEntity(rb.createResponse(), HttpStatus.FORBIDDEN);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		// pre validation
		if (getPawnRelatedValidationService().checkPawnTicketsMatchOwner(pTicketIds, iu.getId())) {
			LOGGER.debug("Update status of pawn tickets: {}, but not all pawn tickets belongs to current user: {}",
					Arrays.toString(pTicketIds), iu.getId());
			rb.addErrorMessage(PAWN_TICKET_IDS, ERROR_TICKET_REL_NOT_MATCH);
			return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
		}
		// update & persist
		TransactionStatus status = ensureTransaction();
		try {
			boolean result = getPawnService().updateBatchPawnTicketStatus(pTicketIds, pStatus);
			if (!result) {
				status.setRollbackOnly();
			}
		} catch (Exception e) {
			LOGGER.error("Cannot update pawn tickets status and rollback transaction.", e);
			status.setRollbackOnly();
		} finally {
			getTransactionManager().commit(status);
			if (!status.isRollbackOnly()) {
				rb.setSuccess(true);
			}
		}
		return new ResponseEntity(rb.createResponse(), HttpStatus.OK);
	}

	public PawnService getPawnService() {
		return mPawnService;
	}

	public void setPawnService(final PawnService pPawnService) {
		mPawnService = pPawnService;
	}

	public PawnRelatedValidationService getPawnRelatedValidationService() {
		return mPawnRelatedValidationService;
	}

	public void setPawnRelatedValidationService(final PawnRelatedValidationService pPawnRelatedValidationService) {
		mPawnRelatedValidationService = pPawnRelatedValidationService;
	}

	public ResponseBuilderFactory getResponseBuilderFactory() {
		return mResponseBuilderFactory;
	}

	public void setResponseBuilderFactory(final ResponseBuilderFactory pResponseBuilderFactory) {
		mResponseBuilderFactory = pResponseBuilderFactory;
	}
}
