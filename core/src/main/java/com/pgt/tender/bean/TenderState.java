package com.pgt.tender.bean;

/**
 * Created by Yove on 16/2/13.
 */
public interface TenderState {

    int INITIAL = 1;

    //在当期状态
    int LIVE_PAWNAGE = 20;

    //绝当状态
    int DEAD_PAWNAGE = 30;


    //赎当状态
    int REDEEM_PAWNAGE = 40;

    int HIDE = 0;

    int DELETED = -1;


}
