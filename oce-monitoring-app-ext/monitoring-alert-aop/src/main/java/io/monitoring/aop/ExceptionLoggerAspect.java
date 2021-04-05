/**
 * 
 */
package io.monitoring.aop;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

/**
 * @author vipink
 *
 */
@Configuration
@Aspect
public class ExceptionLoggerAspect {

	/**
	 * 
	 */
	public ExceptionLoggerAspect() {}

    @AfterThrowing(pointcut = "execution(* oracle.monitoring.aop.*.*.*(..))", throwing = "ex")
    public void logError(Exception ex) {
        ex.printStackTrace();
    }
}
