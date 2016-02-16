package com.pgt.pawn.controller;

import com.pgt.pawn.service.PawnPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
@RequestMapping(value = "")
@ResponseBody
public class PawnPersonInfo {

    @Autowired
    private PawnPersonInfoService pawnPersonInfoService;




}
