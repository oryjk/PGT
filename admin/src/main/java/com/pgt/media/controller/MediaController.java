package com.pgt.media.controller;

import com.pgt.media.MediaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlwang on 1/6/16.
 */
@RestController
@RequestMapping("/media")
public class MediaController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MediaController.class);
    @Autowired
    private MediaService mediaService;

    @RequestMapping(value = "/delete/{mediaId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity deleteMedia(@PathVariable("mediaId") Integer mediaId) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
        if (ObjectUtils.isEmpty(mediaId)) {
            LOGGER.debug("The media id is empty.");
            responseEntity.getBody().put("success", false);
            responseEntity.getBody().put("message", "the media id is empty");
            return responseEntity;
        }
        mediaService.deleteMedia(mediaId);
        responseEntity.getBody().put("success", true);
        return responseEntity;
    }
}
