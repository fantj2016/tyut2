package com.tyut.eureka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@MapperScan("com.tyut.eureka.core.mapper")
public class HelpstudyService {

	public static void main(String[] args) {
		SpringApplication.run(HelpstudyService.class, args);
	}
}
