package com.pgt.style;
import com.pgt.common.bean.Media;
import com.pgt.configuration.Configuration;
import com.pgt.configuration.URLConfiguration;
import com.pgt.media.MediaService;
import com.pgt.media.bean.MediaType;
import com.pgt.style.bean.PageBackground;
import com.pgt.style.bean.PageBackgroundQuery;
import com.pgt.style.service.PageBackgroundService;
import com.pgt.utils.PaginationBean;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaodong on 16-1-13.
 */
@RestController
@RequestMapping("/pageBackground")
public class PageBackgroundController {

    @Autowired
    private PageBackgroundService pageBackgroundService;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private Configuration configuration;

    private static final Logger LOGGER = LoggerFactory.getLogger(PageBackgroundController.class);



    @RequestMapping(value="/createPageBackgroundUI",method = RequestMethod.GET)
    public ModelAndView createPageBackgroundUI(ModelAndView modelAndView){

        modelAndView.setViewName("/style/backgrounAddandModify");
        return modelAndView;
    }

    @RequestMapping(value="/createPageBackground",method = RequestMethod.POST)
    public ModelAndView createPageBackground(ModelAndView modelAndView, PageBackground pageBackground,String herder){

        if(ObjectUtils.isEmpty(pageBackground)){
            LOGGER.debug("The pageBackground is empty");
             return modelAndView;
        }
        pageBackground.setCreationDate(new Date());
        pageBackground.setUpdateDate(new Date());
        pageBackgroundService.createPageBackground(pageBackground);


        LOGGER.debug("PageBackground has created, the pageBackground  is {}", pageBackground.getPageBackgroundId());
        modelAndView.setViewName("redirect:/pageBackground/createPageBackgroundImage/"+pageBackground.getPageBackgroundId());
        return modelAndView;
    }


    @RequestMapping(value="/createPageBackgroundImage/{pageBackgroundId}",method = RequestMethod.GET)
    public ModelAndView createPageBackgroundImage(ModelAndView modelAndView,@PathVariable("pageBackgroundId") Integer pageBackgroundId){

        if(pageBackgroundId==null){
            LOGGER.debug("The pageBackgroundId is empty");
            return modelAndView;
        }
        PageBackgroundQuery pageBackgroundQuery = new PageBackgroundQuery();
        pageBackgroundQuery.setPageBackgroundId(pageBackgroundId);
        List<PageBackground> pageBackgroundList=pageBackgroundService.queryPageBackground(pageBackgroundQuery);
        if(CollectionUtils.isEmpty(pageBackgroundList)){
            LOGGER.debug("The updatePageBackground is empty");
        }
        PageBackground pageBackground=pageBackgroundList.get(0);
        modelAndView.addObject("pageBackground",pageBackground);
        modelAndView.addObject("staticServer", configuration.getStaticServer());
        modelAndView.setViewName("/style/backgroundImageModify");
        return  modelAndView;
    }




    @RequestMapping(value="/updatePageBackgroundUI/{pageBackgroundId}",method = RequestMethod.GET)
    public ModelAndView updatePageBackgroundUI(@PathVariable("pageBackgroundId") Integer pageBackgroundId, ModelAndView modelAndView) {

        PageBackgroundQuery pageBackgroundQuery = new PageBackgroundQuery();
        pageBackgroundQuery.setPageBackgroundId(pageBackgroundId);
        List<PageBackground> pageBackgroundList=pageBackgroundService.queryPageBackground(pageBackgroundQuery);
        if(CollectionUtils.isEmpty(pageBackgroundList)){
            LOGGER.debug("The updatePageBackground is empty");
        }
        PageBackground pageBackground=pageBackgroundList.get(0);
        modelAndView.addObject("pageBackground",pageBackground);
        modelAndView.setViewName("/style/backgrounAddandModify");
        return modelAndView;
    }


    @RequestMapping(value="/updatePageBackground",method = RequestMethod.POST)
    public ModelAndView updatePageBackground(ModelAndView modelAndView,PageBackground pageBackground){

       if(ObjectUtils.isEmpty(pageBackground)){
           LOGGER.debug("The pageBackground is empty");
           return modelAndView;
       }
       pageBackground.setUpdateDate(new Date());
       pageBackgroundService.updatePageBackground(pageBackground);
       LOGGER.debug("PageBackground has update, the pageBackground  is {}", pageBackground.getPageBackgroundId());
        modelAndView.setViewName("redirect:/pageBackground/createPageBackgroundImage/"+pageBackground.getPageBackgroundId());
        return modelAndView;
   }


    @RequestMapping(value = "/create/stepImage", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity createMedias(Media media) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);

        if (media.getType().equals(MediaType.header.toString())) {
            Media oldMedia = mediaService.queryPageBackgroundHeaderMedia(media.getReferenceId());
            if (!ObjectUtils.isEmpty(oldMedia)) {
                mediaService.deleteMedia(oldMedia.getId());
            }
        }
        if (media.getType().equals(MediaType.footer.toString())) {
            Media oldMedia = mediaService.queryPageBackgroundFooterMedia(media.getReferenceId());
            if (!ObjectUtils.isEmpty(oldMedia)) {
                mediaService.deleteMedia(oldMedia.getId());
            }
        }
        if (media.getType().equals(MediaType.middle.toString())) {
            Media oldMedia = mediaService.queryPageBackgroundMiddleMedia(media.getReferenceId());
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



    @RequestMapping(value="/deletePageBackground/{pageBackgroundId}")
    public ModelAndView deletePageBackground(ModelAndView modelAndView,@PathVariable("pageBackgroundId")String pageBackgroundId){

        LOGGER.debug("Delete the pageBackground with id  is {}.",pageBackgroundId);
       if(StringUtils.isEmpty(pageBackgroundId)){
           LOGGER.debug("The pageBackGroundId is Empty");
           return modelAndView;
       }
        LOGGER.debug("Delete the pageBackground with id  is {}.",pageBackgroundId);
        pageBackgroundService.deletePageBackgroundById(Integer.parseInt(pageBackgroundId));
        modelAndView.setViewName("redirect:/pageBackground/queryPageBackground");
        return modelAndView;
    }


    @RequestMapping(value = "/queryPageBackground",method=RequestMethod.GET)
    public ModelAndView queryPageBackground(ModelAndView modelAndView,	@RequestParam(value = "currentIndex", required = false) Integer currentIndex,
                                            @RequestParam(value = "capacity", required = false) Long capacity){

        PaginationBean paginationBean = new PaginationBean();
        if (ObjectUtils.isEmpty(currentIndex)) {
            currentIndex = 1;
        }
        paginationBean.setCapacity(configuration.getAdminCategoryCapacity());
        if (!ObjectUtils.isEmpty(capacity)) {
            paginationBean.setCapacity(capacity);
        }

        paginationBean.setCurrentIndex((currentIndex - 1) * paginationBean.getCapacity());
        PageBackgroundQuery pageBackgroundQuery = new PageBackgroundQuery();
        int total=pageBackgroundService.queryPageBackgroundCount(pageBackgroundQuery);
        paginationBean.setTotalAmount(total);

        pageBackgroundQuery.setPaginationBean(paginationBean);
        List<PageBackground> pageBackgroundList = pageBackgroundService.queryPageBackground(pageBackgroundQuery);
        modelAndView.addObject("pageBackgroundList",pageBackgroundList);
        modelAndView.addObject("paginationBean",paginationBean);
        modelAndView.setViewName("/style/backgroundList");
        return modelAndView;
    }


}
