package com.pgt.inventory;

import java.util.Set;

/**
 * Created by Administrator on 2015/12/25.
 */
public class LockInventoryException extends Exception {
    private Set<Integer> oosProductIds;

    public Set<Integer> getOosProductIds() {
        return oosProductIds;
    }

    public void setOosProductIds(Set<Integer> oosProductIds) {
        this.oosProductIds = oosProductIds;
    }

    /**
     *
     */
    private static final long serialVersionUID = -3801720994352586482L;

    public LockInventoryException() {
        super();
    }

    public LockInventoryException(String error) {
        super(error);
    }

    public LockInventoryException(Throwable throwable) {
        super(throwable);
    }


    public LockInventoryException(String error, Throwable throwable) {
        super(error);
    }

}
