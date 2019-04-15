package com.yucong.cloudvideo.sign;

import java.util.HashMap;
import java.util.Map;

public class TestSign {

    // 秘钥
    private static final String KEY = "e10adc3949ba59abbe56e057f20f883e";
    // 标识
    private static final String IDENTITY = "5b357915281c0fef984cd974bcd07f17";

    public static void main(String[] args) {
        Map<String, String> parameters = new HashMap<>();
        // HTTP 参数列表
        parameters.put("mobile", "13900001111");
        parameters.put("password", "123456");
        parameters.put("isTrai", "0");
        parameters.put("identity", IDENTITY);
        String sign = Sign.sign(KEY, parameters);
        System.out.println("sign: " + sign);
    }


}
