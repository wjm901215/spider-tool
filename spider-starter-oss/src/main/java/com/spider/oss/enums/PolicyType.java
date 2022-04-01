package com.spider.oss.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * minio策略配置枚举
 *
 * @author SpiderMan
 * @version 1.0.0: com.spider.garbage.common.enumerate.PolicyType,v 0.1 2021/7/22 15:01 Exp $$
 */
@Getter
@AllArgsConstructor
public enum PolicyType {

    /**
     * 只读
     */
    READ("read", "只读"),

    /**
     * 只写
     */
    WRITE("write", "只写"),

    /**
     * 读写
     */
    READ_WRITE("read_write", "读写");

    /**
     * 类型
     */
    private final String type;
    /**
     * 描述
     */
    private final String policy;
}
