/**
 * 
 */
package io.monitoring.controller;

import io.monitoring.domain.MetricRule;
import io.monitoring.manager.RulesManager;
import io.monitoring.metrics.QueryResponse;
import io.monitoring.metrics.Status;
import io.monitoring.rule.engine.TSDBRule;
import io.monitoring.service.RuleException;
import io.monitoring.service.RuleService;

import java.util.Optional;

import io.monitoring.metrics.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vipink
 * 
 * This is going to have following endpoints
 * 1. Listing all the rules
 * 2. List all the active rules
 * 3. Add a rule
 * 4. Delete a rule
 * 5. Evaluate a rule
 * 6. Evaluate all the rules
 *
 */
@RestController
@RequestMapping("/api/v1/rules") 
public class RulesController {

	@Autowired
	private RulesManager rulesManager; 
	
    @GetMapping("")
	public Iterable<MetricRule> rules() {
    	try {
    		QueryResponse<MetricRule> resp = new QueryResponse<>();
			return rulesManager.getRuleService().findAll();
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}
    
    @GetMapping("/evaluate/{rule}")
	public ResponseEntity<?> evaluate(@PathVariable String rule) {
    	try {
    		Optional<Alert> optionalAlert = rulesManager.evaluate(rule);
    		if (optionalAlert.isPresent()) {
    			return new ResponseEntity<>(optionalAlert.get(), HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new Status(" No alert found"), HttpStatus.OK);
    		}
		} catch (RuleException e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    
    @PostMapping("/create-and-evaluate")
	public ResponseEntity<?> createAndEvaluate(@RequestBody MetricRule rule) {
    	try {
    		Optional<MetricRule> optionalAlert = rulesManager.createAndEvaluate(rule);
    		if (optionalAlert.isPresent()) {
    			return new ResponseEntity<>(optionalAlert.get(), HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new Status(" No alert found"), HttpStatus.OK);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    
    @GetMapping("/evaluateall")
	public ResponseEntity<?> evaluateAll() {
    	try {
			return new ResponseEntity<>(rulesManager.evaluateAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
       return new ResponseEntity<>(new Status(" No alert found"), HttpStatus.OK);	
     }
    
    @GetMapping("/engine/start")
	public ResponseEntity<?> ruleEngineStart() {
    	try {
    		Optional<Status> optionalAlert = rulesManager.startRuleEngine();
    		if (optionalAlert.isPresent()) {
    			return new ResponseEntity<>(optionalAlert.get(), HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new Status(" Could not start rule engine or already start"), HttpStatus.OK);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
    
    @GetMapping("/engine/stop")
	public ResponseEntity<?> ruleEngineStop() {
    	try {
    		Optional<Status> optionalAlert = rulesManager.stopRuleEngine();
    		if (optionalAlert.isPresent()) {
    			return new ResponseEntity<>(optionalAlert.get(), HttpStatus.OK);
    		} else {
    			return new ResponseEntity<>(new Status(" Could not stop rule engine or already stopped"), HttpStatus.OK);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
