package com.yucong.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfiguration {

	@Bean
	public MyDemoBean myDemoBean() {
		MyDemoBean myDemoBean = new MyDemoBean();
		myDemoBean.setName("superwoman");
		myDemoBean.setAge(10);
		myDemoBean.setHeight(110);
		return myDemoBean;
	}
}
