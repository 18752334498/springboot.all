package com.yucong.mail;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.yucong.util.ZkUtil;

@Configuration
public class MailConfig {

    /**
     * <li>写法参照org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration</li>
     * <li>spring-boot-autoconfigure下的mail包中，有5个java类</li>
     * <li>在application.properties中8个mail属性，只有2个类用到了这8个属性</li>
     * <li>MailProperties 读取8个属性并注册</li>
     * <li>MailSenderPropertiesConfiguration 读取 MailProperties，并注册 JavaMailSenderImpl</li>
     * <li>自定义 MailProperty，类似于 MailProperties</li>
     * <li>自定义 MailConfig，类似于 MailSenderPropertiesConfiguration</li>
     * <li>最终完成 JavaMailSenderImpl 的注册</li>
     * 
     * <li>druid连接池的配置和这个原理相同</li>
     */

	@Autowired
	private ZkUtil zkUtil;

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean({ JavaMailSenderImpl.class })
	public MailProperty mailProperty() {
		return new MailProperty(zkUtil);
	}

	@Bean
	public JavaMailSenderImpl mailSender(MailProperty mailProperty) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		applyProperties(sender, mailProperty);
		return sender;
	}

	private void applyProperties(JavaMailSenderImpl sender, MailProperty mailProperty) {
		sender.setHost(mailProperty.getHost());
		if (mailProperty.getPort() != null) {
			sender.setPort(mailProperty.getPort());
		}
		sender.setUsername(mailProperty.getUsername());
		sender.setPassword(mailProperty.getPassword());
		sender.setProtocol(mailProperty.getProtocol());
		if (mailProperty.getDefaultEncoding() != null) {
			sender.setDefaultEncoding(mailProperty.getDefaultEncoding().name());
		}
		if (!mailProperty.getProperties().isEmpty()) {
			sender.setJavaMailProperties(asProperties(mailProperty.getProperties()));
		}
	}

	private Properties asProperties(Map<String, String> source) {
		Properties properties = new Properties();
		properties.putAll(source);
		return properties;
	}

}
