/**
 * 
 */
package io.monitoring.controller;

import io.monitoring.manager.MetricsManager;
import io.monitoring.metrics.QueryResponse;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vipink
 *
 */
@RestController
@RequestMapping("/api/v1")
public class ScrapeMetricsController {

	@Autowired
	private MetricsManager metricsManager;
	
 
    @GetMapping("/metrics")
	public QueryResponse metricsAll(HttpServletRequest request) {
    	Map<String, String[]> parameters = request.getParameterMap();
    	return metricsManager.keys(parameters);
	}
    
    @GetMapping("/metrics/{name}")
	public QueryResponse metricsByName(HttpServletRequest request, 
			@PathVariable String name) {
    	Map<String, String[]> parameters = request.getParameterMap();
    	Map<String, String[]> modifiedMap =  new HashMap<>();
    	modifiedMap.putAll(parameters);
    	modifiedMap.put("name", new String[] {name});
    	return metricsManager.findByKey(modifiedMap);
	}
    
    @GetMapping("/metrics/search")
	public QueryResponse metrics(HttpServletRequest request) {
    	Map<String, String[]> parameters = request.getParameterMap();
    	return metricsManager.query(parameters);
	}
    
    @GetMapping("/metrics/key")
	public QueryResponse fetchKeys(HttpServletRequest request) {
    	return metricsManager.keys();
	}
    
    @GetMapping("/metrics/query")
	public QueryResponse match(HttpServletRequest request) {
    	Map<String, String[]> parameters = request.getParameterMap();
    	return metricsManager.match(parameters);
	}
}
