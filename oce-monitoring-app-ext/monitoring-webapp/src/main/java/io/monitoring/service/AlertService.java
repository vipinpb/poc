/**
 * 
 */
package io.monitoring.service;

import io.monitoring.domain.MetricAlert;
import oracle.monitoring.metrics.Alert;
import oracle.monitoring.store.service.AlertException;

/**
 * This service helps you create the alerts and also let you attach those to the rules.
 * 
 * When rule engine fire, it generates monitoring alerts 
 * @author vipink
 *
 */
public interface AlertService {

	Iterable<Alert> findByRule(String ruleName) ;
	
	Iterable<MetricAlert> findByRuleId(Long id) ;
	
	Iterable<MetricAlert> list();
	
	void add(Alert add) throws AlertException;

	void assignAlertToRule(Alert add, String ruleName) throws AlertException;

	void save(MetricAlert metricAlert);
}
