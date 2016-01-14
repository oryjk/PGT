package com.pgt.style.service;

import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;

import java.util.List;

/**
 * Created by xiaodong on 16-1-13.
 */
public interface PageBackgroundService {

    void createPageBackground (PageBackground pageBackground);

    void updatePageBackground(PageBackground pageBackground);

    void deletePageBackgroundById(Integer id);

    void deletePageBackgroundByKes(Integer[] ids);

    List<PageBackground> queryPageBackground(PageBackgroundQuery pageBackgroundQuery);

    Integer queryPageBackgroundCount(PageBackgroundQuery pageBackgroundQuery);

}
