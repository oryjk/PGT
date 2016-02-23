package com.pgt.cart.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.com.pgt.order.bean.P2PInfo;
import com.pgt.tender.bean.Tender;

/**
 * Created by Administrator on 2016/2/18.
 */
public interface P2PMapper extends SqlMapper {

    void createInfo(P2PInfo info);
    void updateInfo(P2PInfo info);
    Tender queryTenderById(Integer id);

}
