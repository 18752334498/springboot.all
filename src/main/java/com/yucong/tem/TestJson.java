package com.yucong.tem;

import com.alibaba.fastjson.JSON;

public class TestJson {

	public static void main(String[] args) {
		Address address = new Address();
		address.setId(444);
		address.setDetail("pukou");
		Dog dog = new Dog();
		dog.setId(333l);
		dog.setName("andemen");
		Person person = new Person();
		person.setId(2);
		person.setName("Tony");
		person.setAddress(address);
		person.setDog(dog);
		String jsonString = JSON.toJSONString(person);
		System.out.println(jsonString);

		String string = "{\"address\":{\"detail\":\"pukou\",\"id\":ff},\"dog\":{\"id\":333,\"name\":\"andemen\"},\"id\":2,\"name\":\"frfrfrf\"}";

		Person person2 = JSON.parseObject(string, Person.class);
		System.out.println(person2);

	}

}
