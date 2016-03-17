package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.home.bean.HotSale;
import com.pgt.product.bean.Product;
import com.pgt.search.bean.ESFilter;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Created by carlwang on 1/27/16.
 */

@Service
public class CategorySearchEngineService extends AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategorySearchEngineService.class);
    @Autowired
    private CategoryHelper categoryHelper;
    @Autowired
    private ESConfiguration esConfiguration;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void index() {
        BulkResponse bulkResponse;
        try {
            LOGGER.debug("Begin to category index.");

            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Category> rootCategories = categoryHelper.findRootCategories(esConfiguration.getCategoryType());
            if (!ObjectUtils.isEmpty(rootCategories)) {
                rootCategories.stream().forEach(rootCategory -> {
                    LOGGER.debug("The root category id {}.", rootCategory.getId());
                    List<Category> subCategories = rootCategory.getChildren();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        byte[] rootByte = mapper.writeValueAsBytes(rootCategory);
                        IndexRequestBuilder indexRequestBuilder =
                                client.prepareIndex(esConfiguration.getIndexName(), esConfiguration.getCategoryTypeName(), rootCategory.getId() + "")
                                        .setSource(rootByte);
                        bulkRequest.add(indexRequestBuilder);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if (!ObjectUtils.isEmpty(subCategories)) {
                        subCategories.stream().forEach(category -> {
                            LOGGER.debug("The category id {}.", category.getId());
                            try {
                                byte[] bytes = mapper.writeValueAsBytes(category);
                                IndexRequestBuilder indexRequestBuilder =
                                        client.prepareIndex(esConfiguration.getIndexName(), esConfiguration.getCategoryTypeName(), category.getId()
                                                + "")
                                                .setSource(bytes);
                                bulkRequest.add(indexRequestBuilder);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
            if (bulkRequest.numberOfActions() == 0) {
                LOGGER.debug("Not need create index, cause request number is 0.");
                return;
            }
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                LOGGER.error("The category index is failed.");
                return;
            }
            LOGGER.debug("The category index is complete,success create {},the index name is {},the index type name is {}.", bulkResponse.getItems
                    ().length, esConfiguration.getIndexName(), esConfiguration.getCategoryTypeName());

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("End to category index.");

    }

    @Override
    public void update(Integer id) {

    }

    @Override
    public void initialIndex() {
        LOGGER.debug("Begin to create category mapping");
        createIndex(esConfiguration.getIndexName(), esConfiguration.isClearIndex());
        createMapping(esConfiguration.getIndexName(), esConfiguration.getCategoryTypeName(), esConfiguration.getCategoryAnalyzerFields());
        LOGGER.debug("End to create category mapping.");

    }

    @Override
    protected void buildSort(SearchRequestBuilder searchRequestBuilder, List<ESSort> esSorts) {

    }

    @Override
    protected void buildFilter(BoolQueryBuilder boolQueryBuilder, ESFilter esFilter) {

    }

    public SearchResponse findRootCategory() {

        ESTerm typeTerm = new ESTerm();

        typeTerm.setPropertyName(Constants.TYPE);
        typeTerm.setTermValue(Constants.ROOT);
        ESSort esSort = new ESSort();
        esSort.setPropertyName(Constants.SORT);
        esSort.setSortOrder(SortOrder.ASC);


        return findCategories(Lists.newArrayList(typeTerm), esSort);
    }

    public SearchResponse findRootCategory(CategoryType categoryType) {
        ESTerm typeTerm = new ESTerm();

        typeTerm.setPropertyName(Constants.TYPE);
        typeTerm.setTermValue(categoryType.toString());
        ESSort esSort = new ESSort();
        esSort.setPropertyName(Constants.SORT);
        esSort.setSortOrder(SortOrder.ASC);
        return findCategories(Lists.newArrayList(typeTerm), esSort);
    }

    public SearchResponse findCategories(List<ESTerm> esTerms, ESSort esSort) {
        SearchResponse response = null;

        try {
            SearchRequestBuilder searchRequestBuilder =
                    getSearchClient().prepareSearch(esConfiguration.getIndexName()).setTypes(esConfiguration.getCategoryTypeName())
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            if (!ObjectUtils.isEmpty(esTerms)) {
                esTerms.stream().forEach(esTerm -> qb.should(matchQuery(esTerm.getPropertyName(), esTerm.getTermValue())));
            }
            if (!ObjectUtils.isEmpty(esSort)) {
                FieldSortBuilder sortBuilder = new FieldSortBuilder(esSort.getPropertyName());
                sortBuilder.order(esSort.getSortOrder());
                sortBuilder.unmappedType("long");
                searchRequestBuilder.addSort(sortBuilder);
            }
            searchRequestBuilder.setTerminateAfter(Integer.MAX_VALUE);
            response = searchRequestBuilder.execute().actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public SearchResponse findCategoryById(String categoryId) {
        SearchResponse response = null;

        try {
            SearchRequestBuilder searchRequestBuilder =
                    getSearchClient().prepareSearch(esConfiguration.getIndexName()).setTypes(esConfiguration.getCategoryTypeName())
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            BoolQueryBuilder qb = boolQuery();
            qb.must(QueryBuilders.idsQuery().ids(categoryId));
            searchRequestBuilder.setQuery(qb);
            searchRequestBuilder.setTerminateAfter(Integer.MAX_VALUE);
            response = searchRequestBuilder.execute().actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    public void updateCategoryIndex(Category category, String indexName) {

        LOGGER.debug("Begin to update category index with id is {}.", category.getId());
        ObjectMapper mapper = new ObjectMapper();
        try {
            byte[] rootByte = mapper.writeValueAsBytes(category);
            UpdateRequestBuilder updateRequestBuilder =
                    getIndexClient().prepareUpdate(StringUtils.isEmpty(indexName) ? Constants.SITE_INDEX_NAME : indexName, Constants
                            .CATEGORY_INDEX_TYPE, category.getId() + "")
                            .setDoc(rootByte);
            UpdateResponse updateResponse = updateRequestBuilder.execute().actionGet(10000);
            updateResponse.getHeaders().stream().forEach(s -> LOGGER.debug(s));
            if (updateResponse.isCreated()) {
                LOGGER.debug("Success update category index.");
                return;
            }

            LOGGER.debug("Not success update category index.");

        } catch (JsonProcessingException e) {
            LOGGER.error("JsonProcessingException has occur when update category.");
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("IOException has occur when update category.");
            e.printStackTrace();
        }
    }

    public void createHotSaleIndex(Integer rootCategoryId) {

    }

}
