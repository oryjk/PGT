package com.pgt.search.procedure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.bean.ESCategory;
import com.pgt.category.service.CategoryHelper;
import com.pgt.configuration.Site;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.bean.TenderQuery;
import com.pgt.tender.service.TenderService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 3/20/16.
 */
@Service
public class P2PCategoryProcedure implements IndexProcedure {
    private static final Logger LOGGER = LoggerFactory.getLogger(P2PCategoryProcedure.class);
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private TenderService tenderService;


    @Override
    public List<Pair<String, String>> buildSourceList() {
        List<Category> rootCategories = categoryHelper.findRootCategories(CategoryType.TENDER_ROOT);
        if (CollectionUtils.isEmpty(rootCategories)) {
            LOGGER.debug("Root category is empty.");
            return null;
        }
        List<Pair<String, String>> categoryList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        rootCategories.stream().forEach(rootCategory -> {
            ESCategory esRootCategory = new ESCategory(rootCategory);
            LOGGER.debug("The root category id {}.", esRootCategory.getId());
            List<Category> subCategories = esRootCategory.getChildren();
            List<ESCategory> newSubCategories = new ArrayList<>();
            TenderQuery tenderQuery = new TenderQuery();
            tenderQuery.setNeedHot(true);
            tenderQuery.setCategoryHot(true);
            LOGGER.debug("Current site is {},sub category size is {}.", Site.P2P, subCategories.size());
            if (!CollectionUtils.isEmpty(subCategories)) {
                subCategories.stream().forEach(category -> {
                    ESCategory esCategory = new ESCategory(category);
                    tenderQuery.setCategoryId(category.getId());
                    List<Tender> tenders = tenderService.queryTenderByQuery(tenderQuery);
                    if (CollectionUtils.isEmpty(tenders) || tenders.size() < 3) {
                        tenderQuery.setNeedHot(false);
                        tenders = tenderService.queryTenderByQuery(tenderQuery);
                    }
                    LOGGER.debug("The hot tender size is {}.", tenders.size());
                    esCategory.setHotTenders(tenders);
                    try {
                        String categoryString = mapper.writeValueAsString(esCategory);
                        Pair<String, String> categoryPair = Pair.of(esCategory.getId() + "", categoryString);
                        categoryList.add(categoryPair);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    newSubCategories.add(esCategory);
                });
            }
            esRootCategory.setEsChildren(newSubCategories);
            try {
                String categoryString = mapper.writeValueAsString(esRootCategory);
                Pair<String, String> categoryPair = Pair.of(esRootCategory.getId() + "", categoryString);
                categoryList.add(categoryPair);

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });

        return categoryList;
    }
}
