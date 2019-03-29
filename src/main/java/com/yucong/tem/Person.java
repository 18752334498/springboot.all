package com.yucong.tem;

public class Person {

	private Integer id;
	private String name;
	private Address address;
	private Dog dog;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}
	@Override
	public String toString() {
		return id + "\t" + name + "\t" + address + "\t" + dog;
	}

}
