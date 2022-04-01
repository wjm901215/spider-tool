/**
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.spider.oss.config;


import lombok.Data;

import java.io.Serializable;

/**
 * 云存储配置信息
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 16:12
 */
@Data
public class CloudStorageConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 类型 1：本地存储  2：阿里云  3：七牛云 4：MinIO
     */
    private Integer type;
    /**
     * 阿里云绑定的域名
     */
    private String aliyunDomain;
    /**
     * 阿里云路径前缀
     */
    private String aliyunPrefix;
    /**
     * 阿里云EndPoint
     */
    private String aliyunEndPoint;
    /**
     * 阿里云AccessKeyId
     */
    private String aliyunAccessKeyId;
    /**
     * 阿里云AccessKeySecret
     */

    private String aliyunAccessKeySecret;
    /**
     * 阿里云BucketName
     */
    private String aliyunBucketName;

    /**
     * 本地存储地址
     */
    private String dirPath;

    /**
     * 本地访问域名
     */
    private String localDomain;

    /**
     *七牛绑定的域名
     */
    private String qiniuDomain;
    /**
     *七牛路径前缀
     */
    private String qiniuPrefix;
    /**
     *七牛ACCESS_KEY
     */
    private String qiniuAccessKey;
    /**
     *七牛SECRET_KEY
     */
    private String qiniuSecretKey;
    /**
     *七牛存储空间名
     */
    private String qiniuBucketName;
    /**
     * Minio 绑定钉域名
     */
    private String minioDomain;
    /**
     *MinIO路径前缀
     */
    private String minioPrefix;
    /**
     *MinIOACCESS_KEY
     */
    private String minioAccessKey;
    /**
     *MinIOSECRET_KEY
     */
    private String minioSecretKey;
    /**
     *MinIO存储空间名
     */
    private String minioBucketName;


}
