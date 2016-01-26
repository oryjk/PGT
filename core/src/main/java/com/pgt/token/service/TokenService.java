package com.pgt.token.service;

import com.pgt.token.bean.Token;

/**
 * Created by xiaodong on 16-1-14.
 */
public interface TokenService {

    /**
     * query a token
     * @param token
     * @return
     */
    Token queryToken (Token token);

    /**
     * delete token by id
     * @param id
     */
    void deleteTokenById(Integer id);

    /**
     * update token
     * @param token
     */
    void updateToken(Token token);

    /**
     * create a token
     * @param token
     */
    void createToken(Token token);

}
