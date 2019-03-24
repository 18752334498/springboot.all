package org.yucong.autoconfig.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目外的配置类
 *
 */
@Configuration
@EnableConfigurationProperties(DemoBeanProperties.class)
public class DemoBeanAutoConfiguration {

	@Autowired
	private DemoBeanProperties properties;

	// 这是项目外的配置，如果项目里面的MyDemoBean继承并且实现DemoBean，而且重新配置了
	// 此时@ConditionalOnMissingBean发挥作用，即demoBean()失效
	@Bean
	@ConditionalOnMissingBean(DemoBean.class)
	public DemoBean demoBean() {
		DemoBean demoBean = new DemoBean();
		demoBean.setName(properties.getName());
		demoBean.setAge(properties.getAge());
		demoBean.setHeight(properties.getHeight());
		return demoBean;
	}

}
