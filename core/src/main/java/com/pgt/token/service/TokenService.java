package com.pgt.token.service;

import com.pgt.token.bean.Token;

/**
 * Created by xiaodong on 16-1-14.
 */
public interface TokenService {

    Token queryToken (Token token);

    void deleteTokenById(Integer id);

    void updateToken(Token token);

    void createToken(Token token);

}
