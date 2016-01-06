package com.pgt.index;

import com.pgt.search.service.ESSearchService;
import org.elasticsearch.action.bulk.BulkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlwang on 11/29/15.
 */

@RequestMapping("/index")
@RestController
public class CreateIndexController {


    @Autowired
    private ESSearchService esSearchService;

    @RequestMapping(value = "/createProductIndex", method = RequestMethod.GET)
    public Map<String, Object> createProductIndex() {
        Map<String, Object> message = new HashMap<>();
        esSearchService.initialIndex(true);
        esSearchService.categoryIndex();
        esSearchService.hotProductIndex();
        BulkResponse responses = esSearchService.productsIndex();
        message.put("responses", responses);
        if (responses.hasFailures()) {

            message.put("message", "error");
            return message;
        }
        message.put("message", "success");
        return message;
    }


    public ESSearchService getEsSearchService() {
        return esSearchService;
    }

    public void setEsSearchService(ESSearchService esSearchService) {
        this.esSearchService = esSearchService;
    }

}
