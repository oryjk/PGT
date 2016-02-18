package com.pgt.sso.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pgt.cart.bean.Order;
import com.pgt.constant.Constants;
import com.pgt.search.service.AbstractSearchEngineService;
import com.pgt.sso.bean.UserCache;
import com.pgt.user.bean.User;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * Created by carlwang on 2/18/16.
 */
@Service
public class SSOService extends AbstractSearchEngineService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SSOService.class);


    public boolean cacheUser(User user, Order b2cOrder, Order p2pOrder) {
        if (ObjectUtils.isEmpty(user)) {
            LOGGER.debug("The user is empty.");
            return false;
        }
        try {
            UserCache userCache = new UserCache(new Date(), user, b2cOrder, p2pOrder);
            Client client = getIndexClient();
            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(userCache);
            IndexRequestBuilder indexRequestBuilder = client.prepareIndex(Constants.SITE_INDEX_NAME, Constants
                            .USER_CACHE_INDEX_TYPE,
                    user.getId() + "")
                    .setSource(data);
            IndexResponse indexResponse = indexRequestBuilder.execute().get();
            if (!indexResponse.isCreated()) {
                LOGGER.debug("Not success create the user cache index.");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LOGGER.debug("Success to create the user cache index.");
        return true;
    }


    public boolean updateCacheUser(User user, Order b2cOrder, Order p2pOrder) {
        if (ObjectUtils.isEmpty(user)) {
            LOGGER.debug("The user is empty.");
            return false;
        }
        try {
            UserCache userCache = new UserCache(new Date(), user, b2cOrder, p2pOrder);
            Client client = getIndexClient();
            ObjectMapper mapper = new ObjectMapper();
            String data = mapper.writeValueAsString(userCache);
            UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate(Constants.SITE_INDEX_NAME, Constants
                            .USER_CACHE_INDEX_TYPE,
                    user.getId() + "")
                    .setDoc(data);
            UpdateResponse updateResponse = updateRequestBuilder.execute().actionGet(10000);
            if (updateResponse.getShardInfo().getFailed() > 0) {
                LOGGER.debug("Not success update the user cache index.");
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("Success to update the user cache index.");
        return true;
    }


    public boolean deleteCacheUser(Integer userId) {
        if (ObjectUtils.isEmpty(userId)) {
            LOGGER.debug("The user is empty.");
            return false;
        }
        try {
            Client client = getIndexClient();
            DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(Constants.SITE_INDEX_NAME, Constants
                            .USER_CACHE_INDEX_TYPE,
                    userId + "");

            DeleteResponse deleteResponse = deleteRequestBuilder.execute().get();
            if (!deleteResponse.isFound()) {

                LOGGER.debug("Can not find the user with id is {}.", userId);
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        LOGGER.debug("Success to delete the user cache index.");
        return true;
    }

    public boolean isUserExpire(Integer userId) {
        if (ObjectUtils.isEmpty(userId)) {
            LOGGER.debug("The user is empty.");
            return true;
        }
        try {
            SearchRequestBuilder searchRequestBuilder = buildSearchRequestBuilder(Constants.SITE_INDEX_NAME, Constants.USER_CACHE_INDEX_TYPE);
            BoolQueryBuilder qb = boolQuery();
            searchRequestBuilder.setQuery(qb);
            qb.must(termQuery("user.id", userId));
            SearchResponse response = searchRequestBuilder.execute().actionGet();
            SearchHits searchHits = response.getHits();
            if (ObjectUtils.isEmpty(searchHits) || ArrayUtils.isEmpty(searchHits.getHits())) {
                LOGGER.debug("Can not find the user with id is {}, so may not login.", userId);
                return true;
            }
            SearchHit[] searchHits1 = searchHits.getHits();
            if (searchHits1.length > 1) {
                LOGGER.debug("Is not the only user ID. ");
                return true;
            }
            SearchHit searchHit = searchHits1[0];
            Long updateTime = (Long) searchHit.getSource().get("updateTime");
            long expireTime = updateTime + 30 * 60 * 1000;
            Long currentTime = System.currentTimeMillis();
            if (expireTime < currentTime) {
                LOGGER.debug("The user is expire. ");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.debug("The user not expire.");
        return false;
    }



    @Override
    public void index() {

    }

    @Override
    public void update(Integer id) {

    }
}
