package com.yucong.autoconfig;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class AutoConfigListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        MyDemoBean bean = context.getBean(MyDemoBean.class);
        System.out.println(bean.getName() + "\t" + bean.getAge() + "\t" + bean.getHeight());

    }

}
