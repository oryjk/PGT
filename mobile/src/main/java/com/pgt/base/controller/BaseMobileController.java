package com.pgt.base.controller;

import com.pgt.base.constans.MobileConstants;
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
        responseMap.put(MobileConstants.MOBILE_STATUS, MobileConstants.MOBILE_STATUS_FAIL);
        responseMap.put(MobileConstants.MOBILE_MESSAGE, value);
        return responseMap;
    }


    protected List  searchConvertToList(SearchResponse searchResponse){
        SearchHit[] searchHits= searchResponse.getHits().getHits();
        List message = new ArrayList();
        for(SearchHit searchHit:searchHits){
            message.add(searchHit.getSource());
        }
        return message;
    }


}
