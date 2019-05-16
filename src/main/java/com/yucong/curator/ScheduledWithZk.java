package com.yucong.curator;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yucong.util.ZkUtil;

@Component
public class ScheduledWithZk {

    @Autowired
    private ZkUtil zkUtil;

    @PostConstruct
    public void task() {
        zkUtil.LoopExecuteTask("/test/schedule", new ZkUtil.CallBack() {
            @Override
            public void process() {
                System.out.println(Thread.currentThread().getName() + ":  scheduled task . . . ");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
