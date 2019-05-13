package com.yucong.condition;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(ConditionalOnPropertyConfig.class)
public class ConditionalOnMissingBeanConfig {

	@Bean
	@ConditionalOnMissingBean({ User.class })
    public User fourUser() {
        System.out.println("================李四=================");
        return new User("44", "李四", "444");
	}

    @Bean
    @ConditionalOnBean({User.class})
    public User fiveUser() {
        System.out.println("================王五=================");
        return new User("55", "王五", "555");
    }

}
