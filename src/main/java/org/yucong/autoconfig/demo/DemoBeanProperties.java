package org.yucong.autoconfig.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 属性配置类，获取application.properties里的属性并和实体类绑定
 *
 */
@ConfigurationProperties(prefix = "hero.message")
public class DemoBeanProperties {

	public String name;
	public int age;
	public int height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
