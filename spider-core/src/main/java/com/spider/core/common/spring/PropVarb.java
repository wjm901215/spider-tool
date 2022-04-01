package com.spider.core.common.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("propvarb")
public class PropVarb {

    @Autowired
    private Environment env;

    public int id=1;
    public String getProp(String s) {
        return env.getProperty(s);
    }
}
