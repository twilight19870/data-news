package com.xt.message.center.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * 应用启动类
 * 
 * @author vivi207
 *
 */
@Slf4j
@EntityScan("com.xt.data.news.entity")
@EnableTransactionManagement
@ComponentScan("com.xt")
@SpringBootApplication
public class DataNewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataNewsApplication.class, args);
	}
}
