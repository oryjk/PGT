package com.pgt.domain.constant;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by fei on 2016/3/18.
 */
@Component
@Scope("singleton")
public interface DomainConstant {
    public static final  String JUEDANGTAO = "1";
    public static final  String ZAIDANGTAO = "2";
}
