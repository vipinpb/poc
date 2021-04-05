/**
 * 
 */
package io.monitoring.rule.engine;

/**
 * @author vipink
 *
 */
public class RuleParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9072169688649268584L;

	/**
	 * 
	 */
	public RuleParserException() {
		
	}

	/**
	 * @param message
	 */
	public RuleParserException(String message) {
		
		super("Error parsing the rule "+ message);
	}

	/**
	 * @param cause
	 */
	public RuleParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public RuleParserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public RuleParserException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
