package com.pgt.link.bean;

import com.pgt.utils.PaginationBean;

/**
 * Created by zhangxiaodong on 16-2-24.
 */
public class FriendlyLink implements  FriendlyLinkType{

    private Integer id;
    private String name;
    private String link;
    private String type;
    private int state = INITIAL;

    private PaginationBean paginationBean;


    public PaginationBean getPaginationBean() {
        return paginationBean;
    }

    public void setPaginationBean(PaginationBean paginationBean) {
        this.paginationBean = paginationBean;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
