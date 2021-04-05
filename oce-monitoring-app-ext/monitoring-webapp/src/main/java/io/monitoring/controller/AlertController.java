/**
 * 
 */
package io.monitoring.controller;

import io.micrometer.core.annotation.Timed;
import io.monitoring.domain.MetricAlert;
import io.monitoring.domain.Source;
import io.monitoring.manager.RulesManager;
import io.monitoring.service.AlertService;

import java.util.List;

import oracle.monitoring.metrics.Alert;
import static io.monitoring.common.Constants.ALERT_LOGGER;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vipink
 *
 */
@RestController
@Timed 
@RequestMapping("/api/v1")
public class AlertController {

	@Autowired
	private RulesManager rulesManager; 
	
	@Autowired
	private AlertService alertService; 
	
    @GetMapping("/alerts")
	public Iterable<MetricAlert> availableAlert() {
    	try {
			return alertService.list();
		} catch (Exception e) {
			ALERT_LOGGER.error(e);
		}
    	return null;
	}
    
    @PostMapping("/alerts")
    public MetricAlert addMetricAlert(@RequestBody MetricAlert metricAlert) {
    	alertService.save(metricAlert);
        return metricAlert;
    }
    
    @PutMapping("/alerts")
    public MetricAlert updateSource(@RequestBody MetricAlert metricAlert) {
    	alertService.save(metricAlert);
        return metricAlert;
    }
    
}
