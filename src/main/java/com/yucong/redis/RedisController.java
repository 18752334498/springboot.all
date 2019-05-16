package com.yucong.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

// @RestController
@RequestMapping("redis")
public class RedisController {

    private final static String SUCCESS = "success";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @RequestMapping("string")
    public String getString() {
        ValueOperations<String, Object> forValue = redisTemplate.opsForValue();
        User user = new User().setName("Tom").setAge(34).setEmail("www.qq.com");
        List<String> list = new ArrayList<>(Arrays.asList("aa,bb,cc,dd".split(",")));

        forValue.set("value1", "hello world");
        forValue.set("value2", user);
        forValue.set("value3", list);
        forValue.set("value4", new HashSet<>(list));
        forValue.set("value5", JSONObject.parseObject(JSON.toJSONString(user), HashMap.class));
        forValue.set("value6", JSON.toJSONString(user));


        Object object1 = forValue.get("value1");
        Object object2 = forValue.get("value2");
        Object object3 = forValue.get("value3");
        Object object4 = forValue.get("value4");
        Object object5 = forValue.get("value5");
        Object object6 = forValue.get("value6");

        System.out.println(object1);
        System.out.println(object2);
        System.out.println(object3);
        System.out.println(object4);
        System.out.println(object5);
        System.out.println(object6);

        return SUCCESS;
    }

    @RequestMapping("list")
    public String getList() {
        return SUCCESS;
    }

    @RequestMapping("hash")
    public String getHash() {
        return SUCCESS;
    }

    @RequestMapping("set")
    public String getSet() {
        return SUCCESS;
    }

    @RequestMapping("bean")
    public String getBean() {
        return SUCCESS;
    }

    @RequestMapping("json")
    public String getJson() {
        return SUCCESS;
    }

}
