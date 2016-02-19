package com.pgt.backup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-2-19.
 */
@Controller
@RequestMapping("backup")
public class BackupController {
    @Autowired
    private BackupImpl backupImpl;

    @RequestMapping("main")
    public ModelAndView main(ModelAndView modelAndView){
        modelAndView.setViewName("buckup/buckup");
        return  modelAndView;
    }

    @RequestMapping("findall")
    public @ResponseBody String findall(){
        return null;
    }

    @RequestMapping("add")
    public @ResponseBody String add(){
            backupImpl.add();
            return findall();
    }

    @RequestMapping("del")
    public @ResponseBody String del(@RequestParam String filename){
        return findall();
    }
}
