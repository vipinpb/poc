package io.monitoring.service.impl;

import io.monitoring.common.Util;
import io.monitoring.domain.MetricAlert;
import io.monitoring.domain.MetricRule;
import io.monitoring.repository.MetricAlertRepository;
import io.monitoring.repository.MetricRuleRepository;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.TSDBRule;
import io.monitoring.service.AlertService;
import io.monitoring.service.AlertStoreService;
import io.monitoring.service.NotAlarmFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.monitoring.metrics.Alert;
import io.monitoring.store.service.AlertException;

@Service
public class AlertServiceImpl implements AlertService { 


	public AlertServiceImpl() {}
	
	@Autowired
	AlertStoreService alertStoreService;
	
	@Autowired
	MetricAlertRepository metricAlertRepository;
	
	@Autowired
	MetricRuleRepository metricRuleRepository;

	@Override
	public Iterable<Alert> findByRule(String ruleName)  {
		List<Alert> alerts = new ArrayList<>();
		Iterable<MetricRule> metricRules = metricRuleRepository.findByName(ruleName);
		Iterable<IRule> rules= Util.buildRules(metricRules);
		for (MetricRule r : metricRules) {
			Iterable<MetricAlert> metricAlerts = metricAlertRepository.findByRuleId(r.getId());
			for (MetricAlert a : metricAlerts) {
				Alert alert = new Alert(a.getName(),r.getExp(), 1);
				alert.setValue("20");
				alerts.add(alert);
			}
		}
		return alerts;
	}
	
	@Override
	public Iterable<MetricAlert>  findByRuleId(Long id) {
		return null;
	}

	@Override
	public Iterable<MetricAlert> list(){
		return metricAlertRepository.findAll();
	}
	
	public Iterable<Alert> listActiveAlert() throws NotAlarmFoundException {
		return alertStoreService.getAlerts();
	}

	@Override
	public void add(Alert alert) throws AlertException {
		try {
			alertStoreService.store(alert);		
		} catch (Exception ex) {
			throw new AlertException("Error saving alert") ; 
		}
	}
	
	@Override
	public void assignAlertToRule(Alert add, String ruleName) throws AlertException {
		
	}

	@Override
	public void save(MetricAlert metricAlert) {
		metricAlertRepository.save(metricAlert);
	}
}
