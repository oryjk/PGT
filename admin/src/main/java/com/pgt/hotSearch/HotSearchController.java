package com.pgt.hotSearch;

import com.pgt.common.service.HotSearchService;
import com.pgt.hot.bean.HotSearch;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by xiaodong on 16-1-30.
 */
@RequestMapping("/hotSearch")
@RestController
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

    private static final Logger LOGGER = LoggerFactory.getLogger(HotSearchController.class);


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(ModelAndView modelAndView,@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
                              @RequestParam(value = "capacity", required = false) Long capacity) {


        List<HotSearch> hotSearchList=hotSearchService.queryHotSearchByQuery(new HotSearch());
        if(CollectionUtils.isEmpty(hotSearchList)){
            LOGGER.debug("The hotSearchList is empty");
         }
        modelAndView.addObject("hotSearchList",hotSearchList);
        modelAndView.setViewName("/hotSearch/listHotSearch");
        return modelAndView;
    }


    @RequestMapping(value = "/addHotSearch", method = RequestMethod.POST)
    public ModelAndView addHotSearch(ModelAndView modelAndView, HotSearch hotSearch) {

        if (ObjectUtils.isEmpty(hotSearch)) {
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }
        hotSearchService.createHotSearch(hotSearch);
        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteHotSearch(ModelAndView modelAndView, Integer id) {

        LOGGER.debug("The method is delete hotSearch {}", id);
        if (id == null) {
            LOGGER.debug("The hotSearch id is empty{}", id);
            return modelAndView;
        }
        hotSearchService.deleteHotSearchById(id);
        return modelAndView;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateHotSearch(ModelAndView modelAndView, HotSearch hotSearch) {

        if (ObjectUtils.isEmpty(hotSearch)) {
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }
        LOGGER.debug("The method is update hotSearch {}", hotSearch.getHotSearchId());
        hotSearchService.updateHotSearch(hotSearch);
        return modelAndView;
    }


}
