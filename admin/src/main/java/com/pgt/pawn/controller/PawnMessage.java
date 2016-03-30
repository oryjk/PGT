package com.pgt.pawn.controller;

/**
 * Created by Yove on 16/2/7.
 */
public interface PawnMessage {

	String ERROR_SHOP_OWNER_ID_INVALID = "Error.shop.owner.ownerIdInvalid";
	String ERROR_SHOP_NAME_INVALID = "Error.shop.name.shopNameInvalid";
	String ERROR_SHOP_NAME_DUPLICATE = "Error.shop.name.shopNameDuplicate";

	String ERROR_TICKET_NUMBER_INVALID = "Error.ticket.number.invalid";
	String ERROR_TICKET_NUMBER_DUPLICATE = "Error.ticket.number.duplicate";
	String ERROR_TICKET_SHOP_INVALID = "Error.ticket.shop.invalid";

	String ERROR_TICKET_REL_NOT_MATCH = "Error.ticket.rel.ticketNotMatchUser";

	String ERROR_INVEST_PERMISSION_DENIED = "Error.invest.permission.permissionDenied";
}
