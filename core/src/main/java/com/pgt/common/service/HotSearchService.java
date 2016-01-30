package com.pgt.common.service;

import com.pgt.hot.bean.HotSearch;

import java.util.List;

/**
 * Created by xiaodong on 16-1-30.
 */
public interface HotSearchService {

    void createHotSearch(HotSearch hotSearch);

    void updateHotSearch(HotSearch hotSearch);

    void deleteHotSearchById(Integer id);

    List<HotSearch> queryHotSearchByQuery(HotSearch hotSearch);

}
