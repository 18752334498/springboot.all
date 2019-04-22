package com.yucong.vm;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.yucong.util.ZkUtil;

public class ZKListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ZkUtil zkUtil = context.getBean("zkUtil", ZkUtil.class);
        try {
            String url = zkUtil.findData("/royasoft/vwt/cloudvideo/url");
            String identity = zkUtil.findData("/royasoft/vwt/cloudvideo/identity");
            String key = zkUtil.findData("/royasoft/vwt/cloudvideo/key");
            String orgCode = zkUtil.findData("/royasoft/vwt/cloudvideo/orgCode");

            System.out.println("=========zk info========");
            System.out.println("url: " + url);
            System.out.println("identity: " + identity);
            System.out.println("key: " + key);
            System.out.println("orgCode: " + orgCode);
            System.out.println("=========zk info========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
