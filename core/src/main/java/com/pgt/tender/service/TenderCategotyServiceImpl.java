package com.pgt.tender.service;

import com.pgt.tender.bean.TenderCategory;
import com.pgt.tender.mapper.TenderCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zhangxiaodong on 16-2-19.
 */

@Transactional
@Service
public class TenderCategotyServiceImpl implements TenderCategoryService{

    @Autowired
    private TenderCategoryMapper tenderCategoryMapper;

    @Override
    public void createTenderCategory(TenderCategory tenderCategory) {
        tenderCategoryMapper.createTenderCategory(tenderCategory);
    }

    @Override
    public void deleteTenderCategory(Integer id) {
        tenderCategoryMapper.deleteTenderCategory(id);
    }

    @Override
    public void updateTenderCategory(TenderCategory tenderCategory) {
        tenderCategoryMapper.updateTenderCategory(tenderCategory);
    }

    @Override
    public TenderCategory findTenderCategoryById(Integer id) {
        return tenderCategoryMapper.findTenderCategoryById(id);
    }

    @Override
    public TenderCategory findTenderByTenderIdAndCategoryId(TenderCategory tenderCategory) {
        return tenderCategoryMapper.findTenderByTenderIdAndCategoryId(tenderCategory);
    }

    @Override
    public void deleteTenderCategoryByTenderId(Integer tenderId) {
        tenderCategoryMapper.deleteTenderCategoryByTenderId(tenderId);
    }
}
