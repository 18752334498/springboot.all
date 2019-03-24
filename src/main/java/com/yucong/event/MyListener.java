package com.yucong.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.yucong.autoconfig.demo.DemoBean;

public class MyListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		ConfigurableApplicationContext context = event.getApplicationContext();
		DemoBean demoBean = context.getBean(DemoBean.class);
		System.out.println(demoBean);
		System.out.println("============== springboot启动完成  ===============");
	}

}
