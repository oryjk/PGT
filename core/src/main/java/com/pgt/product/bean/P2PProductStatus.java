package com.pgt.product.bean;

/**
 * Created by carlwang on 2/25/16.
 */
public interface P2PProductStatus {
    //在当期状态
    int LIVE_PAWNAGE = 10;

    //绝当状态
    int DEAD_PAWNAGE = 20;


    //赎当状态
    int REDEEM_PAWNAGE = 30;

    //审核阶段
    int REVIEW_PAWNAGE = -10;
}
