package com.yucong.deferredResult;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		WebMvcConfigurer.super.configureAsyncSupport(configurer);
	}

}
