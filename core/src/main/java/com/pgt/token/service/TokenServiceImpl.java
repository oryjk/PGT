package com.pgt.token.service;

import com.pgt.token.bean.Token;
import com.pgt.token.dao.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by xiaodong on 16-1-14.
 */
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
     private TokenMapper tokenMapper;

    @Override
    public Token queryToken(Token token) {
        return tokenMapper.queryToken(token);
    }

    @Override
    public void deleteTokenById(Integer id) {
          tokenMapper.deleteTokenById(id);
    }

    @Override
    public void updateToken(Token token) {
         tokenMapper.updateToken(token);
    }

    @Override
    public void createToken(Token token) {
        tokenMapper.createToken(token);
    }
}

