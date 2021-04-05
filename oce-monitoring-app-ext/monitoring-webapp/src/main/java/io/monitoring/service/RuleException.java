/**
 * 
 */
package io.monitoring.service;

/**
 * @author vipink
 *
 */
public class RuleException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182768092606962910L;
	/**
	 * 
	 */
	public RuleException() {}

	/**
	 * @param message
	 */
	public RuleException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public RuleException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

}
