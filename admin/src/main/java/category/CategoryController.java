package category;

import com.pgt.category.bean.Category;
import com.pgt.category.service.CategoryService;
import com.pgt.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by carlwang on 12/17/15.
 */

@RestController
@RequestMapping("/category")
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(ModelAndView modelAndView) {
        LOGGER.debug("create GET.");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Category category, ModelAndView modelAndView, BindingResult bindingResult, HttpServletRequest request,
                               HttpServletResponse response) {
        LOGGER.debug("Begin create category.");
        if (bindingResult.hasErrors()) {
            LOGGER.debug("Some data value mission.");
            return modelAndView;
        }
        String categoryId = categoryService.createCategory(category);
        LOGGER.debug("The category is is {}.", categoryId);
        LOGGER.debug("end create category.");
        return modelAndView;
    }
}
