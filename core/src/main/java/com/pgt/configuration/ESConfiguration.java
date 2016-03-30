package com.pgt.configuration;

import com.pgt.category.bean.CategoryType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 11/30/15.
 */
@Component
@Scope("singleton")
public class ESConfiguration {
    private String host = "localhost";
    private Integer indexPort = 9300;
    private Integer searchPort = 9300;
    private List<String> analyzerFields = new ArrayList<>();
    private List<String> productAnalyzerFields = new ArrayList<>();
    private List<String> categoryAnalyzerFields = new ArrayList<>();
    private List<String> hotSaleAnalyzerFields = new ArrayList<>();
    private List<String> tenderAnalyzerFields = new ArrayList<>();
    private boolean clearIndex = true;
    private boolean needIndex = true;
    private List<String> useToSearch = new ArrayList<>();
    private List<String> tenderSearchProperties = new ArrayList<>();
    private int tenderListCapacity = 12;

    //index,type
    private String indexName = "mp";
    private String categoryTypeName = "category";
    private String productTypeName = "product";
    private CategoryType categoryType = CategoryType.ROOT;
    private String hotProductTypeName="hotProduct";
    //search key
    private String tenderRootCategoryIdTerm="rootCategory.id";
    private String tenderParentCategoryIdTerm="parentCategory.id";
    private String eSSort="creationDate";

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

    public String geteSSort() {
        return eSSort;
    }

    public void seteSSort(String eSSort) {
        this.eSSort = eSSort;
    }

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

    public List<String> getTenderAnalyzerFields() {
        return tenderAnalyzerFields;
    }

    public void setTenderAnalyzerFields(List<String> tenderAnalyzerFields) {
        this.tenderAnalyzerFields = tenderAnalyzerFields;
    }


    public int getTenderListCapacity() {
        return tenderListCapacity;
    }

    public void setTenderListCapacity(int tenderListCapacity) {
        this.tenderListCapacity = tenderListCapacity;
    }

    public List<String> getTenderSearchProperties() {
        return tenderSearchProperties;
    }

    public void setTenderSearchProperties(List<String> tenderSearchProperties) {
        this.tenderSearchProperties = tenderSearchProperties;
    }


    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    public String getCategoryTypeName() {
        return categoryTypeName;
    }

    public void setCategoryTypeName(String categoryTypeName) {
        this.categoryTypeName = categoryTypeName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public CategoryType getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public String getHotProductTypeName() {
        return hotProductTypeName;
    }

    public void setHotProductTypeName(String hotProductTypeName) {
        this.hotProductTypeName = hotProductTypeName;
    }

    public String getTenderRootCategoryIdTerm() {
        return tenderRootCategoryIdTerm;
    }

    public void setTenderRootCategoryIdTerm(String tenderRootCategoryIdTerm) {
        this.tenderRootCategoryIdTerm = tenderRootCategoryIdTerm;
    }

    public String getTenderParentCategoryIdTerm() {
        return tenderParentCategoryIdTerm;
    }

    public void setTenderParentCategoryIdTerm(String tenderParentCategoryIdTerm) {
        this.tenderParentCategoryIdTerm = tenderParentCategoryIdTerm;
    }
}
