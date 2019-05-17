package com.yucong.condition;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

public class ConditionListener implements ApplicationListener<ApplicationReadyEvent> {

    /**
     * 
     * <li>自定义两个@Configuration配置类 A 和 B ，默认可能先加载 A，再加载 B</li>
     * <li>现在要先加载 B，再加载 A ，可以在 A 类上用@AutoConfigureAfter(B.class)注解，前提是 B 是自动配置类</li>
     * <li>META-INF\spring.factories文件中EnableAutoConfiguration</li>
     * 
     */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
        ConfigurableApplicationContext context = event.getApplicationContext();
        try {
            Object threeUser = context.getBean("threeUser");
            if (threeUser != null)
                System.out.println("=====:" + threeUser.toString());
        } catch (Exception e) {
            // ignore
        }

        try {
            Object fourUser = context.getBean("fourUser");
            if (fourUser != null)
                System.out.println("=====:" + fourUser.toString());
        } catch (Exception e) {
            // ignore
        }

        try {
            Object fiveUser = context.getBean("fiveUser");
            if (fiveUser != null)
                System.out.println("=====:" + fiveUser.toString());
        } catch (Exception e) {
            // ignore
        }

	}

}
