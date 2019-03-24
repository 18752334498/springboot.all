package com.yucong.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 此配置会让项目外的DemoBean失效，因为项目外的配置用了一个@ConditionalOnMissingBean(DemoBean.class)
 * 
 * @author Administrator
 *
 */
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
