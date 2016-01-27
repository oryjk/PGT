package com.pgt.tender.controller;

import com.pgt.common.bean.ViewMapperConfiguration;
import com.pgt.configuration.Configuration;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.bean.TenderQuery;
import com.pgt.tender.service.TenderService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 16-1-27.
 */
@RestController
@RequestMapping("/tender")
public class TenderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenderController.class);

    @Autowired
    private TenderService tenderService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private ViewMapperConfiguration viewMapperConfiguration;

    @RequestMapping(value = "/tenderList", method = RequestMethod.GET)
    public ModelAndView get (@RequestParam(value = "term", required = false) String term,
                             @RequestParam(value = "sortProperty", required = false) String sortProperty,
                             @RequestParam(value = "sortValue", required = false, defaultValue = "ASC") String sortValue,
                             @RequestParam(value = "currentIndex", required = false) Long currentIndex, ModelAndView modelAndView) {

        modelAndView.setViewName(viewMapperConfiguration.getTenderListPage());
        LOGGER.debug("The method query tenderList");
        PaginationBean paginationBean= new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0L;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        TenderQuery tenderQuery = new TenderQuery();

        //封装关键字查询
        if(!StringUtils.isEmpty(term)){
            LOGGER.debug("The query term is {}",term);
            tenderQuery.setNameLike(true);
            tenderQuery.setName(term);
        }
        //封装排序
        if(!StringUtils.isEmpty(sortProperty)){
            if(sortProperty=="id"){
              tenderQuery.orderbyId(sortValue.endsWith("ASC")?true:false);
            }
            if(sortProperty=="pawnShopId"){
                tenderQuery.orderbyPawnShopId(sortValue.endsWith("ASC")?true:false);
            }
            if(sortProperty=="pawnTicketId"){
                tenderQuery.orderbyPawnTicketId(sortValue.endsWith("ASC")?true:false);
            }
            if(sortProperty=="publishDate"){
                tenderQuery.orderbyPublishDate(sortValue.endsWith("ASC")?true:false);
            }
        }

        List<Tender> tenderAll=tenderService.queryTenderByQuery(tenderQuery);
        if(CollectionUtils.isEmpty(tenderAll)){
           LOGGER.debug("The query tender is empty");
           return modelAndView;
        }
        paginationBean.setTotalAmount(tenderAll.size());
        tenderQuery.setPaginationBean(paginationBean);
        List<Tender> tenderList= tenderService.queryTenderByQuery(tenderQuery);
            if(!CollectionUtils.isEmpty(tenderList)){
                   modelAndView.addObject("tenderList",tenderList);
                   modelAndView.addObject("paginationBean",paginationBean);
            }
        return modelAndView;
    }

    @RequestMapping(value = "/createUI", method = RequestMethod.GET)
    public ModelAndView createTenderUI(ModelAndView modelAndView){

        LOGGER.debug("The method create tender UI");
        modelAndView.setViewName("/tender/tenderAddAndModify");

        return modelAndView;
    }

    @RequestMapping(value = "/updateUI/{tenderId}",method = RequestMethod.GET)
    public ModelAndView updateTenderUI(ModelAndView modelAndView, @PathVariable("tenderId")Integer tenderId){

       if(ObjectUtils.isEmpty(tenderId)){
           LOGGER.debug("The tenderId is empty");
           return modelAndView;
       }
       Tender tender= tenderService.queryTenderById(tenderId,null);
        if(ObjectUtils.isEmpty(tender)){
            LOGGER.debug("The tender is empty for is {}",tenderId);
            return modelAndView;
        }
        LOGGER.debug("The method update tender UI");
        modelAndView.addObject("tender",tender);
        modelAndView.setViewName("/tender/tenderAddAndModify");
        return modelAndView;
    }

    @RequestMapping(value ="/create",method = RequestMethod.POST)
    public  ModelAndView createTender(ModelAndView modelAndView,Tender tender){
        modelAndView.setViewName("/tender/tenderAddAndModify");
        if(ObjectUtils.isEmpty(tender)){
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(tender.getSmallMoney())){
            LOGGER.debug("The smallMoney is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(tender.getTenderTotal())){
            LOGGER.debug("The tender total is empty");
            return modelAndView;
        }
        tender.setCreationDate(new Date());
        tender.setUpdateDate(new Date());
        int tenderQuantity=tender.getTenderTotal().intValue()/tender.getSmallMoney().intValue();
        tender.setTenderQuantity(tenderQuantity);
        tenderService.createTender(tender);
        modelAndView.setViewName("redirect:/tender/tenderList");
        return modelAndView;
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public  ModelAndView updateTender(ModelAndView modelAndView,Tender tender){
        modelAndView.setViewName("/tender/tenderAddAndModify");
        if(ObjectUtils.isEmpty(tender)){
            LOGGER.debug("The tender is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(tender.getSmallMoney())){
            LOGGER.debug("The smallMoney is empty");
            return modelAndView;
        }
        if(ObjectUtils.isEmpty(tender.getTenderTotal())){
            LOGGER.debug("The tender total is empty");
            return modelAndView;
        }
        tender.setUpdateDate(new Date());
        int tenderQuantity=tender.getTenderTotal().intValue()/tender.getSmallMoney().intValue();
        tender.setTenderQuantity(tenderQuantity);
        tenderService.updateTender(tender);
        modelAndView.setViewName("redirect:/tender/tenderList");
        return modelAndView;
    }

    @RequestMapping(value="/delete/{tenderId}",method= RequestMethod.GET)
    public ResponseEntity delete(@PathVariable("tenderId")Integer tenderId, ModelAndView modelAndView) {

        LOGGER.debug("Delete the tenderId is {}.", tenderId);
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
        Map<String, Object> response = responseEntity.getBody();
        if(ObjectUtils.isEmpty(tenderId)){
            LOGGER.debug("The tender id is null.");
            response.put("success", false);
            response.put("message", "The tender id is empty.");
            return responseEntity;
        }
        tenderService.deleteTender(tenderId);
        response.put("success", true);
        LOGGER.debug("The  deleted with tender id is {}.", tenderId);
        return responseEntity;
    }


}
