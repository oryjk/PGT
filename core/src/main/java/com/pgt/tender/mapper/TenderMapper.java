package com.pgt.tender.mapper;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.tender.bean.Tender;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by carlwang on 1/16/16.
 */
public interface TenderMapper extends SqlMapper {

    Tender queryTenderById(@Param("tenderId") Integer tenderId, @Param("onlyActive") Boolean onlyActive);

    List<Tender> queryTendersByCategoryId(@Param("categoryId") Integer categoryId, @Param("onlyActive") Boolean onlyActive);

    void createTender(Tender tender);

    void updateTender(Tender tender);

    void deleteTender(@Param("tenderId") Integer tenderId);

}
