package com.pgt.search.service;

import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.search.bean.*;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by carlwang on 1/19/16.
 */

public abstract class AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSearchEngineService.class);

    @Autowired
    private ESConfiguration esConfiguration;
    @Autowired
    private Configuration configuration;


    private Client indexClient;
    private Client searchClient;


    public abstract void index();

    public abstract void update(Integer id);


    public abstract void initialIndex();

    protected void createIndex(String indexName, Boolean override) {
        try {
            boolean exists = getIndexClient().admin().indices().prepareExists(indexName).execute().actionGet().isExists();
            if (exists && !override) {
                LOGGER.debug("The index {indexName} is exist,and not need override.");
                esConfiguration.setClearIndex(false);
                return;
            }
            if (exists) {
                try {
                    DeleteIndexRequest request = new DeleteIndexRequest(indexName);
                    DeleteIndexResponse deleteIndexResponse = getIndexClient().admin().indices().delete(request).actionGet();
                    if (!deleteIndexResponse.isAcknowledged()) {
                        LOGGER.error("Fail to delete the index {indexName}.", indexName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            getIndexClient().admin().indices().prepareCreate(indexName).execute().actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }
        esConfiguration.setClearIndex(false);
    }

    protected Client getIndexClient() throws IOException {
        if (indexClient == null) {
            indexClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfiguration.getHost()), esConfiguration.getIndexPort
                            ()));
        }
        return indexClient;
    }

    protected Client getSearchClient() throws IOException {
        if (searchClient == null) {
            searchClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfiguration.getHost()), esConfiguration
                            .getSearchPort
                                    ()));
        }
        return searchClient;
    }

    protected void createMapping(String indexName, String indexType, List<String> analyseFields) {
        XContentBuilder mapping;
        try {
            mapping = jsonBuilder().startObject()
                    .startObject(indexType)
                    .startObject("properties");

            if (!ObjectUtils.isEmpty(analyseFields)) {
                final XContentBuilder finalMapping = mapping;
                analyseFields.stream().forEach(s -> {
                    try {
                        finalMapping.startObject(s).field("type", "string").field("store", "yes").field("analyzer", "ik_max_word").field
                                ("include_in_all",
                                        true).field("term_vector", "with_positions_offsets").endObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            mapping = mapping.endObject().endObject().endObject();
            PutMappingRequest mappingRequest = Requests.putMappingRequest(indexName).type(indexType).source(mapping);
            getIndexClient().admin().indices().putMapping(mappingRequest).actionGet();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    protected SearchResponse findAll(String indexName, String indexType) {

        SearchResponse response = null;
        try {
            SearchRequestBuilder searchRequestBuilder = initialSearchRequestBuilder(indexName, indexType);
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            buildQueryBuilder(null, null, null, null, null, null, searchRequestBuilder, qb);
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    protected SearchRequestBuilder initialSearchRequestBuilder(String siteIndexName, String indexType) throws IOException {
        return getSearchClient().prepareSearch(siteIndexName)
                .setTypes(indexType)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    }

    /**
     * need use this one.
     *
     * @param esTerms
     * @param esFilter
     * @param paginationBean
     * @param esSorts
     * @param searchRequestBuilder
     */
    protected void buildSearchRequestBuilder(List<ESTerm> esTerms, ESFilter esFilter, PaginationBean paginationBean,
                                             List<ESSort> esSorts, SearchRequestBuilder searchRequestBuilder) {
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        searchRequestBuilder.setQuery(boolQueryBuilder);

        String queryString = buildQueryString(esTerms);
        if (!StringUtils.isEmpty(queryString)) {
            boolQueryBuilder.must(QueryBuilders.queryStringQuery(queryString));
        }
        buildFilter(boolQueryBuilder, esFilter);
        buildSort(searchRequestBuilder, esSorts);
        buildPagination(searchRequestBuilder, paginationBean);
    }

    private void buildPagination(SearchRequestBuilder searchRequestBuilder, PaginationBean paginationBean) {
        if (paginationBean != null) {
            if (paginationBean.getCapacity() == 0) {
                paginationBean.setCapacity(configuration
                        .getProductListPageCapacity());
            }
            searchRequestBuilder.setFrom((int) paginationBean.getCurrentIndex()).setSize((int) paginationBean.getCapacity()).setExplain(true);
        }
    }

    protected abstract void buildSort(SearchRequestBuilder searchRequestBuilder, List<ESSort> esSorts);

    protected abstract void buildFilter(BoolQueryBuilder boolQueryBuilder, ESFilter esFilter);


    protected void buildQueryBuilder(ESTerm esTerm, List<ESTerm> esMatches, ESRange esRange, List<ESSort> esSortList, PaginationBean paginationBean,
                                     ESAggregation esAggregation,
                                     SearchRequestBuilder searchRequestBuilder, BoolQueryBuilder qb) {
        if (!ObjectUtils.isEmpty(esTerm)) {
            //operator will add to configuration or from request
            qb.must(termQuery(esTerm.getPropertyName(), esTerm.getTermValue()));
        }
        if (!ObjectUtils.isEmpty(esMatches)) {
            esMatches.stream().forEach(esMatch ->
                            qb.should(matchQuery(esMatch.getPropertyName(), esMatch.getTermValue()))
            );
        }
        if (!ObjectUtils.isEmpty(esSortList)) {
            esSortList.stream().forEach(esSort1 ->
                    {
                        FieldSortBuilder sortBuilder = new FieldSortBuilder(esSort1.getPropertyName());
                        sortBuilder.order(esSort1.getSortOrder());
                        sortBuilder.unmappedType("long");
                        searchRequestBuilder.addSort(sortBuilder);
                    }
            );
        }
        if (!ObjectUtils.isEmpty(esAggregation)) {
            searchRequestBuilder.addAggregation(AggregationBuilders.terms(esAggregation.getAggregationName()).field(esAggregation
                    .getPropertyName()));
        }
        if (!ObjectUtils.isEmpty(esRange)) {
            searchRequestBuilder.setPostFilter(QueryBuilders.rangeQuery(esRange.getPropertyName()).from(esRange.getFrom()).to(esRange
                    .getTo()));
        }
        if (paginationBean != null) {
            if (paginationBean.getCapacity() == 0) {
                paginationBean.setCapacity(configuration
                        .getProductListPageCapacity());
            }
            searchRequestBuilder.setFrom((int) paginationBean.getCurrentIndex()).setSize((int) paginationBean.getCapacity()).setExplain(true);
        }
    }

    public boolean deleteIndex(String indexName) {
        try {
            DeleteIndexRequest request = new DeleteIndexRequest(indexName);
            DeleteIndexResponse deleteIndexResponse = getIndexClient().admin().indices().delete(request).actionGet();
            if (!deleteIndexResponse.isAcknowledged()) {
                LOGGER.error("Fail to delete the index {indexName}.", indexName);
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    protected String buildQueryString(List<ESTerm> esMatches) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtils.isEmpty(esMatches)) {
            LOGGER.debug("The match term is empty.");
            return null;
        }
        for (int i = 0; i < esMatches.size(); i++) {
            ESTerm esTerm = esMatches.get(i);
            if (i == 0) {
                stringBuilder.append(esTerm.getPropertyName() + ":" + esTerm.getTermValue());
                continue;
            }
            stringBuilder.append(" OR ");
            stringBuilder.append(esTerm.getPropertyName() + ":" + esTerm.getTermValue());
        }
        return stringBuilder.toString();

    }

    protected String buildQueryAndString(List<ESTerm> esMatches) {
        StringBuilder stringBuilder = new StringBuilder();
        if (CollectionUtils.isEmpty(esMatches)) {
            LOGGER.debug("The match term is empty.");
            return null;
        }
        for (int i = 0; i < esMatches.size(); i++) {
            ESTerm esTerm = esMatches.get(i);
            if (i == 0) {
                stringBuilder.append(esTerm.getPropertyName() + ":" + esTerm.getTermValue());
                continue;
            }
            stringBuilder.append(" AND ");
            stringBuilder.append(esTerm.getPropertyName() + ":" + esTerm.getTermValue());
        }
        return stringBuilder.toString();

    }


    protected List<ESTerm> buildESTerms(String keyword, List<String> searchProperties) {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }

        List<String> useToSearch = searchProperties;
        List<ESTerm> esTerms = new ArrayList<>();
        if (!ObjectUtils.isEmpty(useToSearch)) {
            final String finalTerm = keyword;
            useToSearch.stream().forEach(s -> {
                ESTerm esMatch = new ESTerm();
                esMatch.setPropertyName(s);
                esMatch.setTermValue(finalTerm);
                esTerms.add(esMatch);
            });
        }
        return esTerms;
    }


    protected void deleteIndex(String id, String indexName, String type) {
        try {
            DeleteResponse response = getIndexClient().prepareDelete(indexName, type, id).execute().actionGet();
            response.getHeaders().stream().forEach(s -> LOGGER.debug(s));
            if (response.isFound()) {
                LOGGER.debug("Success delete with id is {},type is {}.", id, type);
                return;
            }
            LOGGER.debug("Can not found to delete with id is {},type is {}.", id, type);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean modifyProductInventory(List<Pair<Integer, Integer>> productPairs) {
        return false;
    }


    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public ESConfiguration getEsConfiguration() {
        return esConfiguration;
    }

    public void setEsConfiguration(ESConfiguration esConfiguration) {
        this.esConfiguration = esConfiguration;
    }
}
