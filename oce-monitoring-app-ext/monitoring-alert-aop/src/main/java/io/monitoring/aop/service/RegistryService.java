/**
 * 
 */
package io.monitoring.aop.service;

import io.monitoring.aop.service.test.ControllerCallable;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Service;

/**
 * @author vipink
 *
 */
@Service
public class RegistryService {

	private static Map<String,Callable> actionRegistry = new HashMap<>();
	private static Queue<MethodMetaData> methodsQueue = new LinkedBlockingQueue<>();
	
	static {
		actionRegistry.put("cpu",new ControllerCallable());
	}
	/**
	 * 
	 */
	public RegistryService() {
		// TODO Auto-generated constructor stub
	}

	public void add(MethodMetaData metaData) {
		methodsQueue.add(metaData);
	}
	
	public MethodMetaData poll() {
		return methodsQueue.poll();
	}
}
