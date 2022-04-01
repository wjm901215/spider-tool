package com.spider.core.common.exception;

import com.spider.core.common.i18n.MessageSourceService;
import com.spider.core.common.spring.SpringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * 自定义异常
 *
 * @author Spiderman
 * @version $Id:v 0.1 2018/12/4 18:03 Exp $$
 */
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static MessageSourceService messageSourceService = SpringUtil.getBean("messageSourceService", MessageSourceService.class);

    private String msg;
    private int code = 500;

    public BusinessException(String msg) {
        super(msg);
        this.msg = getMessageDetail(msg, null);
    }

    public BusinessException(String msg, Throwable e) {
        super(msg, e);
        this.msg = getMessageDetail(msg, null);
    }

    public BusinessException(String msg, int code) {
        super(msg);
        this.msg = getMessageDetail(msg, null);
        this.code = code;
    }

    public BusinessException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = getMessageDetail(msg, null);
        this.code = code;
    }
    /**
     * 业务异常,动态参数
     *
     * @param message 异常code，对应的message.properties
     * @param code    错误码
     * @param args    参数
     */
    public BusinessException(String message, int code, String... args) {
        super(message);
        this.msg = getMessageDetail(message, args);
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * 获取详细信息
     *
     * @param msg  异常信息
     * @param args 动态参数
     * @return 错误描述类型
     */
    private String getMessageDetail(String msg, Object[] args) {
        String messageSource = messageSourceService.getMessage(msg, args);
        if (StringUtils.isEmpty(messageSource)) {
            messageSource = msg;
        }
        return messageSource;
    }
}
