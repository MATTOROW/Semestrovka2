package ru.itis.semesterwork.second.config;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.SetBucketPolicyArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    @Bean
    public ApplicationRunner initializeMinioBucket(MinioClient minioClient) {
        return args -> {
            try {
                if (!minioClient.bucketExists(BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build())) {
                    minioClient.makeBucket(MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());

                    // Настройка политик (пример)
                    minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                            .bucket(bucketName)
                            .config(publicBucketPolicy(bucketName))
                            .build());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize MinIO bucket", e);
            }
        };
    }


    private String publicBucketPolicy(String bucketName) {
        return """
        {
            "Version": "2012-10-17",
            "Statement": [
                {
                    "Effect": "Allow",
                    "Principal": "*",
                    "Action": ["s3:GetObject"],
                    "Resource": ["arn:aws:s3:::%s/*"]
                }
            ]
        }
        """.formatted(bucketName);
    }
}
