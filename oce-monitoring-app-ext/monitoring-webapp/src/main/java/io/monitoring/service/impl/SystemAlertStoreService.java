/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.service.AlertStoreService;
import io.monitoring.service.RuleException;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import oracle.monitoring.metrics.Alert;
import oracle.monitoring.store.service.AlertException;
import oracle.monitoring.store.service.MonitoringAlertStoreService;

/**
 * @author vipink
 *
 */

public class SystemAlertStoreService implements AlertStoreService {

	@Autowired
	MonitoringAlertStoreService monitoringAlertStoreService;
	
	private static Map<String, String> activeRules = new ConcurrentHashMap<>();
	/**
	 * 
	 */
	public SystemAlertStoreService() {}

	public Iterable<Alert> getAlerts() {
		return monitoringAlertStoreService.getAlerts();
	}
	
	public Optional<Alert> findAlertByName(String alertName) {
		try {
			return monitoringAlertStoreService.findAlertByName(alertName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Alert> remove(String alertName) {
		try {
			return monitoringAlertStoreService.remove(alertName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void store(Alert alert) throws AlertException {
		try {
			monitoringAlertStoreService.store(alert);
		} catch (Exception e) {
			throw new AlertException("Error storing the active alert ", e);
		}
		
	}

	@Override
	public void storeActiveRule(String ruleName) throws RuleException {
		try {
			activeRules.put(ruleName, ruleName);
		} catch ( Exception ex) {
			throw new RuleException("Error storing the active rule ", ex);
		}
	}

	@Override
	public void removeActiveRule(String ruleName) throws RuleException {
		try {
			activeRules.remove(ruleName);
		} catch ( Exception ex) {
			throw new RuleException("Error removing the active rule ", ex);
		}
	}

	@Override
	public Iterable<String> listActiveRules() throws RuleException {
		Iterable<String> iterable = (Iterable<String>)activeRules.keySet();
		return iterable;
	}
}
