package com.yucong.mail;

import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.yucong.util.ZkUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MailConfig {

	@Autowired
	private ZkUtil zkUtil;

	@Bean(initMethod = "init")
	@ConditionalOnMissingBean({ JavaMailSenderImpl.class })
	public MailProperty mailProperty() {
		log.info("开始配置自定义MailProperty");
		return new MailProperty(zkUtil);
	}

	/**
	 * <li>写法参照org.springframework.boot.autoconfigure.mail.MailSenderPropertiesConfiguration</li>
	 */
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
