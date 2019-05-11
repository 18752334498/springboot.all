package com.yucong.vm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.yucong.util.ZkUtil;

@Configuration
public class BeanFromVM {

    private static final String ZKADDRESS = "com.yucong.zkAddress";

    @Bean
    public String zkAddress() {
        return StringUtils.isEmpty(System.getProperty(ZKADDRESS)) ? System.getenv(ZKADDRESS) : System.getProperty(ZKADDRESS);
    }

    @Bean
    public ZkUtil zkUtil(String zkAddress) {
        System.out.println("从vm获取zk信息，开始连接zk: " + zkAddress);
        ZkUtil zkUtil = new ZkUtil(zkAddress, "");
        return zkUtil;
    }

}
