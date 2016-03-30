package com.pgt.common.service;

import com.pgt.common.bean.BreadCrumb;
import com.pgt.configuration.URLConfiguration;
import com.pgt.search.service.CategorySearchEngineService;
import com.pgt.utils.SearchConvertToList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by carlwang on 3/13/16.
 */

@Service
public class BreadCrumbService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BreadCrumbService.class);

    @Autowired
    private URLConfiguration urlConfiguration;
    @Autowired
    private CategorySearchEngineService categorySearchEngineService;

    public List<BreadCrumb> buildSearchBreadCrumb(String categoryId, String keyword) {
        List<BreadCrumb> breadCrumbs = new ArrayList<>();
        if (!StringUtils.isBlank(categoryId)) {
            SearchResponse response = categorySearchEngineService.findCategoryById(null, categoryId);
            List<Map<String, Object>> list = SearchConvertToList.searchConvertToList(response);
            if (!CollectionUtils.isEmpty(list)) {
                BreadCrumb breadCrumb = new BreadCrumb();
                breadCrumb.setBreadName((String) list.get(0).get("name"));
                //&cid=61&ctype=TENDER_HIERARCHY
                breadCrumb.setBreadUrl(urlConfiguration.getTenderListPage() + "?cid=" + list.get(0).get("id") + "&ctype=" + list.get(0).get("type"));
                breadCrumbs.add(breadCrumb);
            }
        }
        if (!StringUtils.isBlank(keyword)) {
            BreadCrumb breadCrumb = new BreadCrumb();
            breadCrumb.setBreadName(keyword);
            breadCrumbs.add(breadCrumb);
        }
        return breadCrumbs;
    }
}
