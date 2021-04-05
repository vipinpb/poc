/**
 * 
 */
package io.monitoring.rule.engine;

/**
 * @author vipink
 *
 */
public interface RuleParser {
	public Condition parse(String expression) throws RuleParserException;
}
