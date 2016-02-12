package com.pgt.pawn.service;

import org.springframework.stereotype.Service;

/**
 * Created by jeniss on 16/2/7.
 */
@Service(value = "pawnRelatedValidationService")
public class PawnRelatedValidationService {

	public boolean checkPawnShopName(final String pName) {
		// FIXME
		return false;
	}

	public boolean checkPawnTicketNumber(final String pNumber) {
		// FIXME
		return false;
	}

	public boolean checkPawnShopNameUniqueness(final String pName) {
		return false;
	}

	public boolean checkPawnShopExistence(final String pNumber) {
		return false;
	}

	public boolean checkPawnTicketNumberUniquess(final String pNumber) {
		return false;
	}
}
