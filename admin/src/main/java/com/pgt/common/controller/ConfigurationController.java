package com.pgt.common.controller;

import com.pgt.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by carlwang on 1/5/16.
 */

@RestController
public class ConfigurationController {
    @Autowired
    private Configuration configuration;


    @RequestMapping("/setCapacity/{capacity}")
    @ResponseBody
    public ResponseEntity get(@PathVariable(value = "capacity") Integer capacity) {
        ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(new HashMap<String, Object>(), HttpStatus.OK);
        configuration.setAdminPlpCapacity(capacity);
        Map<String, Object> response = responseEntity.getBody();
        response.put("success", true);
        return responseEntity;
    }
}
