package com.yucong.anno;

import java.lang.reflect.Field;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String age;

    public void judge(Object obj) throws Exception {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(NotEmpty.class)) {
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
        person.judge(person);
    }

}
