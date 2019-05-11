package com.yucong.redis;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yucong.util.ZkUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RedisConfig {

    private String ZK_ADDRESS = "/redis";

    @Autowired
    private ZkUtil zkUtil;

    /**
     * 配置( JedisPoolConfig , PoolConfig ) extends GenericObjectPoolConfig
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() throws Exception {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        // 最大连接数
        String maxTotal = zkUtil.findData(ZK_ADDRESS + "/maxTotal");
        poolConfig.setMaxTotal(Integer.parseInt(maxTotal));

        // 最大空闲连接数
        String maxIdle = zkUtil.findData(ZK_ADDRESS + "/maxIdle");
        poolConfig.setMaxIdle(Integer.parseInt(maxIdle));

        // 最小空闲连接数
        String minIdle = zkUtil.findData(ZK_ADDRESS + "/minIdle");
        poolConfig.setMinIdle(Integer.parseInt(minIdle));

        // 最大等待时间
        String maxWaitMillis = zkUtil.findData(ZK_ADDRESS + "/maxWaitMillis");
        poolConfig.setMaxWaitMillis(Long.parseLong(maxWaitMillis));

        return poolConfig;
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory(GenericObjectPoolConfig poolConfig) throws Exception {
        log.info("Redis 初始化中...");
        // 判断是单机还是集群
        RedisConnectionFactory factory = null;
        String address = zkUtil.findData(ZK_ADDRESS + "/address");
        String[] serverArray = address.split(",");
        if (serverArray.length > 1) {
            // 集群
            log.info("Redis 集群配置中...");
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
            Set<RedisNode> nodes = new HashSet<>();
            for (String ipPort : serverArray) {
                String[] ipAndPort = ipPort.split(":");
                nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            }
            // String password = zkUtil.findData(ZK_ADDRESS + "/password");
            // if (StringUtils.isNotBlank(password)) {
            // redisClusterConfiguration.setPassword(RedisPassword.of(password));
            // }
            redisClusterConfiguration.setClusterNodes(nodes);
            redisClusterConfiguration.setMaxRedirects(3);

            LettuceClientConfiguration lettuceConfig =
                    LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(10 * 1000)).poolConfig(poolConfig).build();

            factory = new LettuceConnectionFactory(redisClusterConfiguration, lettuceConfig);
        } else {
            // 单机
            log.info("Redis 单机配置中...");
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            // 选择dbindex
            // redisStandaloneConfiguration.setDatabase(database);
            String[] addressAndPort = serverArray[0].split(":");
            redisStandaloneConfiguration.setHostName(addressAndPort[0]);
            redisStandaloneConfiguration.setPort(Integer.parseInt(addressAndPort[1]));
            // String password = zkUtil.findData(ZK_ADDRESS + "/password");
            // if (StringUtils.isNotBlank(password)) {
            // redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
            // }
            LettuceClientConfiguration clientConfig =
                    LettucePoolingClientConfiguration.builder().commandTimeout(Duration.ofMillis(10 * 1000)).poolConfig(poolConfig).build();

            factory = new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);
        }
        return factory;
    }

    /**
     * <li>实例化 RedisTemplate 对象</li>
     * <li>RedisConnectionFactory(JedisConnectionFactory,LettuceConnectionFactory)</li>
     * <li>Lettuce和Jedis的都是连接RedisServer的客户端程序，Jedis在实现上是直连redisServer，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接</li>
     * <li>Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全</li>
     * <li>满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例</li>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        log.info("Redis初始化成功");
        return redisTemplate;
    }
}
