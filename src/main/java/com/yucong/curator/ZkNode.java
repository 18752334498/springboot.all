package com.yucong.curator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ZkNode {

    String nodePath() default "";

    String defaultValue() default "";

    boolean isWather() default false;
}
