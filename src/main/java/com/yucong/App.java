package com.yucong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yucong.event.MyListener;
import com.yucong.redis.RedisListener;
import com.yucong.vm.ZKListener;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.addListeners(new MyListener());
        app.addListeners(new ZKListener());
        app.addListeners(new RedisListener());
		app.run(args);
	}
}
