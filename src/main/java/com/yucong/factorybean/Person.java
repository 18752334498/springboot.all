package com.yucong.factorybean;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 6392471977113590745L;

    private String id;

    private String name;

    private String age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



}
