package com.pgt.data;

import com.alibaba.fastjson.JSONObject;
import com.pgt.category.bean.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by fei on 2016/4/7.
 */
@Service
public class PawnService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PawnService.class);
    @Autowired
    private PawnDataList pawnDataList;

    public List<Category> findAllPawnCategroys(){
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        pawnDataList = (PawnDataList) wac.getBean("PawnDataList");
        LOGGER.debug("the pawn all data is :" + JSONObject.toJSONString(pawnDataList.getPawnDataList()));
        return pawnDataList.getPawnDataList();
    }


}
