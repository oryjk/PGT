package com.pgt.pawn.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.bean.PawnPersonInfoQuery;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
@Component
public interface PawnPersonInfoMapper  extends SqlMapper{

    void createPawnPersonInfo(PawnPersonInfo pawnPersonInfo);

    void updatePawnPersonInfo(PawnPersonInfo pawnPersonInfo);

    void deletePawnPersonInfoById(Integer id);

    List<PawnPersonInfo> queryPawnPersonInfo(PawnPersonInfoQuery pawnPersonInfoQuery);

    PawnPersonInfo queryPawnPersonInfoById(Integer id);
}
