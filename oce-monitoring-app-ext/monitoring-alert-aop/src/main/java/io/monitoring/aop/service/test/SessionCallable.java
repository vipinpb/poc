package io.monitoring.aop.service.test;

import java.util.concurrent.Callable;

public class SessionCallable implements Callable {

	public SessionCallable() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object call() throws Exception {
		System.out.println("Session Task");
		return null;
	}

}
