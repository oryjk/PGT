package com.pgt.utils;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-26.
 */
public class SearchConverToList {

    public static List searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List message = new ArrayList();
        for(SearchHit searchHit:searchHits){
            message.add(searchHit.getSource());
        }
        return message;
    }

}
