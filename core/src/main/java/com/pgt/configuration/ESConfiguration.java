package com.pgt.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 11/30/15.
 */
public class ESConfiguration {
    private String host = "localhost";
    private Integer indexPort = 9300;
    private Integer searchPort = 9300;
    private List<String> analyzerFields = new ArrayList<>();
    private List<String> productAnalyzerFields = new ArrayList<>();
    private List<String> categoryAnalyzerFields = new ArrayList<>();
    private List<String> hotSaleAnalyzerFields = new ArrayList<>();
    private boolean clearIndex=true;
    private boolean needIndex=true;
    private List<String> useToSearch=new ArrayList<>();

    private Map<String, Integer> priceAggrs = new HashMap<String, Integer>() {
        {
            //TODO
            put("0-999", 999);
            put("-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
            put("0-999", 999);
        }
    };

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getSearchPort() {
        return searchPort;
    }

    public void setSearchPort(Integer searchPort) {
        this.searchPort = searchPort;
    }

    public Integer getIndexPort() {
        return indexPort;
    }

    public void setIndexPort(Integer indexPort) {
        this.indexPort = indexPort;
    }

    public List<String> getAnalyzerFields() {
        return analyzerFields;
    }

    public void setAnalyzerFields(List<String> analyzerFields) {
        this.analyzerFields = analyzerFields;
    }


    public boolean isClearIndex() {
        return clearIndex;
    }

    public void setClearIndex(boolean clearIndex) {
        this.clearIndex = clearIndex;
    }

    public boolean isNeedIndex() {
        return needIndex;
    }

    public void setNeedIndex(boolean needIndex) {
        this.needIndex = needIndex;
    }


    public List<String> getProductAnalyzerFields() {
        return productAnalyzerFields;
    }

    public void setProductAnalyzerFields(List<String> productAnalyzerFields) {
        this.productAnalyzerFields = productAnalyzerFields;
    }

    public List<String> getCategoryAnalyzerFields() {
        return categoryAnalyzerFields;
    }

    public void setCategoryAnalyzerFields(List<String> categoryAnalyzerFields) {
        this.categoryAnalyzerFields = categoryAnalyzerFields;
    }

    public List<String> getHotSaleAnalyzerFields() {
        return hotSaleAnalyzerFields;
    }

    public void setHotSaleAnalyzerFields(List<String> hotSaleAnalyzerFields) {
        this.hotSaleAnalyzerFields = hotSaleAnalyzerFields;
    }

    public List<String> getUseToSearch() {
        return useToSearch;
    }

    public void setUseToSearch(List<String> useToSearch) {
        this.useToSearch = useToSearch;
    }
}
