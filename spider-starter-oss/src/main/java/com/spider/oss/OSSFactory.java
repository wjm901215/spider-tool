
package com.spider.oss;


import com.spider.core.common.constant.Constant;
import com.spider.oss.config.CloudStorageConfig;
import com.spider.oss.service.*;

/**
 * 文件上传Factory
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 10:18
 */
public final class OSSFactory {

    public static CloudStorageService build(CloudStorageConfig config) {
        //获取云存储配置信息
        if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.MINIO.getValue()) {
            return new MinioStorageService(config);
        } else if (config.getType() == Constant.CloudService.LOCAL.getValue()) {
            return new LocalStorageService(config);
        } else if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        }
        return null;
    }

    public static String getDomainPath(CloudStorageConfig config) {
        //获取云存储配置信息
        if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return config.getAliyunDomain();
        } else if (config.getType() == Constant.CloudService.LOCAL.getValue()) {
            return config.getLocalDomain();
        } else if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return config.getQiniuDomain();
        }
        return null;
    }

    public static String getDirPath(CloudStorageConfig config) {
        //获取云存储配置信息
        if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return config.getAliyunPrefix();
        } else if (config.getType() == Constant.CloudService.LOCAL.getValue()) {
            return config.getDirPath();
        } else if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return config.getQiniuPrefix();
        }
        return null;
    }

}
