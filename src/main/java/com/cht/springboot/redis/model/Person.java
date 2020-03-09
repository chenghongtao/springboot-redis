package com.cht.springboot.redis.model;

import lombok.Data;

@Data
public class Person {
	private String name;
	private String sex;
	private int age;
	public Person(String name, String sex, int age) {
		super();
		this.name = name;
		this.sex = sex;
		this.age = age;
	}
	

	
}
