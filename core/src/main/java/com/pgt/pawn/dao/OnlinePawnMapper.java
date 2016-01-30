package com.pgt.pawn.dao;


import com.pgt.base.mapper.SqlMapper;
import com.pgt.pawn.bean.OnlinePawn;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hd on 16-1-28.
 */
@Repository
public interface OnlinePawnMapper extends SqlMapper {

    Integer add(OnlinePawn onlinePawn);

    OnlinePawn select(Integer id);

    Integer update(OnlinePawn onlinePawn);

    Integer delete(Integer id);

    List<OnlinePawn> findByPhoneNumber(String  phoneNumber);

}
