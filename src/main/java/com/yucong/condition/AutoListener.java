package com.yucong.condition;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class AutoListener implements ApplicationListener<ApplicationReadyEvent> {

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		ConfigurableApplicationContext context = event.getApplicationContext();
        try {
            Object threeUser = context.getBean("threeUser");
            if (threeUser != null)
                System.out.println("=====:" + threeUser.toString());
        } catch (Exception e) {
            // ignore
        }

        try {
            Object fourUser = context.getBean("fourUser");
            if (fourUser != null)
                System.out.println("=====:" + fourUser.toString());
        } catch (Exception e) {
            // ignore
        }

        try {
            Object fiveUser = context.getBean("fiveUser");
            if (fiveUser != null)
                System.out.println("=====:" + fiveUser.toString());
        } catch (Exception e) {
            // ignore
        }

	}

}
