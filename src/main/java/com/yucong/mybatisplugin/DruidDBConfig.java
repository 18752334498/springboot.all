package com.yucong.mybatisplugin;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.yucong.mail.MailConfig;
import com.yucong.util.ZkUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration // 注释此注解，则取消Druid连接池，采用默认连接池（Spring-Boot-2.0.0-M1版本将默认的数据库连接池从tomcat改为了hikari）
public class DruidDBConfig {

    /**
     * <li>自定义druid连接池的原理，请参考 {@link MailConfig}</li>
     * 
     * <li>自定义druid连接池的写法，请参考org.springframework.boot.autoconfigure.jdbc.DataSourceConfiguration</li>
     * <li>DataSourceConfiguration中的方法createDataSource()，查看源码可知，只配置了driverClassName，url，username，password</li>
     */

    @Autowired
    private ZkUtil zkUtil;

    @Bean(initMethod = "init")
    public DruidProperty druidProperty() {
        return new DruidProperty(zkUtil);
    }

    @Bean
    public DruidDataSource druidDataSource() throws Exception {
		log.info("DruidDataSource start init！！！");
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(druidProperty().getUrl());
        datasource.setUsername(druidProperty().getUsername());
        datasource.setPassword(druidProperty().getPassword());
        datasource.setDriverClassName(druidProperty().getDriverClassName());

        datasource.setInitialSize(druidProperty().getInitialSize());
        datasource.setMaxActive(druidProperty().getMaxActive());
        datasource.setMinIdle(druidProperty().getMinIdle());
        datasource.setMaxWait(druidProperty().getMaxWait());
        datasource.setValidationQuery(druidProperty().getValidationQuery());
        datasource.setTestWhileIdle(druidProperty().isTestWhileIdle());
        datasource.setFilters(druidProperty().getFilters());
        datasource.setConnectionProperties(druidProperty().getConnectionProperties());
        log.info("DruidDataSource success init！！！");
        return datasource;
    }

    /**
     * <li>更多配置请参考，如果不想用application.prorperties配置属性而是将配置放在zk，就可以用此方法自定义</li>
     * <li>org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration</li>
     * <li>org.mybatis.spring.boot.autoconfigure.MybatisProperties</li>
     */
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSource());

        // 加载MyBatis配置文件
        // PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        // sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));

        // 配置mybatis的config文件
        // sqlSessionFactoryBean.setConfigLocation(resolver.getResource("mybatis-config.xml"));

        log.info("SqlSessionFactory success init！！！");
        return sqlSessionFactoryBean.getObject();
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
