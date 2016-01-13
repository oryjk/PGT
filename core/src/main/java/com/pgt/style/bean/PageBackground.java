package com.pgt.style.bean;

import com.pgt.common.bean.Media;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by carlwang on 1/13/16.
 */
public class PageBackground implements Serializable {
    private Integer pageBackgroundId;
    private Media headerMedia;
    private Media middleMedia;
    private Media footerMedia;
    private Date creationDate;
    private Date updateDate;
    private Date startDate;
    private Date endDate;

    public Integer getPageBackgroundId() {
        return pageBackgroundId;
    }

    public void setPageBackgroundId(Integer pageBackgroundId) {
        this.pageBackgroundId = pageBackgroundId;
    }

    public Media getHeaderMedia() {
        return headerMedia;
    }

    public void setHeaderMedia(Media headerMedia) {
        this.headerMedia = headerMedia;
    }

    public Media getMiddleMedia() {
        return middleMedia;
    }

    public void setMiddleMedia(Media middleMedia) {
        this.middleMedia = middleMedia;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Media getFooterMedia() {
        return footerMedia;
    }

    public void setFooterMedia(Media footerMedia) {
        this.footerMedia = footerMedia;
    }
}
