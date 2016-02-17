package com.pgt.pawn.controller;

import com.pgt.configuration.Configuration;
import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.bean.PawnPersonInfoQuery;
import com.pgt.pawn.service.PawnPersonInfoService;
import com.pgt.utils.PaginationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
@RestController
@RequestMapping("/pawnPersonInfo")
public class PawnPersonInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger( PawnPersonInfoController.class);

    @Autowired
    private PawnPersonInfoService pawnPersonInfoService;

    @Autowired
    private Configuration configuration;


    @RequestMapping(value ="/query",method = RequestMethod.GET)
    public ModelAndView queryPawnPersonInfo(@RequestParam(value = "term", required = false) String term,
                                            @RequestParam(value = "sortProperty", required = false) String sortProperty,
                                            @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                                            @RequestParam(value = "currentIndex", required = false) Long currentIndex,ModelAndView modelAndView, PawnPersonInfoQuery pawnPersonInfoQuery){

        Map pawnTypes=configuration.getPawnType();
        if(!CollectionUtils.isEmpty(pawnTypes)){
            modelAndView.addObject("pawnTypes",pawnTypes);
            LOGGER.debug("add pawnTypes");
        }

        if(!ObjectUtils.isEmpty(pawnPersonInfoQuery)){
            if(!ObjectUtils.isEmpty(pawnPersonInfoQuery.getPawnType())){
                  modelAndView.addObject("pawnTypeVo",pawnPersonInfoQuery.getPawnType());
                  LOGGER.debug("add pawnType to modelAndView");
            }
        }

        modelAndView.setViewName("/pawn/pawnPersonInfoList");
        LOGGER.debug("The method to query shareOrder");
        LOGGER.debug("The method query tenderList");
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0L;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        List<PawnPersonInfo> pawnPersonInfoListAll=pawnPersonInfoService.queryPawnPersonInfo(pawnPersonInfoQuery);
        if(ObjectUtils.isEmpty(pawnPersonInfoListAll)){
            LOGGER.debug("The pawnPersonInfoList is empty");
            return modelAndView;
        }
        LOGGER.debug("The totalAmount is {}",pawnPersonInfoListAll.size());
        paginationBean.setTotalAmount(pawnPersonInfoListAll.size());
        pawnPersonInfoQuery.setPaginationBean(paginationBean);

        if (!StringUtils.isEmpty(sortProperty)) {
            LOGGER.debug("The sortProperty is {} and sortValve is {}", sortProperty, sortValue);
            pawnPersonInfoQuery.orderbyCondition(sortValue.endsWith("ASC") ? true : false, sortProperty);
        }
        List<PawnPersonInfo> pawnPersonInfoList=  pawnPersonInfoService.queryPawnPersonInfo(pawnPersonInfoQuery);

        modelAndView.addObject("pawnPersonInfoList",pawnPersonInfoList);
        modelAndView.addObject("paginationBean",paginationBean);
        return modelAndView;
    }


    @RequestMapping(value = "/deletePawnPersonInfo/{id}",method = RequestMethod.GET)
    public ModelAndView deletePawnPersonInfoById(ModelAndView modelAndView,@PathVariable("id") Integer id){
        LOGGER.debug("The method deletePawnPersonInfoById");
        if(ObjectUtils.isEmpty(id)){
            LOGGER.debug("The pawnPersonInfo id is empty");
            return modelAndView;
        }
        pawnPersonInfoService.deletePawnPersonInfoById(id);
        LOGGER.debug("The delete successful and id is {}",id);
        modelAndView.setViewName("redirect:/pawnPersonInfo/query");
        return modelAndView;
    }

    @RequestMapping(value = "/queryPawnPersonInfoById/{id}",method = RequestMethod.GET)
     public ModelAndView queryPawnPersonInfoById(ModelAndView modelAndView,@PathVariable("id") Integer id){
         LOGGER.debug("The method to queryPawnPersonInfo");

        Map pawnTypes=configuration.getPawnType();
        if(!CollectionUtils.isEmpty(pawnTypes)){
            modelAndView.addObject("pawnTypes",pawnTypes);
            LOGGER.debug("add pawnTypes");
        }
        if(ObjectUtils.isEmpty(id)){
             LOGGER.debug("The pawnPersonInfo id is empty");
             return modelAndView;
         }
         PawnPersonInfo pawnPersonInfo=pawnPersonInfoService.queryPawnPersonInfoById(id);
         if(ObjectUtils.isEmpty(pawnPersonInfo)){
             LOGGER.debug("The pawnPersonInfo is empty");
             return modelAndView;
         }
         modelAndView.addObject("pawnPersonInfo",pawnPersonInfo);
         modelAndView.setViewName("/pawn/pawnPersonInfoDetail");
         return modelAndView;
     }

    @RequestMapping(value = "/updateStatus",method = RequestMethod.POST)
    public ModelAndView updateStatus(ModelAndView modelAndView, String contacts,String status,Integer id){

        modelAndView.setViewName("/pawn/pawnPersonInfoDetail");
        LOGGER.debug("The method to update status");
        if(ObjectUtils.isEmpty(id)){
            LOGGER.debug("The pawnPersonInfo id is empty");
            modelAndView.addObject("error","id is empty");
            return modelAndView;
        }

        PawnPersonInfo pawnPersonInfo=pawnPersonInfoService.queryPawnPersonInfoById(id);
        if(ObjectUtils.isEmpty(pawnPersonInfo)){
            LOGGER.debug("The pawnPersonInfo is empty");
            modelAndView.addObject("error","pawnPersonInfo is empty");
            modelAndView.setViewName("redirect:/pawnPersonInfo/queryPawnPersonInfoById"+id);
            return modelAndView;
        }
        if(StringUtils.isEmpty(contacts)){
            LOGGER.debug("The contacts is empty");
            modelAndView.addObject("error","contacts is empty");
            modelAndView.setViewName("redirect:/pawnPersonInfo/queryPawnPersonInfoById"+id);
            return modelAndView;
        }
        if(!StringUtils.isEmpty(status)){
            pawnPersonInfo.setStatus(status);
        }
        pawnPersonInfo.setContacts(contacts);
        pawnPersonInfoService.updatePawnPersonInfo(pawnPersonInfo);
        LOGGER.debug("The method successful and is is {} ,status is {}",id,status);
        modelAndView.setViewName("redirect:/pawnPersonInfo/query");
        return modelAndView;
    }

}
