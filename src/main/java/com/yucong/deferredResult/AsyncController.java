package com.yucong.deferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.yucong.util.User;

@RestController
@RequestMapping("async")
public class AsyncController {

	@Autowired
	private AsyncService asyncService;

	@RequestMapping("test1")
	public DeferredResult<User> test1(){
		System.out.println("controller: " + Thread.currentThread().getName());
		return asyncService.test1();
	}
	
	@RequestMapping("test2")
	public Callable<User> test2() {
		System.out.println("controller start: " + Thread.currentThread().getName());
		Callable<User> callable = new Callable<User>() {
			@Override
			public User call() throws Exception {
				TimeUnit.SECONDS.sleep(5);
				System.out.println("callable: " + Thread.currentThread().getName());
				return new User(1, "Jack", 23);
			}
		};
		System.out.println("controller end: " + Thread.currentThread().getName());
		return callable;
	}

}
