package com.spider.core.common.utils;

/**
 * Redis所有Keys
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-18 19:51
 */
public class RedisKeys {

    public static String getSysConfigKey(String key){
        return "sys:config:" + key;
    }

    /**
     * 数据字典缓存key
     * @param key codeType+codeValue
     * @return
     */
    public static String getSysDictionaryKey(String key){
        return "sys:dictionary:" + key;
    }
    /**
     * 区域缓存key
     * @param key 区域编码
     * @return
     */
    public static String getSysAreaKey(String key){
        return "sys:area:" + key;
    }

    public static String getUserLoginTokenKey(String key){
        return "usr:login:" + key;
    }

    public static String getAppKey(String key){
        return "app:key:" + key;
    }
    public static String getConnectKey(String key){
        return "app:connect:" + key;
    }

}
