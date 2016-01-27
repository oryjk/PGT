package com.pgt.style.service;

import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;

import java.util.List;

/**
 * Created by xiaodong on 16-1-13.
 */
public interface PageBackgroundService {

    /**
     *  create PageBackground
     * @param pageBackground
     */
    void createPageBackground (PageBackground pageBackground);

    /**
     * update PageBackground
     * @param pageBackground
     */
    void updatePageBackground(PageBackground pageBackground);

    /**
     * delete a PageBackground
     * @param id
     */
    void deletePageBackgroundById(Integer id);

    void deletePageBackgroundByKes(Integer[] ids);

    /**
     * query PageBackground
     * @param pageBackgroundQuery
     * @return
     */
    List<PageBackground> queryPageBackground(PageBackgroundQuery pageBackgroundQuery);

    /**
     * query PageBackgroundCount
     * @param pageBackgroundQuery
     * @return
     */
    Integer queryPageBackgroundCount(PageBackgroundQuery pageBackgroundQuery);

}
