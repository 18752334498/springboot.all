package com.yucong.specialconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class SpecialConfig {

    @Configuration
    @Import({ConfigAnimal.CreateCat.class, ConfigAnimal.CreateDog.class})
    protected static class BatchImportConfigToCreateBean {
    }
}
