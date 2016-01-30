package com.pgt.common.service;

import com.pgt.common.dao.HotSearchMapper;
import com.pgt.hot.bean.HotSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaodong on 16-1-30.
 */
@Service
public class HotSearchServiceImpl  implements HotSearchService{

    @Autowired
    private HotSearchMapper hotSearchMapper;

    @Override
    public void createHotSearch(HotSearch hotSearch) {
        hotSearchMapper.createHotSearch(hotSearch);
    }

    @Override
    public void updateHotSearch(HotSearch hotSearch) {

        hotSearchMapper.updateHotSearch(hotSearch);
    }

    @Override
    public void deleteHotSearchById(Integer id) {
       hotSearchMapper.deleteHotSearchById(id);
    }

    @Override
    public List<HotSearch> queryHotSearchByQuery(HotSearch hotSearch) {
        return hotSearchMapper.queryHotSearchByQuery(hotSearch);
    }
}
