package com.pgt.style.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.common.bean.Media;
import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xiaodong on 16-1-13.
 */
@Component
public interface PageBackgroundMapper extends SqlMapper{

    void createPageBackground (PageBackground pageBackground);

    void updatePageBackground(PageBackground pageBackground);

    void deletePageBackgroundById(Integer id);

    void deletePageBackgroundByKes(Integer[] ids);

    List<PageBackground>   queryPageBackground(PageBackgroundQuery pageBackgroundQuery);

    Integer queryPageBackgroundCount(PageBackgroundQuery pageBackgroundQuery);

    Media queryPageBackgroundHeaderMedia(Integer pageBackgroundId);

    Media queryPageBackgroundMiddleMedia(Integer pageBackgroundId);

    Media queryPageBackgroundFooterMedia(Integer pageBackgroundId);

}
