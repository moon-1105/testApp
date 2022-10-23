package com.mw1.testApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude= {DataSourceAutoConfiguration.class})
public class TestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestAppApplication.class, args);
	}

}
