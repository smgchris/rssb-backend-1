package com.rssb.backend1.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BucketName {
    BUCKET_NAME("rssb-spring-bucket");
    private final String bucketName;
}
