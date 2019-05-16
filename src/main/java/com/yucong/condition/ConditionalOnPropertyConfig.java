package com.yucong.condition;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@ConditionalOnProperty(prefix = "spring.test", name = "name")
//@ConditionalOnProperty(name = "spring.test.name")
//@ConditionalOnProperty(name = "spring.test.name", havingValue = "tom")
//@ConditionalOnProperty(name = "spring.test.name", havingValue = "jack", matchIfMissing = true)

@Configuration
public class ConditionalOnPropertyConfig {

	@Bean
	@ConditionalOnMissingBean({ User.class })
    public User threeUser() {
        return new User("33", "张三", "333");
	}

}

//组合：prefix = "spring.test", name = "name"
// 从application.properties文件，获取spring.test.name的属性（不在乎什么值），如果有值继续执行程序，否则终止该配置

//组合：name = "spring.test.name"
// 从application.properties文件，获取spring.test.name的属性（不在乎什么值），如果有值继续执行程序，否则终止该配置

//组合：name = "spring.test.name", havingValue = "tom"
// 从application.properties文件，获取spring.test.name的属性，如果值为"tom"，继续执行程序，否则终止该配置

//组合：name = "spring.test.name", havingValue = "tom", matchIfMissing = true（matchIfMissing default false）
// 从application.properties文件，获取到了spring.test.name的属性，如果值为"tom"，继续执行程序，否则终止该配置
// 从application.properties文件，获取到了spring.test.name的属性，如果值不为"tom"，终止该配置
// 从application.properties文件，获取不到spring.test.name的属性，不在乎什么值havingValue什么值，此时matchIfMissing=true，继续执行程序
