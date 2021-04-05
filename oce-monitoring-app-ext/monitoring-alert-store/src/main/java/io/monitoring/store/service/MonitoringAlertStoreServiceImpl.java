/**
 * 
 */
package io.monitoring.store.service;

import io.monitoring.metrics.Alert;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author vipink
 *
 */
public class MonitoringAlertStoreServiceImpl implements MonitoringAlertStoreService {

	private static Map<String, Alert> monitoringAlerts = new ConcurrentHashMap<>();

	public Iterable<Alert> getAlerts() {
		return monitoringAlerts.values();
	}
	
	public Optional<Alert> findAlertByName(String alertName) {
		try {
			return Optional.ofNullable(monitoringAlerts.get(alertName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Optional<Alert> remove(String alertName) {
		try {
			Alert alert = monitoringAlerts.remove(alertName);
			return Optional.ofNullable(alert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void store(Alert alert) throws AlertException {
		try {
			monitoringAlerts.put(alert.getName(), alert);
		} catch (Exception e) {
			throw new AlertException("Error storing the active alert ", e);
		}
		
	}

}
