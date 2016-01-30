package com.pgt.pawn.service;

import com.pgt.pawn.bean.OnlinePawn;
import com.pgt.pawn.dao.OnlinePawnMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by hd on 16-1-28.
 */
@Service
public class OnlinePawnServiceImp implements OnlinePawnService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlinePawnServiceImp.class);

    @Autowired
    private OnlinePawnMapper onlinePawnMapper;

    @Override
    public Integer add(OnlinePawn onlinePawn){
        LOGGER.debug("Begin create onlinePawn.");
        int id = onlinePawnMapper.add(onlinePawn);
        return id;
    }
    @Override
    public OnlinePawn select(Integer id){
        LOGGER.debug("Begin select onlinePawn.");
        return onlinePawnMapper.select(id);
    }
    @Override
    public Integer update(OnlinePawn onlinePawn){
        LOGGER.debug("Begin update onlinePawn.");
        int id = onlinePawnMapper.update(onlinePawn);
        return id;
    }
    @Override
    public Integer delete(Integer id){
        LOGGER.debug("Begin delete onlinePawn.");
        onlinePawnMapper.delete(id);
        return id;
    }
    @Override
    public List<OnlinePawn> findByPhoneNumber(String phoneNumber){
        return onlinePawnMapper.findByPhoneNumber(phoneNumber);
    }


}
