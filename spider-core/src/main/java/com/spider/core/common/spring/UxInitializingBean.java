package com.spider.core.common.spring;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Spiderman
 */
public class UxInitializingBean implements ApplicationContextInitializer<ConfigurableApplicationContext>,ApplicationListener {

    private ConfigurableApplicationContext app;

    @Override
    public  void initialize(ConfigurableApplicationContext context){
        app=context;
        SpringUtil.setAPP(app);
    }
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent){

    }
}
