package style;

import com.pgt.style.bean.PageBackground;
import com.pgt.style.service.PageBackgroundService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by xiaodong on 16-1-13.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class PageBackgroundTest {


    @Autowired
    private PageBackgroundService pageBackgroundService;

    @Test
    public void testAdd(){

        PageBackground pageBackground = new PageBackground();
        pageBackground.setStartDate(new Date());
        pageBackground.setEndDate(new Date());
        pageBackgroundService.createPageBackground(pageBackground);

    }





}
