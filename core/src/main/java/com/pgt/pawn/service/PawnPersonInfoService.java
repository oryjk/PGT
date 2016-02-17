package com.pgt.pawn.service;

import com.pgt.pawn.bean.PawnPersonInfo;
import com.pgt.pawn.bean.PawnPersonInfoQuery;

import java.util.List;

/**
 * Created by zhangxiaodong on 16-2-15.
 */
public interface PawnPersonInfoService {

    void createPawnPersonInfo(PawnPersonInfo pawnPersonInfo);

    void updatePawnPersonInfo(PawnPersonInfo pawnPersonInfo);

    void deletePawnPersonInfoById(Integer id);

    List<PawnPersonInfo> queryPawnPersonInfo(PawnPersonInfoQuery pawnPersonInfoQuery);

    PawnPersonInfo queryPawnPersonInfoById(Integer id);

}
