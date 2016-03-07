package com.pgt.controller.constant;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by liqiang on 16-3-7.
 */
@Component
@Scope("singleton")
public class WarConstant {
    public static final String GRADLEURL = "gradle.properties";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String EXETUTESHURL = "executewar.sh";
}
