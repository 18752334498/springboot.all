package com.yucong.vm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.yucong.util.ZkUtil;

@Configuration
public class BeanFromVM {

    private static final Logger logger = LoggerFactory.getLogger(BeanFromVM.class);


    private static final String ZKADDRESS = "com.yucong.zkAddress";

    @Bean
    public String zkAddress() {
        return StringUtils.isEmpty(System.getProperty(ZKADDRESS)) ? System.getenv(ZKADDRESS) : System.getProperty(ZKADDRESS);
    }

    @Bean
    public ZkUtil zkUtil(String zkAddress) {
        ZkUtil zkUtil = new ZkUtil(zkUrl, "");
        logger.info("ZK客服端初始化成功，地址：{}", zkUrl);
        return zkUtil;
    }

}
