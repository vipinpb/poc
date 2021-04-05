/**
 * 
 */
package io.monitoring.aop.service;

import java.lang.reflect.Method;

/**
 * @author vipink
 *
 */
public class MethodMetaData {

	private Method method;
	private Object target;
	private Object[] args;
	/**
	 * 
	 */
	public MethodMetaData() {
		// TODO Auto-generated constructor stub
	}
	public MethodMetaData(Method method, Object target, Object[] args) {
		super();
		this.method = method;
		this.target = target;
		this.args = args;
	}
	public Method getMethod() {
		return method;
	}
	public Object getTarget() {
		return target;
	}
	public Object[] getArgs() {
		return args;
	}

}
