package com.yucong.vm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.yucong.util.ZkUtil;

@Configuration // 避免测试报错
public class BeanFromVM {

    private static final String ZKADDRESS = "com.yucong.zkAddress";

    @Bean
    public String zkAddress() {
        return StringUtils.isEmpty(System.getProperty(ZKADDRESS)) ? System.getenv(ZKADDRESS) : System.getProperty(ZKADDRESS);
    }

    @Bean
    public ZkUtil zkUtil(String zkAddress) {
        System.out.println("ZK客服端初始化: " + zkAddress);
        ZkUtil zkUtil = new ZkUtil(zkAddress, "");
        return zkUtil;
    }

}
