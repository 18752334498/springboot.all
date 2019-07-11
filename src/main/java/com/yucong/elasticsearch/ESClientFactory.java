package com.yucong.elasticsearch;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

public class ESClientFactory {

    public static int connectTimeout = 1000;
    public static int socketTimeout = 30000;
    public static int connectionRequestTimeout = 500;
    public static int maxConnPerRoute = 10;
    public static int maxConnTotal = 30;

    private static HttpHost HTTP_HOST;
    private RestClientBuilder builder;
    private RestClient restClient;
    private RestHighLevelClient restHighLevelClient;

    private static ESClientFactory esClientSpringFactory = new ESClientFactory();

    private ESClientFactory() {}

    public static ESClientFactory build(HttpHost httpHost, Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHost;
        maxConnTotal = maxConnectNum;
        maxConnPerRoute = maxConnectPerRoute;
        return esClientSpringFactory;
    }

    public static ESClientFactory build(HttpHost httpHost, Integer connectTimeOut, Integer socketTimeOut, Integer connectionRequestTime,
            Integer maxConnectNum, Integer maxConnectPerRoute) {
        HTTP_HOST = httpHost;
        connectTimeout = connectTimeOut;
        socketTimeout = socketTimeOut;
        connectionRequestTimeout = connectionRequestTime;
        maxConnTotal = maxConnectNum;
        maxConnPerRoute = maxConnectPerRoute;
        return esClientSpringFactory;
    }

    public void init() {
        builder = RestClient.builder(HTTP_HOST);
        setConnectTimeOutConfig();
        setMutiConnectConfig();
        restClient = builder.build();
        restHighLevelClient = new RestHighLevelClient(builder);
        System.out.println("init factory");
    }

    // 配置连接时间延时
    public void setConnectTimeOutConfig() {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(connectTimeout);
            requestConfigBuilder.setSocketTimeout(socketTimeout);
            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
            return requestConfigBuilder;
        });
    }

    // 使用异步httpclient时设置并发连接数
    public void setMutiConnectConfig() {
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(maxConnTotal);
            httpClientBuilder.setMaxConnPerRoute(maxConnPerRoute);
            return httpClientBuilder;
        });
    }

    public RestClient getClient() {
        return restClient;
    }

    public RestHighLevelClient getRhlClient() {
        return restHighLevelClient;
    }

    public void close() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("close client");
    }
}
