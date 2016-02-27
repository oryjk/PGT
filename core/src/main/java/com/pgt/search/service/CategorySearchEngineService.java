package com.pgt.search.service;

import com.google.common.collect.Lists;
import com.pgt.category.bean.CategoryType;
import com.pgt.constant.Constants;
import com.pgt.search.bean.ESFilter;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
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
    @Override
    public void index() {

    }

    @Override
    public void update(Integer id) {

    }

    @Override
    public void initialIndex() {

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
    public SearchResponse findRootCategory(CategoryType categoryType){
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
                    getSearchClient().prepareSearch(Constants.SITE_INDEX_NAME).setTypes(Constants.CATEGORY_INDEX_TYPE)
                            .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            if (!ObjectUtils.isEmpty(esTerms)) {
                esTerms.stream().forEach(esTerm -> qb.should(matchQuery(esTerm.getPropertyName(), esTerm.getTermValue())));
            }
            if (!ObjectUtils.isEmpty(esSort)) {
                searchRequestBuilder.addSort(esSort.getPropertyName(), esSort.getSortOrder());
            }
            searchRequestBuilder.setTerminateAfter(Integer.MAX_VALUE);
            response = searchRequestBuilder.execute().actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }
}
