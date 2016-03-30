package com.pgt.contact;


import com.pgt.configuration.URLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by liqiang on 16-2-18.
 *
 * about-us
 */
@Controller
@RequestMapping("/contact")
public class ContactUsController {
    private static final Logger LOGGER	= LoggerFactory.getLogger(ContactUsController.class);
    @Autowired
    private URLConfiguration urlConfiguration;

    /**
     * add address method
     * @param modelAndView
     * @return
     */
    @RequestMapping(value = "main")
    public ModelAndView addAddressPage(ModelAndView modelAndView){
        modelAndView.setViewName("contact/contact");
        LOGGER.debug("success and go to contact view");
        return modelAndView;
    }
}
