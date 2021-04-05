/**
 * 
 */
package io.monitoring.aop.service;

import java.util.concurrent.Callable;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author vipink
 *
 */
@Service
public class MonitorAlertService {

	@Autowired
	RegistryService registryService;
	
	/**
	 * 
	 */
	public MonitorAlertService() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean isValidAlert(String alertNames) {
		// call the alertStore and find the alerts
		if ("cpu".equalsIgnoreCase(alertNames)) {
			return true;
		}
		return false;
	}
	
	private void store(MethodMetaData metaData) {
		registryService.add(metaData);
	}
	
	public Object process(
			ProceedingJoinPoint joinPoint, 
			MethodMetaData metaData,
			Class proxyCallable) throws Throwable {
		
		registryService.add(metaData);
		if (proxyCallable != null) {
			Callable service = (Callable) proxyCallable.newInstance();
			System.out.println(service);
			return service.call();
		} else {
			return joinPoint.proceed();
		}
	}

}
