package com.yucong.redis;

import redis.clients.jedis.Jedis;

public class RedisTest {

    public static void main(String[] args) {
        // 连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        jedis.set("five", "hehe");

        System.out.println(jedis.get("five"));
        jedis.close();
    }

}
