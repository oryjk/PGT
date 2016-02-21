package com.pgt.media.helper;

import com.pgt.common.bean.Media;
import com.pgt.media.MediaService;
import com.pgt.search.service.TenderSearchEngineService;
import com.pgt.tender.bean.Tender;
import com.pgt.tender.service.TenderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Created by carlwang on 2/21/16.
 */

@Service
public class MediaHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaHelper.class);
    @Autowired
    private MediaService mediaService;
    @Autowired
    private TenderSearchEngineService tenderSearchEngineService;
    @Autowired
    private TenderService tenderService;

    public Integer addMultiMedia(Media media, Class clazz) {
        if (media.getType() == null) {
            LOGGER.debug("The media type is empty.");
            return -1;
        }
        if (media.getReferenceId() == null) {
            LOGGER.debug("The ref id is empty.");
            return -1;
        }
        Tender tender;
        if (checkTenderExsit(media, clazz)) return -1;
        mediaService.createMedia(media);
        tender = tenderService.queryTenderById(media.getReferenceId(), false);
        tenderSearchEngineService.updateTender(tender);
        return media.getId();
    }


    public Integer addSingleMedia(Media media, Class clazz) {
        if (media.getType() == null) {
            LOGGER.debug("The media type is empty.");
            return -1;
        }
        if (media.getReferenceId() == null) {
            LOGGER.debug("The ref id is empty.");
            return -1;
        }
        Tender tender;
        if (checkTenderExsit(media, clazz)) return -1;
        List<Media> mediaList = mediaService.findMediaByRefId(media.getReferenceId(), media.getType());
        if (!CollectionUtils.isEmpty(mediaList)) {
            mediaList.stream().forEach(media1 -> mediaService.deleteMedia(media1.getId()));
        }
        mediaService.createMedia(media);
        tender = tenderService.queryTenderById(media.getReferenceId(), false);
        tenderSearchEngineService.updateTender(tender);
        return media.getId();
    }

    private boolean checkTenderExsit(Media media, Class clazz) {
        Tender tender;
        if (clazz.equals(Tender.class)) {
            tender = tenderService.queryTenderById(media.getReferenceId(), false);
            if (ObjectUtils.isEmpty(tender)) {
                LOGGER.debug("Can not find tender with id is {}.", media.getReferenceId());
                return true;
            }
        }
        return false;
    }

}
