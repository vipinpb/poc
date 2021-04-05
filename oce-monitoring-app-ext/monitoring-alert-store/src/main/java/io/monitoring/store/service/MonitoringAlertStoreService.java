/**
 * 
 */
package io.monitoring.store.service;

import io.monitoring.metrics.Alert;

import java.util.Optional;

/**
 * This provides the storage for the active alerts and also storage for
 * active Rule. 
 * @author vipink
 *
 */
public interface MonitoringAlertStoreService {

	/**
	 * Find the alert by the name of alert
	 * @param alertName
	 * @return
	 */
	Optional<Alert> findAlertByName(String alertName) ;
	/**
	 * Return the list of system generated alert
	 * @return
	 */
	Iterable<Alert> getAlerts();
	/**
	 * Store the active alert
	 * @param alert
	 * @throws AlertException
	 */
	void store(Alert alert) throws AlertException;
	/**
	 * Remove the active alert 
	 * @param alertName
	 * @return
	 */
	Optional<Alert> remove(String alertName);
	
}
