package com.teamten.til;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.teamten.til.common.config.AppProperties;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TilApplication {
	public static void main(String[] args) {
		SpringApplication.run(TilApplication.class, args);
	}
}
