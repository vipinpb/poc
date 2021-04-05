/**
 * 
 */
package io.monitoring.aop;

import io.monitoring.annotation.MonitorAlert;
import io.monitoring.aop.service.MethodMetaData;
import io.monitoring.aop.service.MonitorAlertService;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author vipink
 *
 */
@Aspect
@Component
public class MonitorAlertAspect {

	@Autowired
	MonitorAlertService monitorAlertService;
	/**
	 * 
	 */
	public MonitorAlertAspect() {}

	@Around("@annotation(oracle.monitoring.annotation.MonitorAlert)")
	public Object alert(ProceedingJoinPoint joinPoint) throws Throwable {

		final long start = System.currentTimeMillis();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Object proceed = null;
		MonitorAlert monitorAlertAnnotation = method
				.getAnnotation(MonitorAlert.class);
		

		if (monitorAlertAnnotation.monitor()) {
			// get the alerts to be monitored
			String alertsToBeMonitored[] = monitorAlertAnnotation.alertNames();
			for (String obj : alertsToBeMonitored) {

				if (monitorAlertService.isValidAlert(obj)) {
					Method calledMethod = resolveMethod(joinPoint);
					MethodMetaData metaData = new MethodMetaData(calledMethod,joinPoint.getTarget(),joinPoint.getArgs() );
					return monitorAlertService.process(joinPoint, metaData, monitorAlertAnnotation.proxy());
				}
			}
			final long executionTime = System.currentTimeMillis() - start;

			System.out.println(joinPoint.getSignature() + " executed in "
					+ executionTime + "ms");
			return proceed ;
		}

		return joinPoint.proceed();
	}
    
    protected Method resolveMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();

        Method method = getDeclaredMethodFor(targetClass, signature.getName(),
            signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        }
        return method;
    }
    
    private Method getDeclaredMethodFor(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethodFor(superClass, name, parameterTypes);
            }
        }
        return null;
    }
}
