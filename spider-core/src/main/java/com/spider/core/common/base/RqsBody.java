package com.spider.core.common.base;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * 接口请求基类
 *
 * @author Spiderman
 * @version $Id: v 0.1 2018/7/17 11:13 Exp $$
 */
@Data
public class RqsBody {
    /**
     * 应用ID
     */
    private String appid;
    /**
     * 用户登陆sessionid
     */
    private String sid;
    /**
     * 接口名称
     */
    private String method;
    /**
     * 发送请求的时间，格式"yyyyMMddHHmmss"
     */
    private String timestamp;
    /**
     * Base64(ECB模式的DES加密(json格式业务数据data值，加密key))
     */
    private String data;
    /**
     * 签名
     * Base64(md5(sid+method+timestamp+encode_data))
     */
    private String sign;

    /**
     * 校验字段是否为空
     *
     * @return
     */
    public boolean checkFieldIsNull() {
        if (StringUtils.isBlank(appid)||StringUtils.isBlank(method)
                || StringUtils.isBlank(timestamp) || StringUtils.isBlank(sign)) {
            return true;
        }
        return false;
    }
}
