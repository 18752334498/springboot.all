package com.yucong.jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JpaService {

	@Autowired
	private MyRepository repository;

	public void insertBatch() {

		List<User> list = new ArrayList<>();
		list.add(new User("Tom", 22));
		list.add(new User("Jack", 33));
		list.add(new User("Rose", 44));
		list.add(new User("Ann", 12));
		list.add(new User("Tony", 18));
		List<User> all = repository.saveAll(list);
		System.out.println(all);
	}
}
