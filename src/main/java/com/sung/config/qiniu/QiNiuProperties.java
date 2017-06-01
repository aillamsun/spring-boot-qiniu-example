package com.sung.config.qiniu;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by sungang on 2017/4/11.
 */
@Configuration
@ConfigurationProperties(prefix = "qiniu")
@PropertySource("classpath:qiniu.properties")
public class QiNiuProperties {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String bucketUrl;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getBucketUrl() {
        return bucketUrl;
    }

    public void setBucketUrl(String bucketUrl) {
        this.bucketUrl = bucketUrl;
    }
}
