package com.pgt.tender.mapper;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.tender.bean.TenderCategory;
import org.elasticsearch.common.collect.HppcMaps;
import org.springframework.stereotype.Component;

/**
 * Created by carlwang on 1/20/16.
 */
@Component
public interface TenderCategoryMapper extends SqlMapper {
    void createTenderCategory(TenderCategory tenderCategory);

    void deleteTenderCategory(Integer id);

    void updateTenderCategory(TenderCategory tenderCategory);

    TenderCategory findTenderCategoryById(Integer id);

    TenderCategory findTenderByTenderIdAndCategoryId(TenderCategory tenderCategory);
}
