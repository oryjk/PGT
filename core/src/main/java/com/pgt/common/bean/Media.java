package com.pgt.common.bean;

import com.pgt.media.bean.MediaType;

import java.io.Serializable;

/**
 * @author zhangxiaodong 2015年12月4日
 */

public class Media implements Serializable {

    private Integer id;
    private Integer referenceId;
    private String title;
    private String path;
    private Integer index;
    private MediaType type;

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer pId) {
        id = pId;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(final Integer pReferenceId) {
        referenceId = pReferenceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String pTitle) {
        title = pTitle;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String pPath) {
        path = pPath;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(final Integer pIndex) {
        index = pIndex;
    }

}
