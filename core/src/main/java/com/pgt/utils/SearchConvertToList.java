package com.pgt.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaodong on 16-2-26.
 */
public class SearchConvertToList {

    public static List<Map<String,Object>> searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List list = new ArrayList();
        for(SearchHit searchHit:searchHits){
            list.add(searchHit.getSource());
        }
        return list;
    }

}
