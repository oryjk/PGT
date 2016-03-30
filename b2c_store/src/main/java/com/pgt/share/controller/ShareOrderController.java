package com.pgt.share.controller;
import com.pgt.common.bean.Media;
import com.pgt.communication.bean.Discuss;
import com.pgt.communication.service.DiscussService;
import com.pgt.constant.UserConstant;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.product.bean.Product;
import com.pgt.product.service.ProductServiceImp;
import com.pgt.share.bean.ShareOrder;
import com.pgt.share.bean.ShareOrderQuery;
import com.pgt.share.service.ShareOrderService;
import com.pgt.user.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zhangxiaodong on 16-2-14.
 */
@RestController
@RequestMapping("/shareOrder")
public class ShareOrderController {

    @Autowired
    private ShareOrderService shareOrderService;

    @Autowired
    private ProductServiceImp productServiceImp;

    @Autowired
    private DiscussService discussService;

    @Autowired
    private MediaService mediaService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ShareOrderController.class);

    @RequestMapping(value = "/createShareOrder", method = RequestMethod.GET)
    public ModelAndView createShareOrder(ModelAndView modelAndView,Integer productId){

        if(ObjectUtils.isEmpty(productId)){
            LOGGER.debug("The productId is empty");
            return modelAndView;
        }
        Product product= productServiceImp.queryProduct(productId);
        if(ObjectUtils.isEmpty(product)){
            LOGGER.debug("The product is empty");
            return modelAndView;
        }
        modelAndView.addObject("product",product);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value = "/createShareOrder",method = RequestMethod.POST)
    public ModelAndView createShareOrder(ModelAndView modelAndView, ShareOrder shareOrder, HttpSession session,Integer productId){

        LOGGER.debug("The method is createShareOrder");
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if(ObjectUtils.isEmpty(user)){
            LOGGER.debug("The user is empty");
            modelAndView.addObject("error","user is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(productId)){
            LOGGER.debug("The productId is empty");
            modelAndView.addObject("error","productId is empty");
            return modelAndView;
        }
        Product product= productServiceImp.queryProduct(productId);
        if(ObjectUtils.isEmpty(product)){
            LOGGER.debug("The product is empty");
            modelAndView.addObject("error","product is empty");
            return modelAndView;
        }

        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            modelAndView.addObject("error","shareOrder is empty");
            return modelAndView;
        }
        if(StringUtils.isEmpty(shareOrder.getTitle())){
           LOGGER.debug("The title is empty");
           modelAndView.addObject("error","title is empty");
           return modelAndView;
        }
        if(StringUtils.isEmpty(shareOrder.getEvaluate())){
            LOGGER.debug("The evaluate is empty");
            modelAndView.addObject("error","evaluate is empty");
            return modelAndView;
        }
        if(StringUtils.isEmpty(shareOrder.getStart())){
            LOGGER.debug("The start is empty");
            modelAndView.addObject("error","start is empty");
            return modelAndView;
        }
        shareOrder.setUser(user);
        shareOrder.setProduct(product);
        shareOrderService.createShareOrder(shareOrder);
        LOGGER.debug("The create shareOrder is successful");
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value = "/queryByUser",method = RequestMethod.GET)
    public ModelAndView queryShareOrderByUser(ModelAndView modelAndView,HttpSession session){

        LOGGER.debug("The method is queryShareOrderByUser");
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if(ObjectUtils.isEmpty(user)){
            LOGGER.debug("The user is empty");
            return modelAndView;
        }
        List<ShareOrder> shareOrders=shareOrderService.queryShareOrderByUser(user.getId().intValue());
        modelAndView.addObject("shareOrders",shareOrders);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value = "/queryByProduct",method = RequestMethod.GET)
    public ModelAndView queryShareOrderByProduct(ModelAndView modelAndView,Integer productId){
        LOGGER.debug("The method is queryShareOrderByProduct");
         if(ObjectUtils.isEmpty(productId)) {
             LOGGER.debug("The productId is empty");
             return modelAndView;
         }
        List<ShareOrder> shareOrders=shareOrderService.queryShareOrderByProduct(productId);
        modelAndView.addObject("shareOrders",shareOrders);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value ="/queryById",method = RequestMethod.GET)
    public ModelAndView queryShareOrderById(ModelAndView modelAndView,Integer shareOrderId){
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
        shareOrder.setReadingQuantity(shareOrder.getReadingQuantity()+1);
        shareOrderService.updateShareOrder(shareOrder);
        LOGGER.debug("The ReadingQuantity is {} ",shareOrder.getReadingQuantity());
        modelAndView.addObject("shareOrder",shareOrder);
        modelAndView.setViewName("");
        LOGGER.debug("The method to query successful");
        return modelAndView;
    }

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    public ModelAndView queryShareOrderByQuery(@RequestParam(value = "term", required = false) String term,
                                               @RequestParam(value = "sortProperty", required = false) String sortProperty,
                                               @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                                               ModelAndView modelAndView){
        LOGGER.debug("The method to query shareOrder");
        ShareOrderQuery shareOrderQuery = new ShareOrderQuery();
        if (!StringUtils.isEmpty(term)) {
            LOGGER.debug("The query term is {}", term);
            shareOrderQuery.setTitleLike(true);
            shareOrderQuery.setTitle(term);
        }
        if (!StringUtils.isEmpty(sortProperty)) {
            LOGGER.debug("The sortProerty is {} and sortValve is {}", sortProperty, sortValue);
            shareOrderQuery.orderbyCondition(sortValue.endsWith("ASC") ? true : false, sortProperty);
        }

        List<ShareOrder> shareOrderList=shareOrderService.queryShareOrderByQuery(shareOrderQuery);
        modelAndView.addObject("shareOrderList",shareOrderList);
        modelAndView.setViewName("");
        return modelAndView;
    }

    @RequestMapping(value = "/clickLike",method = RequestMethod.GET)
    public ModelAndView clickLike(ModelAndView modelAndView,Integer shareOrderId){
        LOGGER.debug("The method to clickLike");
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        LOGGER.debug("The shareOrderId is {} ",shareOrderId);
        ShareOrder shareOrder=  shareOrderService.queryShareOrderById(shareOrderId);
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return modelAndView;
        }
        shareOrder.setClickHigh(shareOrder.getClickHigh()+1);
        shareOrderService.updateShareOrder(shareOrder);
        LOGGER.debug("The ClickHigh is {} ",shareOrder.getClickHigh());
        LOGGER.debug("The shareOrder update to clickHigh");
        return modelAndView;
    }

    @RequestMapping(value = "/clickDisLike",method = RequestMethod.GET)
    public ModelAndView clickDisLike(ModelAndView modelAndView,Integer shareOrderId){
        LOGGER.debug("The method to clickDisLike");
        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        LOGGER.debug("The shareOrderId is {} ",shareOrderId);
        ShareOrder shareOrder=  shareOrderService.queryShareOrderById(shareOrderId);
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return modelAndView;
        }
        shareOrder.setClickLow(shareOrder.getClickLow()+1);
        shareOrderService.updateShareOrder(shareOrder);
        LOGGER.debug("The ClickLow is {} ",shareOrder.getClickLow());
        LOGGER.debug("The shareOrder update to clickLow");
        return modelAndView;
    }


    @RequestMapping(value = "/shareOrderReply",method = RequestMethod.POST)
    public ModelAndView shareOrderReply (ModelAndView modelAndView, Discuss discuss, Integer shareOrderId, HttpServletRequest request,HttpSession session){

        if(ObjectUtils.isEmpty(shareOrderId)){
            LOGGER.debug("The shareOrderId is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(discuss)){
            LOGGER.debug("The discuss is empty");
            return modelAndView;
        }
        if(StringUtils.isEmpty(discuss.getContent())){
            LOGGER.debug("The discuss content is empty");
            return modelAndView;
        }
        String ip = request.getRemoteAddr();
        User user = (User) session.getAttribute(UserConstant.CURRENT_USER);
        if(ObjectUtils.isEmpty(user)){
           LOGGER.debug("The user is empty");
           return modelAndView;
        }
        LOGGER.debug("The shareOrderId is {} ",shareOrderId);
        ShareOrder shareOrder=shareOrderService.queryShareOrderById(shareOrderId);
        if(ObjectUtils.isEmpty(shareOrder)){
            LOGGER.debug("The shareOrder is empty");
            return modelAndView;
        }
        discuss.setUser(user);
        discuss.setShareOrder(shareOrder);
        discuss.setIp(ip);
        LOGGER.debug("The user ip is {}",ip);
        LOGGER.debug("The user id is {}",user.getId());
        discussService.createDiscuss(discuss);
        return modelAndView;
    }

    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createMedias(Media media) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
        if (media.getType().equals(MediaType.shareOrder.toString())) {
            List<Media> medias = mediaService.findMediaByRefId(media.getReferenceId(),MediaType.shareOrder);
        }
        Integer mediaId = mediaService.createMedia(media);
        responseEntity.getBody().put("success", true);
        responseEntity.getBody().put("mediaId", mediaId);
        responseEntity.getBody().put("mediaType",media.getType());
        return responseEntity;
    }

}
