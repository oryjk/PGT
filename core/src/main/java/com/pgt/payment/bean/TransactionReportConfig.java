package com.pgt.payment.bean;

/**
 * Created by Administrator on 2015/12/29.
 */
public class TransactionReportConfig {

    private int rowBufferSize;
    private int dataFetchSize;

    public int getDataFetchSize() {
        return dataFetchSize;
    }

    public void setDataFetchSize(int dataFetchSize) {
        this.dataFetchSize = dataFetchSize;
    }

    public int getRowBufferSize() {
        return rowBufferSize;
    }

    public void setRowBufferSize(int rowBufferSize) {
        this.rowBufferSize = rowBufferSize;
    }
}
