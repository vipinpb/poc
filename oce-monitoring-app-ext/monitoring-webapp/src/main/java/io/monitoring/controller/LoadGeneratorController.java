/**
 * 
 */
package io.monitoring.controller;

import static io.monitoring.common.Constants.ALERT_LOGGER;
import io.monitoring.metrics.Status;
import io.monitoring.service.AlertService;
import io.monitoring.util.LoadCpu;
import io.monitoring.util.LoadHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import io.monitoring.metrics.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vipink
 *
 */
@RestController
@RequestMapping("/api/v1/test")
public class LoadGeneratorController {

	
	@Autowired
	private LoadHelper loadHelper; 
	
	
    @GetMapping("/generate/fakealerts")
	public List<Alert> fakealerts() {
    	try {
			return loadHelper.generateAlerts();
		} catch (Exception e) {
			e.printStackTrace();
			ALERT_LOGGER.error(e);
		}
    	return null;
	}
    
    @GetMapping("/createsession")
	public Status createSession(HttpServletRequest request) {
    	HttpSession session = request.getSession(true);
    	session.setMaxInactiveInterval(60);
    	return new Status("created 1 session");
	}
    
    @GetMapping("/removesessions")
	public Status removeSessions(HttpServletRequest request) {
        //request.getSession().getServletContext().setSessionTimeout(sessionTimeout);
    	return new Status("removed 1 session");
	}
    

    @GetMapping("/generate-alerts")
	public Status generateAlerts(HttpServletRequest request) {
    	String scheme = request.getScheme();
    	String host = request.getServerName();
    	int port = request.getServerPort();
    	String ctx = request.getContextPath();
    	String baseUrl = scheme + "://"+host + ":"+port+ctx; 
    	for (int k =0; k< 100 ; k++) {
    		loadHelper.generateSession(baseUrl);
    	}
    	return new Status("created 100 session");
	}
    
    @GetMapping("/generate-cpuload")
	public Status generateCpuload() {
    	new LoadCpu().addLoad();
    	return new Status("generated cpu load for 1000 ms");
	}
    
    @GetMapping("/load-heap")
	public Status useHeap() {
    	Map<Integer,String> loadedMap = new HashMap<>();
    	for (int k =0; k< 100 * 1000 * 60 ; k++) {
    		loadedMap.put(k, new String (" I am testing heap "+k));
    	}
    	return new Status("loaded heap with cpu load for 10000k objects");
	}
    
    @GetMapping("/generateRequest")
	public Status generateRequest() {
    	return null;
	}
    
    @GetMapping("/increaseThreads")
	public Status increaseThreads() {
    	return null;
	}
}
