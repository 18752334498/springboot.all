package com.yucong.anno;

import java.lang.reflect.Field;

import org.springframework.util.StringUtils;

public class TestAnno {

    public static void judge(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object object = field.get(obj);
                if (StringUtils.isEmpty(object)) {
                    System.out.println(field.getName() + "=空值");
                    System.out.println(field.getDeclaringClass().getSimpleName() + "." + field.getName() + " cannot be empty!");
                } else {
                    System.out.println(field.getName() + "=" + object);
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Person person = new Person("1", "tom", "");
        TestAnno.judge(person);
    }

}
