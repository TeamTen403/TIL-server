package com.teamten.til.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class ObjectStorageConfig {
	@Value("${ncp.credentials.access-key}")
	private String accessKey;

	@Value("${ncp.credentials.secret-key}")
	private String secretKey;

	@Value("${ncp.s3.region.static}")
	private String region;

	@Bean
	public AmazonS3 amazonS3Client() {
		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration("https://kr.object.ncloudstorage.com", region))
			.withCredentials(new AWSStaticCredentialsProvider(
				new BasicAWSCredentials(accessKey, secretKey)))
			.build();
	}
}

