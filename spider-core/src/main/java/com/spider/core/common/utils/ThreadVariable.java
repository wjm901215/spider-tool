package com.spider.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局线程变量
 *
 * @author Spiderman
 * @version $Id: com.tettek.service.modules.park.GateWayApi,v 0.1 2018/7/17 11:21 Exp $$
 */
public class ThreadVariable {
    private final static Logger logger = LoggerFactory.getLogger(ThreadVariable.class);
    private static ThreadVariable instance = null;
    private ThreadLocal<Map<Object, Object>> threadLocal;

    public static synchronized ThreadVariable getInstance() {
        if (instance == null) {
            logger.debug("ThreadLocal instance");
            instance = new ThreadVariable();
        }
        return instance;
    }

    public ThreadLocal<Map<Object, Object>> getThreadLocal() {
        if (threadLocal == null) {
            logger.debug("ThreadLocal null");
            threadLocal = new ThreadLocal();
            logger.debug("ThreadLocal created");
            threadLocal.set(new HashMap());
        }
        return threadLocal;
    }

    public void put(String key, Object value) {
        Map map = this.getThreadLocal().get();
        if (map == null) map = new HashMap();
        map.put(key, value);
        this.getThreadLocal().set(map);
    }

    public Object get(String key) {
        Map map = this.getThreadLocal().get();
        if (map == null) return null;
        return map.get(key);
    }

    public void remove(String key) {
        Map map = this.getThreadLocal().get();
        map.remove(key);
        this.getThreadLocal().set(map);
    }

    public void clear() {
        Map map = this.getThreadLocal().get();
        map.clear();
        this.getThreadLocal().set(map);
    }

}
