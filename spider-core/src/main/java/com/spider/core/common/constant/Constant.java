package com.spider.core.common.constant;

/**
 * 常量
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月15日 下午1:23:52
 */
public class Constant {

    public static final String CONFIG_PHOTO_URL = "PHOTO_URL";

    public static final String CHARSET = "UTF-8";
    public static final String SECURITY_MD5 = "MD5";
    public static final String SECURITY_DES = "DES";
    public static final String _DES_PWD = "xKRks/70hno=";
    public static final String UTF8 = "UTF-8";
    public static final Integer SUCCESS = 200;
    /**
     * 语言线程变量key
     */
    public static final String LANG = "lang";
    /**
     * 重发次数
     */
    public static final int RETRY_COUNT = 3;
    /**
     * 内容类型，附件上传
     */
    public static final String CONTENT_TYPE_MULTIPART = "multipart/form-data";


    /**
     * 系统参数配置
     */
    public enum Config {
        /**
         * 后台云存储配置
         */
        ADMIN_CLOUD_STORAGE_CONFIG_KEY,
    }


    /**
     * 订单业务码枚举类
     */
    public enum BusiTypeEnum {
        /**
         * 部门
         */
        DEPT("DT", "部门");

        public String key;
        public String value;

        BusiTypeEnum(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 全局通用返回码枚举类
     *
     * @author Spiderman
     * @version $Id: RspEnum,v 0.1 2017/12/4 15:41 Exp $$
     */
    public enum RspEnum {
        /**
         * 成功
         */
        SUCCESS(200, "成功"),
        /**
         * 签名验证失败
         */
        SIGN_FAIL(201, "签名验证失败"),
        /**
         * 参数不正确
         */
        PARAM_INCORRECT(202, "参数不正确"),
        /**
         * 无效的app_id
         */
        APP_ID_FAIL(203, "无效的app_id"),
        /**
         * 内容解析失败
         */
        DATA_FAIL(204, "DATA内容解密失败，请检查appkey"),
        /**
         * 请求消息过期
         */
        DATA_EXPIRED(205, "请求消息过期"),
        /**
         * 参数异常
         */
        PARAM_FAIL(421, "请检查data请求参数"),
        /**
         * 系统异常
         */
        ERROR(500, "系统异常");
        public int key;
        public String value;

        RspEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String get(int key) {
            RspEnum[] values = RspEnum.values();
            for (RspEnum object : values) {
                if (object.key == key) {
                    return object.value;
                }
            }
            return null;
        }
    }


    /**
     * FLAG操作
     */
    public enum FlagEnum {
        /**
         * 新增/修改/删除
         */
        ADD, UPDATE, DELETE;
    }

    /**
     * 菜单类型
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年11月15日 下午1:24:29
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 定时任务状态
     *
     * @author chenshun
     * @email sunlightcs@gmail.com
     * @date 2016年12月3日 上午12:07:22
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL(0),
        /**
         * 暂停
         */
        PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 文件存储
     */
    public enum CloudService {
        /**
         * 本地存储
         */
        LOCAL(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 七牛
         */
        QINIU(3),
        /**
         * MINIO
         */
        MINIO(4);

        private int value;

        CloudService(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 是否枚举
     */
    public enum YesOrNoEnum {
        /**
         * 是
         */
        YES(1, "是"),
        /**
         * 否
         */
        NO(0, "否");
        public int key;
        public String value;

        YesOrNoEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * 状态枚举
     */
    public enum StatusEnum {
        /**
         * 是
         */
        YES(1, "启用"),
        /**
         * 否
         */
        NO(0, "停用");
        public int key;
        public String value;

        StatusEnum(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

}
