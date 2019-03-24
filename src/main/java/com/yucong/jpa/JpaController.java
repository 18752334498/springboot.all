package com.yucong.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("jap")
public class JpaController {

	@Autowired
	private JpaService service;

	@RequestMapping("test1")
	public String test1() {
		service.insertBatch();
		return "success";
	}
}
