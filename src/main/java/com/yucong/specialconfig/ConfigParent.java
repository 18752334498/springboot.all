package com.yucong.specialconfig;

import org.springframework.context.annotation.Bean;


abstract class ConfigAnimal {

    static class CreateCat {
        @Bean("tom")
        public Cat createCat() {
            return new Cat("Tom");
        }
    }


    static class CreateDog {
        @Bean("jack")
        public Dog createdDog() {
            return new Dog("Jack");
        }
    }

}
