package com.pgt.util;

import com.pgt.search.bean.ESSort;
import com.pgt.search.bean.ESTenderListFilter;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlwang on 2/27/16.
 */
public class TenderListUtil {

    private static Map<Integer, ESSort> tenderSortMap = new HashMap<Integer, ESSort>() {{
        put(1, new ESSort("newest", SortOrder.ASC));//最新
        put(2, new ESSort("tenderTotal", SortOrder.DESC));//金额最多
        put(3, new ESSort("tenderTotal", SortOrder.ASC));//金额最多
        put(4, new ESSort("cycle", SortOrder.ASC));//周期最短
        put(5, new ESSort("cycle", SortOrder.DESC));//周期最长
        put(6, new ESSort("end", SortOrder.ASC));//即将结束

    }};

    public static ESSort getESSort(Integer id) {
        if(ObjectUtils.isEmpty(id)){
            return null;
        }
        return tenderSortMap.get(id);
    }


    public static void buildESTenderListFilter(Integer id, ESTenderListFilter esTenderListFilter) {
        if (ObjectUtils.isEmpty(id)) {
            id = 1;
        }

        switch (id) {
            case 1:
                esTenderListFilter.setAll(true);//查找所有
                break;
            case 2:
                esTenderListFilter.setBeginInMinute(true);//即将开始
                break;
            case 3:
                esTenderListFilter.setEnded(true);//即将结束
        }
    }
}
