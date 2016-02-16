package com.pgt.share.service;

import com.pgt.share.bean.ShareOrder;
import com.pgt.share.bean.ShareOrderQuery;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-14.
 */
public interface ShareOrderService {

    /**
     * create shareOrder
     * @param shareOrder
     */
    void createShareOrder(ShareOrder shareOrder);

    /**
     * update shareOrder
     * @param shareOrder
     */
    void updateShareOrder(ShareOrder shareOrder);

    /**
     *  delete a shareOrder
     * @param shareOrderId
     */
    void deleteShareOrderById(Integer shareOrderId);

    /**
     * query shareOrder by shareOrderQuery
     * @param shareOrderQuery
     * @return
     */
    List<ShareOrder> queryShareOrderByQuery(ShareOrderQuery shareOrderQuery);

    /**
     * update show status for true/false
     * @param isShow
     * @param shareOrderId
     */
    void updateIsShow(Boolean isShow,Integer shareOrderId);

    /**
     * query User ShareOrder
     * @param user
     * @return
     */
    List<ShareOrder> queryShareOrderByUser(Integer userId);

    /**
     * query product ShareOrder
     * @param productId
     * @return
     */
    List<ShareOrder> queryShareOrderByProduct(Integer productId);

    ShareOrder queryShareOrderById(Integer shareOrderId);
}
