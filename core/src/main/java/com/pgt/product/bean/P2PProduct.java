package com.pgt.product.bean;

/**
 * Created by carlwang on 2/25/16.
 */
public class P2PProduct extends Product {


    private int pawnageStatus = P2PProductStatus.LIVE_PAWNAGE;


    public int getPawnageStatus() {
        return pawnageStatus;
    }

    public void setPawnageStatus(int pawnageStatus) {
        this.pawnageStatus = pawnageStatus;
    }
}
