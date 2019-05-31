package com.yucong.beanpostprocess;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof DruidDataSource) {
			DruidDataSource source = (DruidDataSource) bean;
			log.info("DruidDataSource: " + source.getUrl());
			log.info("DruidDataSource: " + source.getUsername());
		}
		if (bean instanceof DataSourceProperties) {
			log.info("DataSourceProperties: " + JSON.toJSONString(bean));
			log.info("DataSourceProperties: " + beanName);
		}
		return bean;
	}

}
