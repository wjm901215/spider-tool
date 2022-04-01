package com.spider.core.common.utils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 输出xml和解析xml的工具类
 */
public class XmlUtil {
    /**
     * java 转换成xml
     *
     * @param obj 对象实例
     * @return String xml字符串
     * @Title: toXml
     * @Description: TODO
     */
    public static String toXml(Object obj) {
        XStream xstream = new XStream();
        //通过注解方式的
        xstream.processAnnotations(obj.getClass());
        return xstream.toXML(obj);
    }

    /**
     * 将传入xml文本转换成Java对象
     *
     * @param xmlStr xml内容
     * @param cls    xml对应的class类
     * @return T   xml对应的class类的实例对象
     * <p>
     * 调用的方法实例：XmlUtil.toBean(xmlStr, XXXBean.class);
     * @Title: toBean
     * @Description: TODO
     */
    public static <T> T toBean(String xmlStr, Class<T> cls) {
        //注意：不是new Xstream(); 否则报错：java.lang.NoClassDefFoundError: org/xmlpull/v1/XmlPullParserFactory
        XStream xstream = new XStream(new DomDriver());
        xstream.processAnnotations(cls);
        T obj = (T) xstream.fromXML(xmlStr);
        return obj;
    }
}