package com.yucong.redis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        jedis.set("five", "hehe");

        System.out.println(jedis.get("five"));
        jedis.close();
    }

    private String read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("D:\\five.sql"));

        String str = "";
        while ((str = br.readLine()) != null) {
            if (str.contains("values")) {
                
            }
        }
        return "";
    }

}
