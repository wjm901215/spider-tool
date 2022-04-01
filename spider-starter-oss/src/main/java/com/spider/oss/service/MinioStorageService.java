package com.spider.oss.service;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.spider.core.common.exception.BusinessException;
import com.spider.core.common.utils.StringPool;
import com.spider.oss.config.CloudStorageConfig;
import com.spider.oss.enums.PolicyType;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * MinIO存储
 *
 * @author SpiderMan
 * @version 1.0.0: com.spider.garbage.common.cloud.MinioStorageService,v 0.1 2021/7/22 09:43 Exp $$
 */
@Slf4j
public class MinioStorageService extends CloudStorageService {

    private MinioClient minioClient;
    static final String REGION = "cn-south-1";

    public MinioStorageService(CloudStorageConfig config) {
        this.config = config;
        init();
    }

    private void init() {
        try {
            minioClient = new MinioClient(config.getMinioDomain(), config.getMinioAccessKey(), config.getMinioSecretKey());
        } catch (MinioException e) {
            log.error("Minio连接失败，请检查配置信息" + ExceptionUtil.stacktraceToString(e));
            throw new BusinessException("Minio连接失败，请检查配置信息", e);
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            // Make bucket if not exist.
            boolean isExist = minioClient.bucketExists(config.getMinioBucketName());
            if (!isExist) {
                // Make a new bucket called
                minioClient.makeBucket(config.getMinioBucketName(), REGION);
                minioClient.setBucketPolicy(config.getMinioBucketName(), getPolicyType(config.getMinioBucketName(), PolicyType.READ));
            }
            minioClient.putObject(config.getMinioBucketName(), path, inputStream, new PutObjectOptions(inputStream.available(), -1));
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException ex) {
            log.error("上传文件失败，请检查配置信息" + ExceptionUtil.stacktraceToString(ex));
            throw new BusinessException("上传文件失败，请检查配置信息", ex);
        }
        return fileLink(path);
    }

    /**
     * 获取附件上传路径
     *
     * @param path
     * @return
     */
    public String fileLink(String path) {
        return config.getMinioDomain().concat(StringPool.SLASH).concat(config.getMinioBucketName()).concat(StringPool.SLASH).concat(path);
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getMinioPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getMinioPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(byte[] data, String module, String suffix) {
        return upload(data, getPath(config.getMinioPrefix() + "/" + module, suffix));
    }


    /**
     * 获取存储桶策略
     *
     * @param bucketName 存储桶名称
     * @param policyType 策略枚举
     * @return String
     */
    public static String getPolicyType(String bucketName, PolicyType policyType) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("    \"Statement\": [\n");
        builder.append("        {\n");
        builder.append("            \"Action\": [\n");

        switch (policyType) {
            case WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            case READ_WRITE:
                builder.append("                \"s3:GetBucketLocation\",\n");
                builder.append("                \"s3:ListBucket\",\n");
                builder.append("                \"s3:ListBucketMultipartUploads\"\n");
                break;
            default:
                builder.append("                \"s3:GetBucketLocation\"\n");
                break;
        }

        builder.append("            ],\n");
        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("\"\n");
        builder.append("        },\n");
        if (PolicyType.READ.equals(policyType)) {
            builder.append("        {\n");
            builder.append("            \"Action\": [\n");
            builder.append("                \"s3:ListBucket\"\n");
            builder.append("            ],\n");
            builder.append("            \"Effect\": \"Deny\",\n");
            builder.append("            \"Principal\": \"*\",\n");
            builder.append("            \"Resource\": \"arn:aws:s3:::");
            builder.append(bucketName);
            builder.append("\"\n");
            builder.append("        },\n");

        }
        builder.append("        {\n");
        builder.append("            \"Action\": ");

        switch (policyType) {
            case WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            case READ_WRITE:
                builder.append("[\n");
                builder.append("                \"s3:AbortMultipartUpload\",\n");
                builder.append("                \"s3:DeleteObject\",\n");
                builder.append("                \"s3:GetObject\",\n");
                builder.append("                \"s3:ListMultipartUploadParts\",\n");
                builder.append("                \"s3:PutObject\"\n");
                builder.append("            ],\n");
                break;
            default:
                builder.append("\"s3:GetObject\",\n");
                break;
        }

        builder.append("            \"Effect\": \"Allow\",\n");
        builder.append("            \"Principal\": \"*\",\n");
        builder.append("            \"Resource\": \"arn:aws:s3:::");
        builder.append(bucketName);
        builder.append("/*\"\n");
        builder.append("        }\n");
        builder.append("    ],\n");
        builder.append("    \"Version\": \"2012-10-17\"\n");
        builder.append("}\n");
        return builder.toString();
    }
}
