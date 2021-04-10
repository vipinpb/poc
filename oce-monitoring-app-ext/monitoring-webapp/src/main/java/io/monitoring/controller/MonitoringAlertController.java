/**
 * 
 */
package io.monitoring.controller;

import io.micrometer.core.annotation.Timed;
import io.monitoring.service.AlertService;
import io.monitoring.service.AlertStoreService;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import io.monitoring.metrics.Alert;
import static io.monitoring.common.Constants.ALERT_LOGGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author vipink
 *
 */
@RestController
@Timed 
@RequestMapping("/api/v1/monitoring")
public class MonitoringAlertController {

	@Autowired
	private AlertStoreService alertStoreService; 
    
    @GetMapping("/monitoringalerts")
	public Iterable<Alert> alerts() {
    	try {
			return alertStoreService.getAlerts();
		} catch (Exception e) {
			ALERT_LOGGER.error(e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"); 
		}
	}
    
    @GetMapping("/consume/{alertName}")
	public Alert consume(@PathVariable String alertName) {
    	try {
    		Optional<Alert> alertOptional = alertStoreService.remove(alertName);
    		if (alertOptional.isPresent()) {
    			return alertOptional.get();
    		} else {
    			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"); 
    		}
		} catch (Exception e) {
			ALERT_LOGGER.error(e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"); 
		}
	}
    
    @GetMapping("/get/{alertName}")
	public Alert get(@PathVariable String alertName) {
    	try {
    		Optional<Alert> alertOptional = alertStoreService.findAlertByName(alertName);
    		if (alertOptional.isPresent()) {
    			return alertOptional.get();
    		} else {
    			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"); 
    		}
		} catch (Exception e) {
			ALERT_LOGGER.error(e);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource"); 
		}
	}
}
