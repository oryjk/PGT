package com.pgt.share.service;

import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.share.bean.ShareOrder;
import com.pgt.share.bean.ShareOrderQuery;
import com.pgt.share.dao.ShareOrderMapper;
import com.pgt.user.bean.User;
import com.pgt.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-14.
 */
@Transactional
@Service
public class ShareOrderServiceImpl implements ShareOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareOrderServiceImpl.class);

    @Autowired
    private ShareOrderMapper shareOrderMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Override
    public void createShareOrder(ShareOrder shareOrder) {
           if(ObjectUtils.isEmpty(shareOrder)){
               LOGGER.debug("The shareOrder is empty");
               return ;
           }
        shareOrder.setCreateDate(new Date());
        shareOrder.setReadingQuantity(0);
        shareOrder.setClickHigh(0);
        shareOrder.setClickLow(0);
        LOGGER.debug("User id is {},Product id is{},title is {}",shareOrder.getUser().getId(),
                shareOrder.getProduct().getProductId()
        ,shareOrder.getTitle());
        shareOrderMapper.createShareOrder(shareOrder);
    }

    @Override
    public void updateShareOrder(ShareOrder shareOrder) {
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return ;
        }
        if(ObjectUtils.isEmpty(shareOrder.getShareOrderId())){
            LOGGER.debug("The shareOrderId is empty");
            return;
        }
        shareOrderMapper.updateShareOrder(shareOrder);
    }

    @Override
    public void deleteShareOrderById(Integer shareOrderId) {
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("THe shoreOrderId is empty");
            return;
        }
        shareOrderMapper.deleteShareOrderById(shareOrderId);
        LOGGER.debug("The delete shareOrderId is {}",shareOrderId);
    }

    @Override
    public List<ShareOrder> queryShareOrderByQuery(ShareOrderQuery shareOrderQuery) {
        return shareOrderMapper.queryShareOrderByQuery(shareOrderQuery);
    }

    @Override
    public void updateIsShow(Boolean isShow, Integer shareOrderId) {
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("THe shoreOrderId is empty");
            return;
        }
        ShareOrder shareOrder=shareOrderMapper.queryShareOrderById(shareOrderId);
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return;
        }
        shareOrder.setIsShow(isShow);
        shareOrderMapper.updateShareOrder(shareOrder);
    }

    @Override
    public List<ShareOrder> queryShareOrderByUser(Integer userId) {
         if(ObjectUtils.isEmpty(userId)){
            LOGGER.debug("The userId is empty");
             return null;
         }
         User user=userService.findUser(userId);
         if(ObjectUtils.isEmpty( user)){
            LOGGER.debug("The user is empty and id is {}",userId);
            return null;
         }
        ShareOrderQuery shareOrderQuery = new ShareOrderQuery();
        shareOrderQuery.setUser(user);
        LOGGER.debug("The query ShareOrder for userId is {}",user.getId());
        List<ShareOrder> shareOrders=shareOrderMapper.queryShareOrderByQuery(shareOrderQuery);
        return shareOrders;
    }

    @Override
    public List<ShareOrder> queryShareOrderByProduct(Integer productId) {
        if(ObjectUtils.isEmpty(productId)){
            LOGGER.debug("The productId is empty");
            return null;
        }
        Product product=productServiceImp.queryProduct(productId);
        if(ObjectUtils.isEmpty(product)){
            LOGGER.debug("The product is empty and id is {}",productId);
            return null;
        }
        ShareOrderQuery shareOrderQuery = new ShareOrderQuery();
        shareOrderQuery.setProduct(product);
        LOGGER.debug("The query ShareOrder for productId is {}",product.getProductId());
        List<ShareOrder> shareOrders=shareOrderMapper.queryShareOrderByQuery(shareOrderQuery);
        return shareOrders;
    }

    @Override
    public ShareOrder queryShareOrderById(Integer shareOrderId) {
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return null;
        }
        return shareOrderMapper.queryShareOrderById(shareOrderId);
    }
}
