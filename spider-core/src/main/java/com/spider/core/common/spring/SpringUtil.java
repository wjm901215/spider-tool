package com.spider.core.common.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring 工具类
 */
@Component
public class SpringUtil {
    private static ApplicationContext app = null;

    public static ApplicationContext getApp() {
        return app;
    }

    public static void setAPP(ApplicationContext context){
        app=context;
    }

    public static Object getBean(String name) {
        return getApp().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        try {
            return getApp().getBean(clazz);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApp().getBean(name, clazz);
    }

    public static String getProp(String prop){
        PropVarb pv=(PropVarb)SpringUtil.getBean("propvarb");
        return pv.getProp(prop);
    }
}

