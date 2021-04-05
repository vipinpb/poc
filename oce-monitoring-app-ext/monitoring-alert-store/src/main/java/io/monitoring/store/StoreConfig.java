/**
 * 
 */
package io.monitoring.store;

import io.monitoring.store.service.MonitoringAlertStoreService;
import io.monitoring.store.service.MonitoringAlertStoreServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author vipink
 *
 */
@Configuration
public class StoreConfig {

	@Bean
	public MonitoringAlertStoreService monitoringAlertStoreService() {
		return new MonitoringAlertStoreServiceImpl();
	}
}
