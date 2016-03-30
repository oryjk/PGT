package com.pgt.search.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 2/26/16.
 */
public class ESMultiSort {
    private List<ESSort> esSorts = new ArrayList<>();

    public List<ESSort> getEsSorts() {
        return esSorts;
    }

    public void setEsSorts(List<ESSort> esSorts) {
        this.esSorts = esSorts;
    }
}
