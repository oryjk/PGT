package com.pgt.data.index.service;

import com.pgt.search.service.AbstractSearchEngineService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 3/4/16.
 */
public class AllIndexService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllIndexService.class);


    private List<AbstractSearchEngineService> searchEngineServiceList = new ArrayList<>();

    public void createIndex() {
        if (CollectionUtils.isEmpty(searchEngineServiceList)) {
            LOGGER.debug("The searchEngineServiceList is empty.");
            return;
        }

        searchEngineServiceList.stream().forEach(abstractSearchEngineService -> {
            abstractSearchEngineService.initialIndex();
            abstractSearchEngineService.index();
        });
    }

    public List<AbstractSearchEngineService> getSearchEngineServiceList() {
        return searchEngineServiceList;
    }

    public void setSearchEngineServiceList(List<AbstractSearchEngineService> searchEngineServiceList) {
        this.searchEngineServiceList = searchEngineServiceList;
    }

}
