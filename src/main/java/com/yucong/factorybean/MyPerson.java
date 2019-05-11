package com.yucong.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
class MyPerson implements FactoryBean<Person> {

    @Override
    public Person getObject() throws Exception {
        Person person = new Person();
        person.setName("Snow");
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
