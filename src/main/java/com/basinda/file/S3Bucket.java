package com.basinda.file;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
public class S3Bucket {
    private String basinda;

    public String getBasinda() {
        return basinda;
    }

    public void setBasinda(String basinda) {
        this.basinda = basinda;
    }
}