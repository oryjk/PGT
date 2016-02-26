package com.pgt.product.bean;

/**
 * Created by carlwang on 2/25/16.
 */
public class P2PProduct extends Product {


    private int pawnageStatus = P2PProductStatus.LIVE_PAWNAGE;
    /**
     * Never change when create. This is origin value
     */
    private int originStock;


    public int getPawnageStatus() {
        return pawnageStatus;
    }

    public void setPawnageStatus(int pawnageStatus) {
        this.pawnageStatus = pawnageStatus;
    }

    public int getOriginStock() {
        return originStock;
    }

    public void setOriginStock(int originStock) {
        this.originStock = originStock;
    }
}
