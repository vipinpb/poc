/**
 * 
 */
package io.monitoring.service;

import java.util.Optional;

import io.monitoring.metrics.Alert;
import io.monitoring.store.service.AlertException;

/**
 * This provides the storage for the active alerts and also storage for
 * active Rule. 
 * @author vipink
 *
 */
public interface AlertStoreService {

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
	/**
	 * add the active rule which generated the alert
	 * @param ruleName
	 */
	void storeActiveRule(String ruleName) throws RuleException;
	/**
	 * Remove the Active Rule
	 * @param ruleName
	 * @throws RuleException
	 */
	void removeActiveRule(String ruleName) throws RuleException;
	/**
	 * List all the active rules
	 * @return
	 * @throws RuleException
	 */
	Iterable<String> listActiveRules() throws RuleException;
	
	
}
