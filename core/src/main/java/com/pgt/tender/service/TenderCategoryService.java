package com.pgt.tender.service;

import com.pgt.tender.bean.TenderCategory;

/**
 * Created by zhangxiaodong on 16-2-19.
 */
public interface TenderCategoryService {

    void createTenderCategory(TenderCategory tenderCategory);

    void deleteTenderCategory(Integer id);

    void updateTenderCategory(TenderCategory tenderCategory);

    TenderCategory findTenderCategoryById(Integer id);

    TenderCategory findTenderByTenderIdAndCategoryId(TenderCategory tenderCategory);
}
