package com.pgt.search.procedure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carlwang on 3/21/16.
 */
@Service
public class B2BCategoryIndexProcedure implements IndexProcedure {
    private static final Logger LOGGER = LoggerFactory.getLogger(B2BCategoryIndexProcedure.class);
    @Autowired
    private CategoryHelper categoryHelper;

    @Override
    public List<Pair<String, String>> buildSourceList() {
        List<Category> rootCategories = categoryHelper.findRootCategories(CategoryType.ROOT);
        LOGGER.debug("Begin to category index.");
        if (CollectionUtils.isEmpty(rootCategories)) {
            LOGGER.debug("Root category is empty.");
            return null;
        }
        List<Pair<String, String>> categoryList = new ArrayList<>();
        rootCategories.stream().forEach(rootCategory -> {
            LOGGER.debug("The root category id {}.", rootCategory.getId());
            List<Category> subCategories = rootCategory.getChildren();
            ObjectMapper mapper = new ObjectMapper();
            try {
                String categoryString = mapper.writeValueAsString(rootCategory);
                Pair<String, String> categoryPair = Pair.of(rootCategory.getId() + "", categoryString);
                categoryList.add(categoryPair);
                if (!CollectionUtils.isEmpty(subCategories)) {
                    subCategories.stream().forEach(category -> {
                        LOGGER.debug("The category id {}.", category.getId());
                        try {
                            String subCategoryString = mapper.writeValueAsString(category);
                            Pair<String, String> subCategoryPair = Pair.of(category.getId() + "", subCategoryString);
                            categoryList.add(subCategoryPair);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    });
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        });
        return categoryList;
    }

}