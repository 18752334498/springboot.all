package com.yucong.vm;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import com.yucong.factorybean.Person;
import com.yucong.util.ZkUtil;

public class ZKListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        ZkUtil zkUtil = context.getBean("zkUtil", ZkUtil.class);
        Person person = context.getBean("myPerson", Person.class);
        if (zkUtil == null) {
            System.out.println("ZKListener: 获取zk失败！！！");
            return;
        }
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


            System.out.println("=========person info========");
            System.out.println("myPerson: " + person.getName());
            System.out.println("=========person info========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
