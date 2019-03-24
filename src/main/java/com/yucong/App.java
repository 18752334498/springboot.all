package com.yucong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yucong.event.MyListener;

@SpringBootApplication
public class App {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(App.class);
		app.addListeners(new MyListener());
		app.run(args);
	}
}
