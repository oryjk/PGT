package com.pgt.data;

import com.alibaba.fastjson.JSONObject;
import com.pgt.category.bean.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //获取类型集合
    public List<Map<String,Object>> findAllPawnCategroysType(){
        List<Category> pawnList = findAllPawnCategroys();
        List<Map<String,Object>> typeList = new ArrayList<>();
        for(Category category:pawnList){
            Map<String,Object> typeMap = new HashMap<>();
            typeMap.put("type", category.getType());
            typeList.add(typeMap);
        }
        return  typeList;
    }

    //获取分类id集合
    public List<Map<String,Object>> findAllPawnCategroysId(){
        List<Category> pawnList = findAllPawnCategroys();
        List<Map<String,Object>> typeList = new ArrayList<>();
        for(Category category:pawnList){
            Map<String,Object> typeMap = new HashMap<>();
            typeMap.put("type", category.getId());
            typeList.add(typeMap);
        }
        return  typeList;
    }

    //获取图片id集合
    public List<Map<String,Object>> findAllPawnCategroysFrontMediaId(){
        List<Category> pawnList = findAllPawnCategroys();
        List<Map<String,Object>> typeList = new ArrayList<>();
        for(Category category:pawnList){
            Map<String,Object> typeMap = new HashMap<>();
            typeMap.put("type", category.getFrontMedia().getId());
            typeList.add(typeMap);
        }
        return  typeList;
    }

}
