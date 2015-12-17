package com.pgt.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.pgt.category.bean.Category;
import com.pgt.category.bean.CategoryType;
import com.pgt.category.service.CategoryHelper;
import com.pgt.category.service.CategoryService;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import com.pgt.home.bean.HotSale;
import com.pgt.hot.service.HotProductHelper;
import com.pgt.product.bean.Product;
import com.pgt.product.helper.ProductHelper;
import com.pgt.search.bean.*;
import com.pgt.utils.PaginationBean;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by carlwang on 11/30/15.
 */
@Service
public class ESSearchService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ESSearchService.class);
    @Autowired
    private ESConfiguration esConfiguration;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryHelper categoryHelper;


    @Autowired
    private HotProductHelper hotProductHelper;

    private Client indexClient;
    private Client searchClient;


    public void initialIndex(Boolean override) {
        createIndex(Constants.SITE_INDEX_NAME, override);
        createProductMapping(Constants.SITE_INDEX_NAME);
        createCategoryMapping(Constants.SITE_INDEX_NAME);
        createHotSaleMapping(Constants.SITE_INDEX_NAME);
    }

    /**
     * Do category index.
     *
     * @return
     */
    public BulkResponse categoryIndex() {
        BulkResponse bulkResponse = null;
        try {
            LOGGER.debug("Begin to category index.");

            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Category> rootCategories = categoryHelper.findRootCategories();
            if (!ObjectUtils.isEmpty(rootCategories)) {
                rootCategories.stream().forEach(rootCategory -> {
                    LOGGER.debug("The root category id {categoryId}.", rootCategory.getId());
                    List<Category> subCategories = rootCategory.getChildren();
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        byte[] rootByte = mapper.writeValueAsBytes(rootCategory);
                        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants.CATEGORY_INDEX_TYPE,
                                rootCategory.getId() + "")
                                .setSource(rootByte);
                        bulkRequest.add(indexRequestBuilder);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    if (!ObjectUtils.isEmpty(subCategories)) {
                        subCategories.stream().forEach(category -> {
                            LOGGER.debug("The category id {categoryId}.", category.getId());
                            try {
                                byte[] bytes = mapper.writeValueAsBytes(category);
                                IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants
                                                .CATEGORY_INDEX_TYPE,
                                        category.getId() + "")
                                        .setSource(bytes);
                                bulkRequest.add(indexRequestBuilder);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                });
            }
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                LOGGER.error("The category index is failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("End to category index.");
        return bulkResponse;
    }


    /**
     * Do product index.
     *
     * @return
     */
    public BulkResponse productIndex() {

        BulkResponse bulkResponse = null;
        try {
            LOGGER.debug("Begin to product index.");

            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Category> rootCategories = categoryHelper.findRootCategories();
            if (!ObjectUtils.isEmpty(rootCategories)) {
                rootCategories.stream().forEach(rootCategory -> {
                    LOGGER.debug("The root category id {categoryId}.", rootCategory.getId());
                    List<Category> subCategories = rootCategory.getChildren();
                    if (!ObjectUtils.isEmpty(subCategories)) {
                        subCategories.stream().forEach(category -> {
                            LOGGER.debug("The category id {categoryId}.", category.getId());
                            List<Product> products = productHelper.findProductsByCategoryId(category.getId() + "");
                            if (!ObjectUtils.isEmpty(products)) {
                                products.stream().forEach(product -> {
                                    try {
                                        ESProduct esProduct = buildESProduct(product, rootCategory, category);
                                        ObjectMapper mapper = new ObjectMapper();
                                        byte[] bytes = mapper.writeValueAsBytes(esProduct);
                                        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants
                                                        .PRODUCT_INDEX_TYPE,
                                                product.getProductId() + "")
                                                .setSource(bytes);
                                        bulkRequest.add(indexRequestBuilder);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                });
                            }

                        });
                    }
                });
            }
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                LOGGER.error("The product index is failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("End to product index.");
        return bulkResponse;
    }

    /**
     * Do hot sale product index.
     *
     * @return
     */
    public BulkResponse hotProductIndex() {

        BulkResponse bulkResponse = null;
        try {
            LOGGER.debug("Begin to product index.");

            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Category> rootCategories = categoryHelper.findRootCategories();
            if (!ObjectUtils.isEmpty(rootCategories)) {
                rootCategories.stream().forEach(rootCategory -> {
                    LOGGER.debug("The root category id {categoryId}.", rootCategory.getId());
                    List<Product> products = hotProductHelper.findCategoryHotProductByRootCategoryId(rootCategory.getId());
                    HotSale hotSale = new HotSale(rootCategory, products);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        byte[] bytes = mapper.writeValueAsBytes(hotSale);
                        IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants
                                        .HOT_PRODUCT_INDEX_TYPE,
                                hotSale.getId() + "")
                                .setSource(bytes);
                        bulkRequest.add(indexRequestBuilder);
                    } catch (JsonProcessingException e) {
                        LOGGER.error("JsonProcessingException");
                    }

                });
            }
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                LOGGER.error("The product index is failed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("End to product index.");
        return bulkResponse;
    }

    /**
     * Do product update
     *
     * @param productIds
     * @return
     */
    public boolean reduceProductInventory(List<Integer> productIds) {
        LOGGER.debug("Begin to reduce the product inventory.");
        BulkResponse bulkResponse;

        try {
            Client client = getIndexClient();
            BulkRequestBuilder bulkRequest = client.prepareBulk();
            List<Product> products = productHelper.findProductsByIds(productIds);
            products.stream().forEach(product -> {
                Category parentCategory = categoryService.queryParentCategoryByProductId(product.getProductId());
                Category rootCategory = categoryService.queryRootCategoryByProductId(product.getProductId());
                product.setStock(0);
                ESProduct esProduct = buildESProduct(product, rootCategory, parentCategory);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    byte[] bytes = mapper.writeValueAsBytes(esProduct);
                    LOGGER.debug("Product id is {}.", esProduct.getProductId());
                    UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate(Constants.SITE_INDEX_NAME, Constants
                            .PRODUCT_INDEX_TYPE, esProduct.getProductId() + "").setSource(bytes);
                    bulkRequest.add(updateRequestBuilder);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            bulkResponse = bulkRequest.execute().actionGet(100000);
            if (bulkResponse.hasFailures()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.debug("End to reduce the product inventory.");
        return true;
    }

    private ESProduct buildESProduct(Product product, Category rootCategory, Category parentCategory) {
        ESProduct esProduct = new ESProduct();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(product, esProduct);
        esProduct.setParentCategoryId(parentCategory.getId() + "");
        esProduct.setParentCategoryName(parentCategory.getName());
        esProduct.setRootCategoryId(rootCategory.getId() + "");
        esProduct.setRootCategoryName(rootCategory.getName());
        return esProduct;
    }

    /**
     * Find product when have only one term.
     *
     * @param esTerm
     * @param esMatch
     * @param esRange
     * @param esSort
     * @param esSortList
     * @param paginationBean
     * @param categoryIdAggregation
     * @param rangeQueryBuilderList @return
     */
    public SearchResponse findProducts(ESTerm esTerm, ESTerm esMatch, ESRange esRange, @Deprecated ESSort esSort, List<ESSort> esSortList,
                                       PaginationBean paginationBean,
                                       ESAggregation categoryIdAggregation, List<RangeQueryBuilder> rangeQueryBuilderList) {
        SearchResponse response = null;
        try {
            SearchRequestBuilder searchRequestBuilder = buildProductRequestBuilder();
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            if (!ObjectUtils.isEmpty(esTerm)) {
                //operator will add to configuration or from request
                qb.must(termQuery(esTerm.getPropertyName(), esTerm.getTermValue()));
            }
            buildQueryBuilder(esMatch, esRange, esSort, esSortList, paginationBean, categoryIdAggregation, searchRequestBuilder, qb);
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    public SearchResponse findHotSales(ESSort esSort) {
        SearchResponse response = null;
        try {
            SearchRequestBuilder searchRequestBuilder = buildHotSalesRequestBuilder();
            searchRequestBuilder.addSort(Constants.SORT, SortOrder.ASC);
            if (esSort != null) {
                searchRequestBuilder.addSort(Constants.SORT, esSort.getSortOrder());
            }
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private SearchRequestBuilder buildHotSalesRequestBuilder() throws IOException {
        return getSearchClient().prepareSearch(Constants.SITE_INDEX_NAME)
                .setTypes(Constants.HOT_PRODUCT_INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    }


    private SearchRequestBuilder buildProductRequestBuilder() throws IOException {
        return getSearchClient().prepareSearch(Constants.SITE_INDEX_NAME)
                .setTypes(Constants.PRODUCT_INDEX_TYPE)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
    }


    /**
     * Find the product when have more than one term.
     *
     * @param esTerms
     * @param esMatch
     * @param esRange
     * @param esSort
     * @param esSortList
     * @param paginationBean
     * @param esAggregation  @return
     */
    public SearchResponse findProducts(List<ESTerm> esTerms, ESTerm esMatch, ESRange esRange, @Deprecated ESSort esSort, List<ESSort> esSortList,
                                       PaginationBean paginationBean,
                                       ESAggregation esAggregation) {
        SearchResponse response = null;
        try {
            SearchRequestBuilder searchRequestBuilder = buildProductRequestBuilder();
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            if (!ObjectUtils.isEmpty(esTerms)) {
                esTerms.stream().forEach(esTerm -> {
                    qb.should(termQuery(esTerm.getPropertyName(), esTerm.getTermValue()));
                });
                //operator will add to configuration or from request

            }
            buildQueryBuilder(esMatch, esRange, esSort, esSortList, paginationBean, esAggregation, searchRequestBuilder, qb);
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void buildQueryBuilder(ESTerm esMatch, ESRange esRange, @Deprecated ESSort esSort, List<ESSort> esSortList, PaginationBean paginationBean,
                                   ESAggregation esAggregation,
                                   SearchRequestBuilder searchRequestBuilder, BoolQueryBuilder qb) {
        if (!ObjectUtils.isEmpty(esMatch)) {
            qb.must(matchQuery(esMatch.getPropertyName(), esMatch.getTermValue()));
        }
        if (!ObjectUtils.isEmpty(esSort)) {
            searchRequestBuilder.addSort(esSort.getPropertyName(), esSort.getSortOrder());
        }
        if (!ObjectUtils.isEmpty(esSortList)) {
            esSortList.stream().forEach(esSort1 ->
                            searchRequestBuilder.addSort(esSort1.getPropertyName(), esSort1.getSortOrder())
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


    /**
     * This method can find all products to category with this category id.Either root category or HIERARCHY category.
     *
     * @param categoryId
     * @return
     */

    public SearchResponse findProductsByCategoryId(String categoryId, ESTerm esMatch, ESRange esRange, ESSort esSort, PaginationBean paginationBean,
                                                   ESAggregation esAggregation) {
        SearchResponse response = null;
        if (!StringUtils.isBlank(categoryId)) {

            SearchResponse categoryResponse = findCategories(Lists.newArrayList(getCategoryEsTerm(Constants.ID, categoryId)), null);
            SearchHits cateHits = categoryResponse.getHits();
            if (ObjectUtils.isEmpty(cateHits)) {
                LOGGER.debug("Can not find the products with category is {}.", categoryId);
                return response;

            }
            SearchHit[] categoryHists = cateHits.getHits();

            if (categoryHists.length == 1) {
                String typeValue = (String) categoryHists[0].getSource().get(Constants.TYPE);
                if (typeValue.equals(CategoryType.ROOT.toString())) {
                    LOGGER.debug("This category id is belong to ROOT category.");
                    List<ESTerm> esTerms = new ArrayList<>();
                    List<Map<String, Object>> childrenList = (List<Map<String, Object>>) categoryHists[0].getSource().get(Constants.CHILDREN);
                    if (childrenList == null) {
                        return response;
                    }
                    childrenList.stream().forEach(stringObjectMap -> {
                        Integer id = (Integer) stringObjectMap.get(Constants.ID);
                        esTerms.add(getCategoryEsTerm(Constants.PARENT_CATEGORY_ID, String.valueOf(id)));
                    });

                    if (esTerms.size() == 0) {
                        LOGGER.debug("Can not find the child categories.");
                        return response;
                    }
                    return findProducts(esTerms, esMatch, esRange, esSort, null, paginationBean, esAggregation);
                }
                LOGGER.debug("This category id is not belong to ROOT category.");

                return findProducts(getCategoryEsTerm(Constants.PARENT_CATEGORY_ID, categoryId), esMatch, esRange, esSort, null, paginationBean,
                        esAggregation, null);

            }

        }
        return response;

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

    private ESTerm getCategoryEsTerm(String propertyName, String termValue) {
        ESTerm esTerm = new ESTerm();
        esTerm.setPropertyName(propertyName);
        esTerm.setTermValue(termValue);
        return esTerm;
    }


    public SearchResponse findCategories(List<ESTerm> esTerms, ESSort esSort) {
        SearchResponse response = null;

        try {
            SearchRequestBuilder searchRequestBuilder = getSearchClient().prepareSearch(Constants.SITE_INDEX_NAME).setTypes(Constants
                    .CATEGORY_INDEX_TYPE)
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            if (!ObjectUtils.isEmpty(esTerms)) {
                esTerms.stream().forEach(esTerm ->
                                qb.should(matchQuery(esTerm.getPropertyName(), esTerm.getTermValue()))
                );
            }
            if (!ObjectUtils.isEmpty(esSort)) {
                searchRequestBuilder.addSort(esSort.getPropertyName(), esSort.getSortOrder());
            }
            searchRequestBuilder.setTerminateAfter(Integer.MAX_VALUE);
            response = searchRequestBuilder.execute()
                    .actionGet();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private Client getIndexClient() throws IOException {
        if (indexClient == null) {
            indexClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfiguration.getHost()), esConfiguration.getIndexPort
                            ()));
        }
        return indexClient;
    }

    private Client getSearchClient() throws IOException {
        if (searchClient == null) {
            searchClient = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(esConfiguration.getHost()), esConfiguration
                            .getSearchPort
                                    ()));
        }
        return searchClient;
    }


    private void createIndex(String indexName, Boolean override) {
        try {
            boolean exists = getIndexClient().admin().indices().prepareExists(indexName).execute().actionGet().isExists();
            if (exists && !override) {
                LOGGER.debug("The index {indexName} is exist,and not need override.");
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
    }

    private void createProductMapping(String index) {
        createMapping(index, Constants.PRODUCT_INDEX_TYPE, esConfiguration.getProductAnalyzerFields());
    }

    private void createCategoryMapping(String index) {
        createMapping(index, Constants.CATEGORY_INDEX_TYPE, esConfiguration.getCategoryAnalyzerFields());
    }

    private void createHotSaleMapping(String index) {
        createMapping(index, Constants.HOT_PRODUCT_INDEX_TYPE, esConfiguration.getHotSaleAnalyzerFields());
    }


    private void createMapping(String indexName, String indexType, List<String> analyseFields) {
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


    public ESConfiguration getEsConfiguration() {
        return esConfiguration;
    }

    public void setEsConfiguration(ESConfiguration esConfiguration) {
        this.esConfiguration = esConfiguration;
    }


    public ProductHelper getProductHelper() {
        return productHelper;
    }

    public void setProductHelper(ProductHelper productHelper) {
        this.productHelper = productHelper;
    }

    public CategoryHelper getCategoryHelper() {
        return categoryHelper;
    }

    public void setCategoryHelper(CategoryHelper categoryHelper) {
        this.categoryHelper = categoryHelper;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public CategoryService getCategoryService() {
        return categoryService;
    }

    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    public HotProductHelper getHotProductHelper() {
        return hotProductHelper;
    }

    public void setHotProductHelper(HotProductHelper hotProductHelper) {
        this.hotProductHelper = hotProductHelper;
    }

}

