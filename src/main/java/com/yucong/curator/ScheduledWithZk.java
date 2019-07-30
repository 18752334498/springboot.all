package com.yucong.curator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yucong.util.ZkUtil;

@Component
public class ScheduledWithZk {

    @Autowired
    private ZkUtil zkUtil;

    @Autowired
    private Person person;

    @PostConstruct
    public void task() {
        zkUtil.LoopExecuteTask("/test/schedule", new ZkUtil.CallBack() {
            @Override
            public void process() {
                System.out.println(Thread.currentThread().getName() + ":  scheduled task . . . ");
                try {
                    Thread.sleep(10000);
                    System.out.println(person);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
