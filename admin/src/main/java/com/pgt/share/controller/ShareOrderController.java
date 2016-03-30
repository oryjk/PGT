package com.pgt.share.controller;
import com.pgt.configuration.Configuration;
import com.pgt.share.bean.ShareOrder;
import com.pgt.share.bean.ShareOrderQuery;
import com.pgt.share.service.ShareOrderService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


/**
 * Created by zhangxiaodong on 16-2-14.
 */
@RestController
@RequestMapping("/shareOrder")
public class ShareOrderController {

    @Autowired
    private ShareOrderService shareOrderService;

    @Autowired
    private Configuration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareOrderController.class);

    @RequestMapping(value = "/queryShareOrderById/{shareOrderId}",method = RequestMethod.GET)
    public ModelAndView queryShareOrderById(ModelAndView modelAndView,@PathVariable("shareOrderId")Integer shareOrderId){
        LOGGER.debug("The method to query shareOrder By Id");
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        LOGGER.debug("The shareOrderId is {} ",shareOrderId);
        ShareOrder shareOrder=shareOrderService.queryShareOrderById(shareOrderId);
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return modelAndView;
        }
        modelAndView.addObject("shareOrder",shareOrder);
        modelAndView.setViewName("");
        LOGGER.debug("The method to query successful");
        return modelAndView;
    }

    @RequestMapping(value = "/updateShow/{shareOrderId}/{isShow}",method = RequestMethod.GET)
    public ModelAndView updateIsShow(ModelAndView modelAndView,@PathVariable("shareOrderId")Integer shareOrderId,
                                     @PathVariable("isShow")Boolean isShow){
        LOGGER.debug("The method to update IsShow");
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(isShow)){
            LOGGER.debug("The isSHow is empty");
            return modelAndView;
        }
        shareOrderService.updateIsShow(isShow,shareOrderId);
        LOGGER.debug("update isShow to {} and id is {}",isShow,shareOrderId);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteShareOrder/{shareOrderId}",method = RequestMethod.GET)
    public ModelAndView deleteShareOrderById(ModelAndView modelAndView,@PathVariable("shareOrderId") Integer shareOrderId){
        LOGGER.debug("The method to delete shareOrder by Id");
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        shareOrderService.deleteShareOrderById(shareOrderId);
        LOGGER.debug("The delete shareOrder is successful and id is {}",shareOrderId);
        return modelAndView;
    }


    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView queryShareOrderByQuery(@RequestParam(value = "term", required = false) String term,
                                               @RequestParam(value = "sortProperty", required = false) String sortProperty,
                                               @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                                               @RequestParam(value = "currentIndex", required = false) Long currentIndex,
                                               ModelAndView modelAndView){
        modelAndView.setViewName("/shareOrder/shareOrderList");
        LOGGER.debug("The method to query shareOrder");
        LOGGER.debug("The method query tenderList");
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0L;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());

        ShareOrderQuery shareOrderQuery = new ShareOrderQuery();
        List<ShareOrder> shareOrderListAll=shareOrderService.queryShareOrderByQuery(shareOrderQuery);
        if (CollectionUtils.isEmpty(shareOrderListAll)) {
            LOGGER.debug("The query tender is empty");
            return modelAndView;
        }
        LOGGER.debug("The totalAmount is {}",shareOrderListAll.size());
        paginationBean.setTotalAmount(shareOrderListAll.size());
        shareOrderQuery.setPaginationBean(paginationBean);
        if (!StringUtils.isEmpty(sortProperty)) {
            LOGGER.debug("The sortProperty is {} and sortValve is {}", sortProperty, sortValue);
            shareOrderQuery.orderbyCondition(sortValue.endsWith("ASC") ? true : false, sortProperty);
        }
        if (!StringUtils.isEmpty(term)) {
            LOGGER.debug("The query term is {}", term);
            shareOrderQuery.setTitleLike(true);
            shareOrderQuery.setTitle(term);
        }
        List<ShareOrder> shareOrderList=shareOrderService.queryShareOrderByQuery(shareOrderQuery);
        modelAndView.addObject("shareOrderList",shareOrderList);
        modelAndView.addObject("paginationBean", paginationBean);
        return modelAndView;
    }

}
