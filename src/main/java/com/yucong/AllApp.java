package com.yucong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yucong.condition.AutoListener;
import com.yucong.redis.RedisListener;

@SpringBootApplication
public class AllApp {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(AllApp.class);
        app.addListeners(new AutoListener());
        app.addListeners(new RedisListener());
		app.run(args);
	}
}
