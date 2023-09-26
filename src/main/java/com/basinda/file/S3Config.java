package com.basinda.file;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.auth.credentials.*;
import org.springframework.context.annotation.Bean;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Bean
    public StaticCredentialsProvider awsCredentialsProvider(){
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(accessKey, secretKey);
        return StaticCredentialsProvider.create(awsCredentials);
    }

    @Bean
    public S3Client s3Client(){
        S3Client client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(awsCredentialsProvider())
                .build();
        return client;
    }
}