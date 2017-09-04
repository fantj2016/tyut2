package com.tyut.eureka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.tyut.eureka.core.mapper")
public class NoticeService {

	public static void main(String[] args) {
		SpringApplication.run(NoticeService.class, args);
	}
}