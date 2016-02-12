package com.pgt.pawn.controller;

import com.pgt.cart.bean.pagination.InternalPagination;
import com.pgt.cart.bean.pagination.InternalPaginationBuilder;
import com.pgt.cart.util.RepositoryUtils;
import com.pgt.internal.bean.InternalUser;
import com.pgt.internal.bean.Role;
import com.pgt.internal.constant.ResponseConstant;
import com.pgt.internal.controller.InternalTransactionBaseController;
import com.pgt.pawn.bean.Pawnshop;
import com.pgt.pawn.service.PawnRelatedValidationService;
import com.pgt.pawn.service.PawnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jeniss on 16/2/7.
 */
@RestController
@RequestMapping("/pawn")
public class PawnController extends InternalTransactionBaseController implements PawnProperties, PawnMessage {

	public static final String PERMISSION_DENIED_INVEST = "permission-denied-invest";
	public static final String VIEW_PAWN_SHOP = "/pawn/pawn-shop";
	public static final String REDIRECT_PAWN_SHOP_WITHOUT_ID = "redirect:/pawn-shop?shopId=";
	public static final String VIEW_PAWN_TICKET_LIST = "/pawn/pawn-ticket-list";

	private static final Logger LOGGER = LoggerFactory.getLogger(PawnController.class);

	@Resource(name = "pawnService")
	private PawnService mPawnService;

	@Resource(name = "pawnRelatedValidationService")
	private PawnRelatedValidationService mPawnRelatedValidationService;

	@RequestMapping(value = "/update-pawn-shop", method = RequestMethod.POST)
	public ModelAndView persistPawnShop(Pawnshop pPawnshop, HttpServletRequest pRequest, HttpServletResponse pResponse) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOP);
		mav.addObject(ResponseConstant.PAWN_SHOP, pPawnshop);
		InternalUser iu = getCurrentInternalUser(pRequest);
		pPawnshop.setManagerId(iu.getId());
		// pre validation
		if (!RepositoryUtils.idIsValid(pPawnshop.getOwnerId())) {
			LOGGER.debug("Cannot find pawn shop with valid owner id: {}", pPawnshop.getOwnerId());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_SHOP_OWNER_ID_INVALID);
			return mav;
		}
		if (getPawnRelatedValidationService().checkPawnShopName(pPawnshop.getName())) {
			LOGGER.debug("Persist pawn shop with an invalid shop name: {}", pPawnshop.getName());
			mav.addObject(ResponseConstant.ERROR_MSG, ERROR_SHOP_NAME_INVALID);
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
			}
		}
		return mav;
	}

	@RequestMapping(value = "/update-pawn-shop", method = RequestMethod.GET)
	public ModelAndView loadPawnShop(@RequestParam(value = "shopId", required = true) int pShopId, HttpServletRequest pRequest,
	                                 HttpServletResponse pResponse) {
		// permission verify
		boolean pass = verifyPermission(pRequest, Role.INVESTOR, Role.IVST_ORDER_MANAGER, Role.ADMINISTRATOR);
		if (!pass) {
			return new ModelAndView(PERMISSION_DENIED);
		}
		InternalUser iu = getCurrentInternalUser(pRequest);
		ModelAndView mav = new ModelAndView(VIEW_PAWN_SHOP);
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

		ModelAndView mav = new ModelAndView(VIEW_PAWN_TICKET_LIST);
		mav.addObject(ResponseConstant.PAWN_TICKET_PAGE, pagination);
		return mav;
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
}
