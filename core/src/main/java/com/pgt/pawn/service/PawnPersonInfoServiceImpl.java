package com.pgt.pawn.service;

import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.bean.PawnPersonInfoQuery;
import com.pgt.pawn.dao.PawnPersonInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
@Service
@Transactional
public class PawnPersonInfoServiceImpl implements PawnPersonInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PawnPersonInfoService.class);

    @Autowired
    private PawnPersonInfoMapper pawnPersonInfoMapper;

    @Override
    public PawnPersonInfo queryPawnPersonInfoById(Integer id) {
        LOGGER.debug("The method queryPawnPersonInfo");
        if(ObjectUtils.isEmpty(id)){
            LOGGER.debug("The queryPawnPersonInfo id is empty");
            return null;
        }
        return pawnPersonInfoMapper.queryPawnPersonInfoById(id);
    }

    @Override
    public List<PawnPersonInfo> queryPawnPersonInfo(PawnPersonInfoQuery pawnPersonInfoQuery) {
        return pawnPersonInfoMapper.queryPawnPersonInfo(pawnPersonInfoQuery);
    }

    @Override
    public void deletePawnPersonInfoById(Integer id) {
        LOGGER.debug("The method deletePawnPerson");
        if(ObjectUtils.isEmpty(id)){
            LOGGER.debug("The queryPawnPersonInfo id is empty");
            return;
        }
        LOGGER.debug("The delete id is {}",id);
        pawnPersonInfoMapper.deletePawnPersonInfoById(id);
    }

    @Override
    public void updatePawnPersonInfo(PawnPersonInfo pawnPersonInfo) {
        LOGGER.debug("The method updatePawnPersonInfo");
        if(ObjectUtils.isEmpty(pawnPersonInfo)){
            LOGGER.debug("The pawnPersonInfo is empty");
            return;
        }
        pawnPersonInfoMapper.updatePawnPersonInfo(pawnPersonInfo);
        LOGGER.debug("The update pawnPersonInfo is is ",pawnPersonInfo.getId());
    }

    @Override
    public void createPawnPersonInfo(PawnPersonInfo pawnPersonInfo) {
        LOGGER.debug("The method createPawnPersonInfo");
        if(ObjectUtils.isEmpty(pawnPersonInfo)){
            LOGGER.debug("The pawnPersonInfo is empty");
            return;
        }
        pawnPersonInfoMapper.createPawnPersonInfo(pawnPersonInfo);
        LOGGER.debug("The create pawnPersonInfo id is",pawnPersonInfo.getId());
    }
}
