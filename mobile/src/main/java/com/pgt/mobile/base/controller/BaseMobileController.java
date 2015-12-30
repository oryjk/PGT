package com.pgt.mobile.base.controller;

import com.pgt.mobile.base.constans.MobileConstans;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 15-12-24.
 */
public class BaseMobileController {


    protected Map<String, Object> responseMobileFail(Map<String, Object> responseMap, String value) {
        responseMap.put(MobileConstans.MOBILE_STATUS, MobileConstans.MOBILE_STATUS_FAIL);
        responseMap.put(MobileConstans.MOBILE_MESSAGE, value);
        return responseMap;
    }


    protected List  searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List message = new ArrayList();
        for(SearchHit searchit:searchHits){
            message.add(searchit.getSource());
        }
        return message;
    }


}
