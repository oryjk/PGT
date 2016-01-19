import com.alibaba.fastjson.JSONObject;
import com.pgt.common.bean.CommPaginationBean;
import com.pgt.mobile.search.controller.ESSearchMobileController;
import com.pgt.mobile.search.controller.EssearchBean;
import com.pgt.search.bean.ESAggregation;
import com.pgt.search.bean.ESRange;
import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTerm;
import com.pgt.search.service.ESSearchService;
import com.pgt.utils.PaginationBean;
import com.pgt.web.search.AbstractController.SearchBaseController;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liqiang on 16-1-18.
 */
//@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class SearchTest extends SearchBaseController{
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchTest.class);
    @Autowired
    private ESSearchService esSearchService;

    @Test
    public void testeSearch() {
        CommPaginationBean paginationBean = new CommPaginationBean(20, 1, 100);
        paginationBean.setCurrentIndex(0);
        EssearchBean essearchBean = new EssearchBean();
        essearchBean.setTerm("玉");
        //LOGGER.debug("******************" + JSONObject.toJSONString(this.essearchService(essearchBean)));
        //LOGGER.debug("******************" + JSONObject.toJSONString(this.findCategories()));

        List id = new ArrayList<>();
        id.add("98");
        SearchResponse r = esSearchService.findProductsByProductIds(id);
        SearchHits hits = r.getHits();
        SearchHit[] productsArr = hits.getHits();
        LOGGER.debug("111111111111111111111111111" + JSONObject.toJSONString( productsArr[0].getSource()));
    }
}
