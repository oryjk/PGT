package com.pgt.share.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.share.bean.ShareOrder;
import com.pgt.share.bean.ShareOrderQuery;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-14.
 */
@Component
public interface ShareOrderMapper extends SqlMapper{

   void createShareOrder(ShareOrder shareOrder);

   void updateShareOrder(ShareOrder shareOrder);

   void deleteShareOrderById(Integer shareOrderId);

   List<ShareOrder> queryShareOrderByQuery(ShareOrderQuery shareOrderQuery);

   ShareOrder queryShareOrderById(Integer shareOrderId);

}
