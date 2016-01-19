package com.pgt.tender.service;

import com.pgt.tender.bean.Tender;
import com.pgt.tender.mapper.TenderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 1/16/16.
 */
@Service
public class TenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderService.class);

    @Autowired
    private TenderMapper tenderMapper;


    public Tender queryTender(Integer tenderId, Boolean onlyActive) {
        LOGGER.debug("The tender id is {},onlyActive is {}.", tenderId, onlyActive);
        Tender tender = tenderMapper.queryTenderById(tenderId, onlyActive);
        LOGGER.debug("The tender is {}.", tender);
        return tender;
    }

    public List<Tender> queryTendersByCategoryId(Integer categoryId, Boolean onlyActive) {
        LOGGER.debug("The category id is {},onlyActive is {}.", categoryId, onlyActive);
        List<Tender> tenders = tenderMapper.queryTendersByCategoryId(categoryId, onlyActive);
        if (ObjectUtils.isEmpty(tenders)) {
            LOGGER.debug("Can not find tender with category id is {}.", categoryId);
            return tenders;
        }
        LOGGER.debug("Find {} tenders with category id is {}.", tenders.size(), categoryId);
        return tenders;
    }

    public Integer createTender(Tender tender) {
        LOGGER.debug("Begin to create tender.");
        tenderMapper.createTender(tender);
        LOGGER.debug("The new tender id is {}.", tender.getTenderId());
        return tender.getTenderId();
    }


    public Integer updateTender(Tender tender) {
        LOGGER.debug("Begin to update tender,the tender id is {}.", tender.getTenderId());
        tenderMapper.updateTender(tender);
        LOGGER.debug("Success for update tender.");
        return tender.getTenderId();
    }

    public Integer deleteTender(Integer tenderId) {
        LOGGER.debug("Begin to delete tender with id is {}.", tenderId);
        tenderMapper.deleteTender(tenderId);
        LOGGER.debug("Success for delete tender.");
        return tenderId;
    }

}
