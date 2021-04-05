/**
 * 
 */
package io.monitoring.service;

import io.monitoring.domain.MetricRule;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.RuleParser;

/**
 * @author vipink
 *
 */
public interface RuleService {

	Iterable<MetricRule> findAll() throws RuleException;
	
	Iterable<IRule> buildAllRules() throws RuleException;

	void create(MetricRule rule) throws RuleException;

	void remove(Long id) throws RuleException;
	
	Iterable<MetricRule> findByName(String ruleName) throws RuleException;

	RuleParser getRuleParser();
}
