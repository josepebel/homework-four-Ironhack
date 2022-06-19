package com.ironhack.operativeproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OperativeProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(OperativeProxyApplication.class, args);
	}

}
