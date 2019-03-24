自动配置原理：
项目外的配置类 DemoBeanAutoConfiguration，
在配置类有一些项目外的bean注入 DemoBean，
此时项目启动时是无法进行配置和注入的，
这时需要自动配置，
在项目的src/main/resources/目录下新建META-INF文件夹，
在META-INF里面新建spring.properties文件
org.springframework.boot.autoconfigure.EnableAutoConfiguration=org.yucong.autoconfig.demo.DemoBeanAutoConfiguration
项目启动时会自动配置 DemoBeanAutoConfiguration，
DemoBeanProperties时获取application.properties文件里的属性配置

@ConfigurationProperties									//注解在实体类上面
@EnableConfigurationProperties(DemoBeanProperties.class)	//注解在配置类上面
