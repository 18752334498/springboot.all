package com.yucong.mybatisplugin;


import org.apache.commons.lang3.StringUtils;

import com.yucong.util.ZkUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DruidProperty {

    private final String ZK = "/datasource/druid";
    private ZkUtil zkUtil;

    // 首先使用zk里面的值，zk没有使用下面的默认值，其他值查看 {@link DruidAbstractDataSource}
    // 参考博客:https://www.cnblogs.com/wuyun-blog/p/5679073.html
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private int initialSize = 1;
    private int maxActive = 10;
    private int minIdle = 1;
    private int maxWait = 10000;
    private String validationQuery = "SELECT 1";
    private boolean testWhileIdle = true;
    private String filters = "stat,wall";
    private String connectionProperties = "druid.stat.mergeSql=true;druid.stat.slowSqlMillis=1000";

    public DruidProperty(ZkUtil zkUtil) {
        this.zkUtil = zkUtil;
    }

    public void init() {
        log.info("开始从zk获取Druid连接属性");
        try {
            String url = zkUtil.findData(ZK + "/url");
            String username = zkUtil.findData(ZK + "/username");
            String password = zkUtil.findData(ZK + "/password");
            String driverClassName = zkUtil.findData(ZK + "/driverClassName");
            String initialSize = zkUtil.findData(ZK + "/initialSize");
            String maxActive = zkUtil.findData(ZK + "/maxActive");
            String minIdle = zkUtil.findData(ZK + "/minIdle");
            String maxWait = zkUtil.findData(ZK + "/maxWait");
            String validationQuery = zkUtil.findData(ZK + "/validationQuery");
            String testWhileIdle = zkUtil.findData(ZK + "/testWhileIdle");
            String filters = zkUtil.findData(ZK + "/filters");
            String connectionProperties = zkUtil.findData(ZK + "/connectionProperties");

            if (StringUtils.isEmpty(url) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(driverClassName)) {
                log.info("获取Druid连接属性失败");
            }

            this.url = url;
            this.username = username;
            this.password = password;
            this.driverClassName = driverClassName;

            if (StringUtils.isNotEmpty(initialSize)) {
                this.initialSize = Integer.valueOf(initialSize);
            }
            if (StringUtils.isNotEmpty(maxActive)) {
                this.maxActive = Integer.valueOf(maxActive);
            }
            if (StringUtils.isNotEmpty(minIdle)) {
                this.minIdle = Integer.valueOf(minIdle);
            }
            // 获取连接时最大等待时间 default:-1
            if (StringUtils.isNotEmpty(maxWait)) {
                this.maxWait = Integer.valueOf(maxWait);
            }
            // 用来检测连接是否有效的sql
            if (StringUtils.isNotEmpty(validationQuery)) {
                this.validationQuery = validationQuery;
            }
            // 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
            if (StringUtils.isNotEmpty(testWhileIdle)) {
                this.testWhileIdle = Boolean.parseBoolean(testWhileIdle);
            }
            //常用的插件有：监控统计用的filter:stat；日志用的filter:log4j；防御sql注入的filter:wall。如果报错：dbType not support : null, url null，则去掉wall属性
            if (StringUtils.isNotEmpty(filters)) {
                this.filters = filters;
            }
            // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
            if (StringUtils.isNotEmpty(connectionProperties)) {
                this.connectionProperties = connectionProperties;
            }

            log.info("Druid连接属性，url={}", url);
            log.info("Druid连接属性，username={}", username);
            log.info("Druid连接属性，password={}", password.substring(0, 2) + "******");
            log.info("Druid连接属性，driverClassName={}", driverClassName);
            log.info("Druid连接属性，initialSize={}", this.initialSize);
            log.info("Druid连接属性，maxActive={}", this.maxActive);
            log.info("Druid连接属性，minIdle={}", this.minIdle);
            log.info("Druid连接属性，maxWait={}", this.maxWait);
            log.info("Druid连接属性，validationQuery={}", this.validationQuery);
            log.info("Druid连接属性，testWhileIdle={}", this.testWhileIdle);
            log.info("Druid连接属性，filters={}", this.filters);
            log.info("Druid连接属性，connectionProperties={}", this.connectionProperties);
            log.info("获取Druid连接属性完毕！！！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public String getConnectionProperties() {
        return connectionProperties;
    }

    public void setConnectionProperties(String connectionProperties) {
        this.connectionProperties = connectionProperties;
    }
}
