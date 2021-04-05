package io.monitoring.manager;

import io.monitoring.domain.MetricRule;
import io.monitoring.metrics.Status;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.RuleEngine;
import io.monitoring.rule.engine.RuleParser;
import io.monitoring.service.AlertService;
import io.monitoring.service.AlertStoreService;
import io.monitoring.service.RuleException;
import io.monitoring.service.RuleService;

import java.util.Optional;

import oracle.monitoring.metrics.Alert;

public interface RulesManager {

	
	/**
	 * 
	 * @return
	 * @throws RuleException
	 */
	public Iterable<IRule> getRules() throws RuleException ; 
	
	public Optional<Alert> evaluate(String rule) throws RuleException;
	
	public Iterable<Alert> evaluateAll() throws RuleException;
	
	public Optional<Alert> evaluate(IRule rule) throws RuleException;
	
	public RuleParser getRuleParser();
	
	public Optional<MetricRule> createAndEvaluate(MetricRule metricRule)
			throws RuleException;
	
	public RuleEngine getRuleEngine();
	public RuleService getRuleService();
	public AlertService getAlertService();
	public AlertStoreService getAlertStoreService();
	
	public Optional<Status> startRuleEngine();

	public Optional<Status> stopRuleEngine();

}
