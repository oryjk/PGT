package pawn;

import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.service.PawnPersonInfoService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhangxiaodong on 16-2-16.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-core-config.xml")
public class PawnPersonInfoTest {

    @Autowired
    private PawnPersonInfoService pawnPersonInfoService;

    @Test
    public void testAdd(){

        PawnPersonInfo pawnPersonInfo = new PawnPersonInfo();

        pawnPersonInfo.setAddress("成都");
        pawnPersonInfo.setDetailAddress("武侯区");
        pawnPersonInfo.setGender("男");
        pawnPersonInfo.setName("张");
        pawnPersonInfo.setPawnType("汽车");
        pawnPersonInfo.setPhoneNumber("18482132723");
        pawnPersonInfo.setType("0");
        pawnPersonInfo.setStatus("0");

        pawnPersonInfoService.createPawnPersonInfo(pawnPersonInfo);
        System.out.print("id:"+pawnPersonInfo.getId());

    }



}
