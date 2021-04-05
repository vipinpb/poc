/**
 * 
 */
package io.monitoring.rule.engine;

/**
 * @author vipink
 *
 */
public interface IRule {
	boolean shouldProcess(RuleContext context) ;
	void fire(RuleContext context);
}
