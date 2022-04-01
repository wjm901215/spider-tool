package com.spider.core.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;

/**
 * MD5签名
 *
 * @author Spiderman && professor X
 * @version $Id: com.tettek.service.common.util.MD5Sign,v 0.1 2018/7/17 14:38 Exp $$
 */
public class MD5Sign {
    /**
     * 方法描述:将字符串MD5加码 生成32位md5码
     *
     * @param content
     * @return
     * @author leon 2016年10月10日 下午3:02:30
     */
    public static String md5(String content) {
        try {
            return DigestUtils.md5Hex(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("An error occurred during the MD5 signature process");
        }
    }

}
