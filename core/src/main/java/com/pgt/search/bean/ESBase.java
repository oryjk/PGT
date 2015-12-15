package com.pgt.search.bean;

import java.io.Serializable;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESBase implements Serializable {
    static final long serialVersionUID = 1L;
    private String propertyName;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
