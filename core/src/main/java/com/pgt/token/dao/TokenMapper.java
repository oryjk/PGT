package com.pgt.token.dao;

import com.pgt.base.mapper.SqlMapper;
import com.pgt.token.bean.Token;

/**
 * Created by xiaodong on 16-1-14.
 */
public interface TokenMapper extends SqlMapper {

    Token queryToken (Token token);

    void deleteTokenById(Integer id);

    void updateToken(Token token);

    void createToken(Token token);

}
