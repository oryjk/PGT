package com.pgt.data;

import com.alibaba.fastjson.JSONObject;
import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-4-6.
 */
public class PawnDataListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MigrateDataListener.class);
    @Autowired
    private PawnDataList pawnDataList;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {//root application context 没有parent，他就是老大.
            try {
                pawnDataList = (PawnDataList) event.getApplicationContext().getBean("PawnDataList");
                List<Category> livePawnList = categoryService.queryLivepawnCategroys();
                pawnDataList.setPawnDataList(livePawnList);
                LOGGER.debug("pawn data is :"  + JSONObject.toJSONString(pawnDataList.getPawnDataList()));
            } catch (Exception e) {
                LOGGER.error("Some error occured when pwan data.The error message is {}.", e.getMessage());
                throw e;
            }
        }
    }

}
