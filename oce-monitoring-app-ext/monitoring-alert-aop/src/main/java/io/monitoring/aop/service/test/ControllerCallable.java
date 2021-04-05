package io.monitoring.aop.service.test;

import java.util.concurrent.Callable;

public class ControllerCallable implements Callable {

	public ControllerCallable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object call() throws Exception {
		System.out.println("Controller");
		return "Service Unavailable Please try later";
	}

}
