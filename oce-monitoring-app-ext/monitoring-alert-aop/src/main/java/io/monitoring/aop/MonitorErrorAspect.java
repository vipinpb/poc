/**
 * 
 */
package io.monitoring.aop;

import io.monitoring.annotation.MonitorAlert;
import io.monitoring.annotation.MonitorError;
import io.monitoring.aop.service.MethodMetaData;
import io.monitoring.aop.service.MonitorAlertService;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author vipink
 *
 */
@Aspect
@Component
public class MonitorErrorAspect {

	@Autowired
	MonitorAlertService monitorAlertService;
	/**
	 * 
	 */
	public MonitorErrorAspect() {}

	//@AfterThrowing("@annotation(oracle.monitoring.annotation.MonitorError)")
	@AfterThrowing(value = "@annotation(monitorError)", throwing = "ex")
	public void error(JoinPoint joinPoint, MonitorError monitorError, Exception ex) throws Throwable {

		final long start = System.currentTimeMillis();
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Object proceed = null;
		System.out.println(monitorError);
		MonitorError monitorAlertAnnotation = method.getAnnotation(MonitorError.class);
		

		System.out.println("Not sure what to do do here");
		ex.printStackTrace();
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
