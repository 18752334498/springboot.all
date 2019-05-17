package com.yucong.specialconfig;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class SpecialListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ConfigurableApplicationContext context = event.getApplicationContext();
        Cat tom = context.getBean("tom", Cat.class);
        Dog jack = context.getBean("jack", Dog.class);


        System.out.println(tom);
        System.out.println(jack);

	}

}
