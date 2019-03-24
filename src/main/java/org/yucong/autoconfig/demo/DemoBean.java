package org.yucong.autoconfig.demo;

/**
 * 项目外的bean
 *
 */
public class DemoBean {

	private String name;
	private int age;
	private int height;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return name + "\t" + age + "\t" + height;
	}
}
