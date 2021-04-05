/**
 * 
 */
package io.monitoring.annotation;

import io.monitoring.aop.service.test.ServiceCallable;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;; 

/**
 * alertNames -- cpu, db, oml, threadcount, heap, session
 * monitor -->  Do we need to monitor this function
 * action -->  list of action to be executed based on the alerts. This should be
 * proxy -->  Set the proxy method to be called instead of method being monitored
 * @author vipink
 *
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface MonitorAlert {
	String[] alertNames() default {};
	boolean monitor() default false;
	boolean store() default false; 
	boolean actions() default false;
	// proxy could be type of fallback .. controller , service, 
	Class proxy() default ServiceCallable.class;
}
