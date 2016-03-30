package com.pgt.myaccount.order.service;

/**
 * Created by Yove on 3/5/2016.
 */
public interface P2POrderSearchStatus {

	/**
	 * 全部
	 */
	int ALL = 0;

	/**
	 * 在当期
	 */
	int DURING_PAWNING           = 10;
	/**
	 * 在当期 & 未支付
	 */
	int DURING_PAWNING_UNPAID    = 11;
	/**
	 * 在当期 & 已支付
	 */
	int DURING_PAWNING_PAID      = 12;
	/**
	 * 已绝当
	 */
	int END_PAWNING              = 20;
	/**
	 * 已绝当 & 未发货
	 */
	int END_PAWNING_PENDING_SHIP = 21;
	/**
	 * 已绝当 & 已发货
	 */
	int END_PAWNING_SHIPPED      = 22;
	/**
	 * 已绝当 & 已完成
	 */
	int END_PAWNING_COMPLETE     = 29;
	/**
	 * 已赎回
	 */
	int REDEEM_PAWNING           = 30;
	/**
	 * 已赎回 & 未赔付
	 */
	int REDEEM_PAWNING_UNPAID    = 31;
	/**
	 * 已赎回 * 已赔付
	 */
	int REDEEM_PAWNING_PAID      = 32;

	int[] STATUS_COLLECTION = new int[] { ALL,
			DURING_PAWNING, DURING_PAWNING_PAID, DURING_PAWNING_UNPAID,
			END_PAWNING, END_PAWNING_COMPLETE, END_PAWNING_PENDING_SHIP, END_PAWNING_SHIPPED,
			REDEEM_PAWNING, REDEEM_PAWNING_PAID, REDEEM_PAWNING_UNPAID };
}
