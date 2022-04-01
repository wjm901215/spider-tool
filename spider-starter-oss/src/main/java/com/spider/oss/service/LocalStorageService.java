package com.spider.oss.service;


import com.spider.core.common.exception.BusinessException;
import com.spider.oss.config.CloudStorageConfig;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地存储
 *
 * @author Spiderman
 * @version $Id: com.spider.garbage.modules.sys.cloud.LocalStorageService,v 0.1 2021/9/11 9:44 Exp $$
 */
public class LocalStorageService extends CloudStorageService {
    private static Logger log = LoggerFactory.getLogger(LocalStorageService.class);

    public LocalStorageService(CloudStorageConfig config) {
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        File targetFile = new File(path);
        try {
            FileUtils.copyInputStreamToFile(inputStream, targetFile);
        } catch (IOException e) {
            log.info("文件上传失败：", e);
            throw new BusinessException("上传文件失败，请检查配置信息", e);
        }
        //去除本地磁盘路径
        if (StringUtils.isNotBlank(config.getDirPath())) {
            path = path.substring(config.getDirPath().length(), path.length());
        }
        return config.getLocalDomain() + path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getDirPath(), suffix));
    }

    @Override
    public String uploadSuffix(byte[] data,String module, String suffix) {
        return upload(data, getPath(config.getDirPath()+"/"+module, suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getDirPath(), suffix));
    }
}
