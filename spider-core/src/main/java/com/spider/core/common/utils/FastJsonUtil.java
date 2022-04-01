package com.spider.core.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 *
 * @author Spiderman
 * @version $Id: com.zyiot.tet.common.util.FastJsonUtil,v 0.1 2018/7/17 13:46 Exp $$
 */
public class FastJsonUtil {
    private static final Logger logger = LoggerFactory.getLogger(FastJsonUtil.class);

    /**
     * json转换list
     *
     * @param jsontext
     * @param list_str
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> List<T> getList(String jsontext, String list_str, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(list_str);
        if (obj == null) {
            return null;
        }
        // if(obj instanceof JSONObject){}
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }

    /**
     * json转换对象
     *
     * @param <T>      -> DepartmentBean
     * @param jsontext -> {"department":{"id":"1","name":"test"},"password":"admin",
     *                 "username":"admin"}
     * @param obj_str  -> department
     * @param clazz    -> DepartmentBean
     * @return -> T
     */
    public static final <T> T getObject(String jsontext, String obj_str, Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsontext);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(obj_str, clazz);
        } else {
            logger.info(obj.getClass() + "");
        }

        return null;
    }

    /**
     * json 对象转换
     * 注：传入任意的jsontext,返回的T都不会为null,只是T的属性为null
     *
     * @param <T>
     * @param jsontext ->{"department":{"id":"1","name":"test"},"password":"admin",
     *                 "username":"admin"}
     * @param clazz    -> UserBean.class
     * @return -> UserBean
     */
    public static final <T> T getObject(String jsontext, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsontext, clazz);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsontext, e);
        }
        return t;
    }

    /**
     * 对象转换json串
     *
     * @param object
     * @return
     */
    public static final String toJSONString(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 对象转换json串
     *
     * @param object
     * @param prettyFormat
     * @return
     */
    public static final String toJSONString(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * json字符串转成为List
     *
     * @param jsonStr json字符串
     * @param clazz   class名称
     * @param <T>
     * @return
     */
    public static <T> List<T> getList(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            logger.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * json字符串转换成list<Map>
     *
     * @param jsonString json字符串
     * @return
     */
    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            logger.error("json字符串转map失败", e);
        }
        return list;
    }

    /**
     * json字符串转换为Map
     *
     * @param jsonStr json字符串
     * @return
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            logger.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }

    /**
     * 对象转换
     *
     * @param _target 目标
     * @param _source 源
     * @return
     */
    public static Object convertObj(Object _target, Object _source) {
        String json = FastJsonUtil.toJSONString(_source);
        return FastJsonUtil.getObject(json, _target.getClass());
    }

}
