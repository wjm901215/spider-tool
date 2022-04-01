package com.spider.core.common.i18n;

import com.spider.core.common.constant.Constant;
import com.spider.core.common.utils.ThreadVariable;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * 国际化消息服务
 *
 * @author Spiderman
 * @version $Id: v 0.1 2018/12/4 18:03 Exp $$
 */
@Component
public class MessageSourceService {
    @Resource
    private MessageSource messageSource;

    /**
     * 根据code 获取消息
     *
     * @param code ：对应messages配置的key.
     * @return
     */
    public String getMessage(String code) {
        return getMessage(code, null);
    }

    /**
     * 根据code和数组参数获取消息
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    public String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }


    /**
     * 根据用户浏览器语言获取消息
     *
     * @param code           ：对应messages配置的key.
     * @param args           : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    public String getMessage(String code, Object[] args, String defaultMessage) {
        //获取语言
        Locale locale = null;
        Object lang = ThreadVariable.getInstance().get(Constant.LANG);
        if (null == lang) {
            locale = new Locale("en", "US");
        } else {
            String[] langAndCountry = ((String) lang).split("-");
            locale = new Locale(langAndCountry[0], langAndCountry[1]);
        }
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
