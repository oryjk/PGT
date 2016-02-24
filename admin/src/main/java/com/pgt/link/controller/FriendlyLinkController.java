package com.pgt.link.controller;

import com.pgt.configuration.Configuration;
import com.pgt.link.bean.FriendlyLink;
import com.pgt.link.service.FriendlyLinkService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Created by zhangxiaodong on 16-2-24.
 */
@RequestMapping(value = "/friendlyLink")
@RestController
public class FriendlyLinkController {


    private static final Logger LOGGER = LoggerFactory.getLogger(FriendlyLinkController.class);

    @Autowired
    private FriendlyLinkService friendlyLinkService;

    @Autowired
    private Configuration configuration;


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(ModelAndView modelAndView, @RequestParam(value = "currentIndex", required = false) Integer currentIndex,
                              @RequestParam(value = "capacity", required = false) Long capacity) {
         LOGGER.debug("The method query friendlyLink");
        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        if (!ObjectUtils.isEmpty(capacity)) {
            paginationBean.setCapacity(capacity);
        }
        FriendlyLink friendlyLink = new FriendlyLink();
        List<FriendlyLink> totalList=friendlyLinkService.queryFriendlyLinkByQuery(friendlyLink);
        if(!CollectionUtils.isEmpty(totalList)){
            int total=totalList.size();
            paginationBean.setTotalAmount(total);
            friendlyLink.setPaginationBean(paginationBean);
            List<FriendlyLink> LinkList=friendlyLinkService.queryFriendlyLinkByQuery(friendlyLink);
            modelAndView.addObject("paginationBean", paginationBean);
            modelAndView.addObject("LinkList",LinkList);
        }
        modelAndView.setViewName("/link/FriendlyLinkList");
        return modelAndView;
    }


    @RequestMapping(value = "/addFriendlyLinkUI", method = RequestMethod.GET)
    public ModelAndView addFriendlyLinkUI(ModelAndView modelAndView){

        modelAndView.setViewName("/link/FriendlyLinkAddAndModify");
        return  modelAndView;
    }


    @RequestMapping(value = "/addFriendlyLink", method = RequestMethod.POST)
    public ModelAndView addFriendlyLink(ModelAndView modelAndView, FriendlyLink friendlyLink) {

        if (ObjectUtils.isEmpty(friendlyLink)) {
            LOGGER.debug("The FriendlyLink is empty");
            return modelAndView;
        }
        friendlyLinkService.createFriendlyLink(friendlyLink);
        LOGGER.debug("The create hotSearch id is {}",friendlyLink.getId());
        modelAndView.setViewName("redirect:/friendlyLink/query");
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteFriendlyLink(ModelAndView modelAndView, @PathVariable("id")Integer id) {

        LOGGER.debug("The method is delete FriendlyLink {}", id);
        if (id == null) {
            LOGGER.debug("The hotSearch id is empty{}", id);
            return modelAndView;
        }
        friendlyLinkService.deleteHotFriendlyLinkById(id);
        modelAndView.setViewName("redirect:/friendlyLink/query");
        return modelAndView;
    }


    @RequestMapping(value = "/updateFriendlyLinkUI/{id}", method = RequestMethod.GET)
    public ModelAndView updateHotSearchUI(ModelAndView modelAndView,@PathVariable("id")Integer id){
        if(id==null){
            LOGGER.debug("The FriendlyLink is empty");
            return modelAndView;
        }
        FriendlyLink friendlyLink=friendlyLinkService.queryFriendlyLinkById(id);
        if(ObjectUtils.isEmpty(friendlyLink)){
            LOGGER.debug("The friendlyLink is empty");
            return modelAndView;
        }
        modelAndView.addObject("friendlyLink",friendlyLink);
        modelAndView.setViewName("/link/FriendlyLinkAddAndModify");
        return  modelAndView;
    }

    @RequestMapping(value = "/updateFriendlyLink", method = RequestMethod.POST)
    public ModelAndView updateHotSearch(ModelAndView modelAndView, FriendlyLink friendlyLink) {

        if (ObjectUtils.isEmpty(friendlyLink)) {
            LOGGER.debug("The friendlyLink is empty");
            return modelAndView;
        }
        LOGGER.debug("The method is update hotSearch {}", friendlyLink.getId());
        friendlyLinkService.updateFriendlyLink(friendlyLink);
        modelAndView.setViewName("redirect:/friendlyLink/query");
        return modelAndView;
    }


}
