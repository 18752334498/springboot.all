package com.yucong.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yucong.App;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestJpa {

	@Autowired
	private JpaService service;

	@Test
	public void test1() {
		service.insertBatch();
	}

}
