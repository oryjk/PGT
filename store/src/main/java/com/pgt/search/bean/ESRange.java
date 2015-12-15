package com.pgt.search.bean;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESRange extends ESBase{
    private Integer from;
    private Integer to;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }
}
