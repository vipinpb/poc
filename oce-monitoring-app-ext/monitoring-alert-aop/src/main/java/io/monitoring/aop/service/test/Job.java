/**
 * 
 */
package io.monitoring.aop.service.test;

import io.monitoring.annotation.MonitorAlert;

import org.springframework.stereotype.Component;

/**
 * @author vipink
 *
 */
@Component
public class Job {

	/**
	 * 
	 */
	public Job() {
		// TODO Auto-generated constructor stub
	}
	
	//@MonitorAlert(alertNames={"cpu"}, monitor = true, proxy =ControllerCallable.class )
	public void apple(String testing) {
		System.out.println(" Job is called " + testing);
	}
	
	@MonitorAlert(alertNames={"cpu"}, monitor = true, proxy = ServiceCallable.class )
	public void mango(String testing) {
		System.out.println(" Job is called " + testing);
	}

}
