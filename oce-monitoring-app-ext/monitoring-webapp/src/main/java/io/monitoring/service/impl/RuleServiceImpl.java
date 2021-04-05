/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.common.Util;
import io.monitoring.domain.MetricRule;
import io.monitoring.repository.MetricRuleRepository;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.RuleParser;
import io.monitoring.service.RuleException;
import io.monitoring.service.RuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author vipink
 *
 */
@Service
public class RuleServiceImpl implements RuleService {

	@Autowired
	MetricRuleRepository metricRuleRepository;
	
	@Autowired
	private RuleParser ruleParser; 
	/**
	 * 
	 */
	public RuleServiceImpl() {}

	/* (non-Javadoc)
	 * @see oracle.monitoring.service.RuleService#findAll()
	 */
	@Override
	public Iterable<MetricRule> findAll() throws RuleException {
		return metricRuleRepository.findAll();
	}
	
	public Iterable<MetricRule> findByName(String ruleName) throws RuleException {
		return metricRuleRepository.findByName(ruleName);
	}

	/* (non-Javadoc)
	 * @see oracle.monitoring.service.RuleService#create(oracle.monitoring.rule.engine.Rule)
	 */
	@Override
	public void create(MetricRule rule) throws RuleException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see oracle.monitoring.service.RuleService#remove(java.lang.String)
	 */
	@Override
	public void remove(Long id) throws RuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public Iterable<IRule> buildAllRules() throws RuleException {
		Iterable<MetricRule> metricRules = metricRuleRepository.findAll();
		return Util.buildRules(metricRules);
	}

	@Override
	public RuleParser getRuleParser() {
		return ruleParser;
	}
	
	

}
