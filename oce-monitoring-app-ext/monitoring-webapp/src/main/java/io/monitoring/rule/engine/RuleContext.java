/**
 * 
 */
package io.monitoring.rule.engine;

import io.monitoring.manager.MetricsManager;
import io.monitoring.manager.RulesManager;
import io.monitoring.metrics.Row;
import io.monitoring.metrics.Alert;

/**
 * @author vipink
 *
 */
public class RuleContext {

	
	RulesManager rulesManager;
	MetricsManager metricsManager;	
	
	public RuleContext(
			RulesManager rulesManager,
			MetricsManager metricsManager)
  {
		this.rulesManager = rulesManager;
		this.metricsManager = metricsManager;
	}
	
	public RulesManager getRulesManager() {
		return rulesManager;
	}

	public MetricsManager getMetricsManager() {
		return metricsManager;
	}

	public void setWarnAlerts(Row row, String name) {
		try {
			Iterable<Alert> alerts = 
					rulesManager.getAlertService().findByRule(name);
			if (alerts != null) {
				for (Alert alert : alerts) {
					if (row != null) {
						alert.setValue(row.getValue());
						alert.setMode(1);
					}
					rulesManager.getAlertStoreService().store(alert);
					rulesManager.getAlertStoreService().storeActiveRule(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setHealthyAlerts(Row row, String name) {
		try {
			Iterable<Alert> alerts = 
					rulesManager.getAlertService().findByRule(name);
			if (alerts != null) {
				for (Alert alert : alerts) {
					alert.setMode(0);
					alert.setTimestamp(System.currentTimeMillis());
					rulesManager.getAlertStoreService().store(alert);
					rulesManager.getAlertStoreService().removeActiveRule(name);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
