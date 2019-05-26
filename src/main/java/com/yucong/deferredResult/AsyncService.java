package com.yucong.deferredResult;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.yucong.util.User;

@Service
public class AsyncService {

	public DeferredResult<User> test1() {
		System.out.println("service start: " + Thread.currentThread().getName());
		ListeningExecutorService exec = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
		ListenableFuture<User> future = exec.submit(() -> {
			TimeUnit.SECONDS.sleep(5);
			System.out.println("execut: " + Thread.currentThread().getName());
			return new User(1, "Tom", 30);
		});

		DeferredResult<User> result = new DeferredResult<>();
		Futures.addCallback(future, new FutureCallback<User>() {
			@Override
			public void onSuccess(User user) {
				System.out.println("callback: " + Thread.currentThread().getName());
				result.setResult(user);
			}
			@Override
            public void onFailure(Throwable t) {
                result.setResult(new User(0, "failure", 0));
            }

		}, exec);

		System.out.println("service end: " + Thread.currentThread().getName());
		return result;
	}
}
