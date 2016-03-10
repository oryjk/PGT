package com.pgt.tender.mapper;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.product.bean.Product;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.bean.TenderMedia;
import com.pgt.tender.bean.TenderQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 1/16/16.
 */
@Component
public interface TenderMapper extends SqlMapper {

    Tender queryTenderById(@Param("tenderId") Integer tenderId, @Param("onlyActive") Boolean onlyActive);

    List<Tender> queryTendersByCategoryId(@Param("categoryId") Integer categoryId, @Param("onlyActive") Boolean onlyActive);

    List<Tender> queryTenders(Tender tender);

    void createTender(Tender tender);

    void updateTender(Tender tender);

    void deleteTender(@Param("tenderId") Integer tenderId);

    List<Tender> queryAllTender();

    List<Tender> queryTenderByQuery (TenderQuery tenderQuery);

    TenderMedia queryTenderP2PFrontMedia(@Param("tenderId") Integer tenderId);


    TenderMedia queryTenderP2PAdvertisement(@Param("tenderId") Integer tenderId);


    List<TenderMedia> queryTenderP2PHeroMedia(@Param("tenderId") Integer tenderId);

    List<TenderMedia> queryTenderP2PMainMedia(@Param("tenderId") Integer tenderId);


    TenderMedia queryTenderP2PExpertMedia(@Param("tenderId") Integer tenderId);

    TenderMedia queryTenderMobileDetailMedia(@Param("tenderId") Integer tenderId);


    List<Product> queryTenderProduct(int tenderId);

    int getTenderOrderCount(int tenderId);

    List<Map<String,Object>> getBuyers(int tenderId);

}
