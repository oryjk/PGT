package com.pgt.common.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.common.bean.Media;
import com.pgt.hot.bean.HotSearch;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xiaodong on 16-1-30.
 */
@Component
public interface HotSearchMapper extends SqlMapper {


      void createHotSearch(HotSearch hotSearch);

      void updateHotSearch(HotSearch hotSearch);

      void deleteHotSearchById(Integer id);

      List<HotSearch> queryHotSearchByQuery(HotSearch hotSearch);

      HotSearch  queryHotSearchById(Integer hotSearchId);

      Media queryHotProductFrontMedia(Integer hotSearchId);
}
