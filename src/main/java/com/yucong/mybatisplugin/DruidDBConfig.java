package com.yucong.mybatisplugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.yucong.util.ZkUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // 注释此注解，则取消Druid连接池，采用默认连接池（Spring-Boot-2.0.0-M1版本将默认的数据库连接池从tomcat改为了hikari）
public class DruidDBConfig {

    @Autowired
    private ZkUtil zkUtil;

    @Bean(initMethod = "init")
    public DruidProperty druidProperty() {
        return new DruidProperty(zkUtil);
    }

    @Bean
    public DruidDataSource dataSourceProperties(DruidProperty druidProperty) throws Exception {
        DruidDataSource datasource = new DruidDataSource();

        log.info("开始配置DruidDataSource");
        datasource.setUrl(druidProperty.getUrl());
        datasource.setUsername(druidProperty.getUsername());
        datasource.setPassword(druidProperty.getPassword());
        datasource.setDriverClassName(druidProperty.getDriverClassName());

        datasource.setInitialSize(druidProperty.getInitialSize());
        datasource.setMaxActive(druidProperty.getMaxActive());
        datasource.setMinIdle(druidProperty.getMinIdle());
        datasource.setMaxWait(druidProperty.getMaxWait());
        datasource.setValidationQuery(druidProperty.getValidationQuery());
        datasource.setTestWhileIdle(druidProperty.isTestWhileIdle());
        datasource.setFilters(druidProperty.getFilters());
        datasource.setConnectionProperties(druidProperty.getConnectionProperties());
        log.info("配置DruidDataSource完毕！！！");
        return datasource;
    }

    @Bean
    public ServletRegistrationBean<StatViewServlet> druidStatViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");

        // bean.addInitParameter("allow","127.0.0.1"); //设置ip白名单
        // bean.addInitParameter("deny","192.168.0.19");//设置ip黑名单，优先级高于白名单

        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "admin");
        // 是否可以重置数据
        bean.addInitParameter("resetEnable", "false");
        return bean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> druidStatFilter() {

        // 创建过滤器
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>(new WebStatFilter());

        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");

        // 忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }
}
