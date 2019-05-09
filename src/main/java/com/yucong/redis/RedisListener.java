package com.yucong.redis;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisListener implements ApplicationListener<ApplicationReadyEvent> {

    @SuppressWarnings("unchecked")
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        RedisTemplate<String, Object> redisTemplate = context.getBean("redisTemplate", RedisTemplate.class);

        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        forValue.set("name", "Tom");
        Object name = forValue.get("name");

        System.out.println("redis伪集群获取的值： " + name.toString());
    }

}
