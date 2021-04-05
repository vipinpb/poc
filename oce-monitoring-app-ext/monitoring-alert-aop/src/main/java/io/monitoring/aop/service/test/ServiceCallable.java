package io.monitoring.aop.service.test;

import java.util.concurrent.Callable;

public class ServiceCallable implements Callable{

	public ServiceCallable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object call() throws Exception {
		System.out.println(" Default Called ");
		return null;
	}

}
