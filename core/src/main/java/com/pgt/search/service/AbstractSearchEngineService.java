package com.pgt.search.service;

import com.pgt.configuration.Configuration;
import com.pgt.configuration.ESConfiguration;
import com.pgt.constant.Constants;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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


    abstract void index();

    abstract void update(Integer id);


    protected void initialIndex() {
        createIndex(Constants.SITE_INDEX_NAME, esConfiguration.isNeedIndex());
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
