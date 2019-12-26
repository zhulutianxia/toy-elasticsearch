package com.toy.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.toy.search.dao")
@EnableScheduling
public class ToySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToySearchApplication.class, args);
	}

}
