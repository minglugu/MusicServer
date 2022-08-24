package com.example.onlinemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 加了个过滤掉默认的配置，只是用了里面的BCryptPasswordEncoder这个类
@SpringBootApplication(exclude
		={org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class OnlinemusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlinemusicApplication.class, args);
	}

}
