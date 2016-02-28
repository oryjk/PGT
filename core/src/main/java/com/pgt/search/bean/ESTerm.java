package com.pgt.search.bean;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESTerm extends ESBase {

    public ESTerm() {
    }

    public ESTerm(String propertyName, String keyword) {
        setPropertyName(propertyName);
        setTermValue(keyword);
    }

    private String termValue;

    public String getTermValue() {
        return termValue;
    }

    public void setTermValue(String termValue) {
        this.termValue = termValue;
    }
}
