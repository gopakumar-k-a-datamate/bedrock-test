package com.datamate.bedrock.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication
public class BedrockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BedrockApplication.class, args);
	}

}
