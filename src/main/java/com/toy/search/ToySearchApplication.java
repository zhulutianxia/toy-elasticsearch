package com.toy.search;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.toy.search.dao")
@EnableScheduling
@EnableElasticsearchRepositories(basePackages = "com.toy.search.repository")
public class ToySearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToySearchApplication.class, args);
	}

}
