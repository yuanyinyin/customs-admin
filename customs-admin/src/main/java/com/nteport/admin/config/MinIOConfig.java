package com.nteport.admin.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "minio")
public class MinIOConfig {
    /**
     * URL
     */
    private String url;

    /**
     * access-key
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucket name
     */
    private String bucketName;

    /**
     * 初始化 MinIO Client
     *
     * @return
     */
    @Bean
    public MinioClient initMinioClient() {
        return MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }
}
