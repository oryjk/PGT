package com.pgt.utils;

import com.pgt.common.bean.CommPaginationBean;
import com.pgt.configuration.ESConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Created by carlwang on 2/27/16.
 */
@Service
@Scope(value = "singleton")
public class PaginationUtils {
    @Autowired
    private ESConfiguration esConfiguration;

    public CommPaginationBean createPagination(Integer currentIndex) {
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0;
        }

        CommPaginationBean paginationBean = new CommPaginationBean();
        paginationBean.setCurrentIndex(currentIndex * esConfiguration.getTenderListCapacity());
        paginationBean.setCapacity(esConfiguration.getTenderListCapacity());
        return paginationBean;
    }


}
