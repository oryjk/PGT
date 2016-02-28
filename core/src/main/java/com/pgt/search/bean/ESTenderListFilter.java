package com.pgt.search.bean;

/**
 * Created by carlwang on 2/26/16.
 */
public class ESTenderListFilter implements ESFilter {


    public ESTenderListFilter() {
    }

    public ESTenderListFilter(String categoryId) {
        this.categoryId = categoryId;
    }

    private boolean all = true;
    private boolean beginInMinute;
    private boolean underway;
    private boolean ended;
    private String categoryId;
    private String rootCategoryId;

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isBeginInMinute() {
        return beginInMinute;
    }

    public void setBeginInMinute(boolean beginInMinute) {
        this.beginInMinute = beginInMinute;
    }

    public boolean isUnderway() {
        return underway;
    }

    public void setUnderway(boolean underway) {
        this.underway = underway;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public String getRootCategoryId() {
        return rootCategoryId;
    }

    public void setRootCategoryId(String rootCategoryId) {
        this.rootCategoryId = rootCategoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
