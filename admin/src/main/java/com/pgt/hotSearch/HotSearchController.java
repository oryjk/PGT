package com.pgt.hotSearch;

import com.pgt.common.bean.Media;
import com.pgt.common.service.HotSearchService;
import com.pgt.configuration.Configuration;
import com.pgt.hot.bean.HotSearch;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 16-1-30.
 */
@RequestMapping("/hotSearch")
@RestController
public class HotSearchController {

    @Autowired
    private HotSearchService hotSearchService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Configuration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(HotSearchController.class);


    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public ModelAndView query(ModelAndView modelAndView,@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
                              @RequestParam(value = "capacity", required = false) Long capacity) {

        PaginationBean paginationBean = new PaginationBean();

        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 0;
        }
        paginationBean.setCurrentIndex(currentIndex);
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        if (!ObjectUtils.isEmpty(capacity)) {
            paginationBean.setCapacity(capacity);
        }
        HotSearch hotSearch = new HotSearch();
        List<HotSearch> totalList=hotSearchService.queryHotSearchByQuery(hotSearch );

        if(!CollectionUtils.isEmpty(totalList)){
            int total=totalList.size();
            paginationBean.setTotalAmount(total);
            hotSearch.setPaginationBean(paginationBean);
            List<HotSearch> hotSearchList=hotSearchService.queryHotSearchByQuery(hotSearch );
            modelAndView.addObject("paginationBean", paginationBean);
            modelAndView.addObject("hotSearchList",hotSearchList);
        }
        modelAndView.setViewName("/hotSearch/listHotSearch");
        return modelAndView;
    }


    @RequestMapping(value = "/addHotSearchUI", method = RequestMethod.GET)
    public ModelAndView addHotSearchUI(ModelAndView modelAndView){

        modelAndView.setViewName("/hotSearch/hotSearchAddAndModify");
        return  modelAndView;
    }


    @RequestMapping(value = "/addHotSearch", method = RequestMethod.POST)
    public ModelAndView addHotSearch(ModelAndView modelAndView, HotSearch hotSearch) {

        if (ObjectUtils.isEmpty(hotSearch)) {
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }
        hotSearchService.createHotSearch(hotSearch);
        LOGGER.debug("The create hotSearch id is {}",hotSearch.getHotSearchId());
        modelAndView.setViewName("redirect:/hotSearch/updateHotSearchUI/"+hotSearch.getHotSearchId());
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{hotSearchId}", method = RequestMethod.GET)
    public ModelAndView deleteHotSearch(ModelAndView modelAndView, @PathVariable("hotSearchId")Integer hotSearchId) {

        LOGGER.debug("The method is delete hotSearch {}", hotSearchId);
        if (hotSearchId == null) {
            LOGGER.debug("The hotSearch id is empty{}", hotSearchId);
            return modelAndView;
        }
        hotSearchService.deleteHotSearchById(hotSearchId);
        Media oldMedia = mediaService.queryHotProductFrontMedia(hotSearchId);
        if (!ObjectUtils.isEmpty(oldMedia)) {
            mediaService.deleteMedia(oldMedia.getId());
        }
        modelAndView.setViewName("redirect:/hotSearch/query");
        return modelAndView;
    }


    @RequestMapping(value = "/updateHotSearchUI/{hotSearchId}", method = RequestMethod.GET)
    public ModelAndView updateHotSearchUI(ModelAndView modelAndView,@PathVariable("hotSearchId")Integer hotSearchId){

        if(hotSearchId==null){
            LOGGER.debug("The hotSearchId is empty");
            return modelAndView;
        }
        HotSearch hotSearch=hotSearchService.queryHotSearchById(hotSearchId);
        if(ObjectUtils.isEmpty(hotSearch)){
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }
        modelAndView.addObject("hotSearch",hotSearch);
        modelAndView.setViewName("/hotSearch/hotSearchAddAndModify");
        return  modelAndView;
    }

    @RequestMapping(value = "/updateHotSearch", method = RequestMethod.POST)
    public ModelAndView updateHotSearch(ModelAndView modelAndView, HotSearch hotSearch) {

        if (ObjectUtils.isEmpty(hotSearch)) {
            LOGGER.debug("The hotSearch is empty");
            return modelAndView;
        }
        LOGGER.debug("The method is update hotSearch {}", hotSearch.getHotSearchId());
        hotSearchService.updateHotSearch(hotSearch);
        modelAndView.setViewName("redirect:/hotSearch/query");
        return modelAndView;
    }


    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createMedias(Media media) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);

        if (media.getType().equals(MediaType.hot.toString())) {
            Media oldMedia = mediaService.queryHotProductFrontMedia(media.getReferenceId().intValue());
            if (!ObjectUtils.isEmpty(oldMedia)) {
                mediaService.deleteMedia(oldMedia.getId());
            }
        }
        Integer mediaId = mediaService.createMedia(media);
        responseEntity.getBody().put("success", true);
        responseEntity.getBody().put("mediaId", mediaId);
        responseEntity.getBody().put("mediaType",media.getType());
        return responseEntity;
    }



}
