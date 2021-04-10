/**
 * 
 */
package io.monitoring.manager;

import static io.monitoring.common.Constants.LOGGER;
import io.monitoring.domain.MetricRule;
import io.monitoring.metrics.Status;
import io.monitoring.metrics.Task;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.RuleEngine;
import io.monitoring.rule.engine.RuleParser;
import io.monitoring.service.AlertService;
import io.monitoring.service.AlertStoreService;
import io.monitoring.service.RuleException;
import io.monitoring.service.RuleService;
import io.monitoring.service.impl.RuleHelper;

import java.util.Date;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicBoolean;

import io.monitoring.metrics.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * @author vipink
 *
 */
public class RuleManagerImpl implements RulesManager { 

	@Autowired
	RuleService ruleService;
	
	@Autowired
	RuleEngine ruleEngine;

	@Autowired
	AlertStoreService alertStoreService;
	
	@Autowired
	private AlertService alertService; 
	
	@Autowired
	ThreadPoolTaskManager threadPoolTaskManager;
	
	final static AtomicBoolean ruleEngineRunning = new AtomicBoolean(false);

	/* (non-Javadoc)
	 * @see oracle.monitoring.service.RuleManager#getRules()
	 */
	@Override
	public Iterable<IRule> getRules() {
		
		try {
			return ruleService.buildAllRules();
		} catch (RuleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Optional<Alert> evaluate(String ruleName) throws RuleException {

		Iterable<MetricRule> metricRuleList = ruleService.findByName(ruleName);
		System.out.println(metricRuleList+ "Found the rules ");
		for (MetricRule r : metricRuleList) {
			System.out.println(r+ "Found the rules ");
			Optional<IRule> ruleOptional = RuleHelper.buildRule(r);
			if (ruleOptional.isPresent()) {
				System.out.println(r+ "evaluating rules ");
				ruleEngine.evaluateRule(ruleOptional.get());
			}
		}
		Iterable<Alert> alerts = alertStoreService.getAlerts();
		Optional<Alert> optionalAlert = Optional.empty();
		for (Alert alert : alerts) {
			optionalAlert = Optional.of(alert);
		}
		return optionalAlert;
	}

	public Optional<Alert> evaluate(IRule rule) throws RuleException {

		ruleEngine.evaluateRule(rule);

		Iterable<Alert> alerts = alertStoreService.getAlerts();
		Optional<Alert> optionalAlert = Optional.empty();
		for (Alert alert : alerts) {
			optionalAlert = Optional.of(alert);
		}
		return optionalAlert;
	}
	
	@Override
	public Iterable<Alert> evaluateAll() throws RuleException {
		
		Iterable<MetricRule> metricRuleList = ruleService.findAll();
		for (MetricRule r : metricRuleList) {
			Optional<IRule> ruleOptional = RuleHelper.buildRule(r);
			if (ruleOptional.isPresent()) {
				try {
					ruleEngine.evaluateRule(ruleOptional.get());
				} catch(RuleException ex){
					ex.printStackTrace();
				}
			}
		}
		Iterable<Alert> alerts = alertStoreService.getAlerts();
		return alerts;
	}

	@Override
	public Optional<MetricRule> createAndEvaluate(MetricRule metricRule) throws RuleException {
			evaluate(RuleHelper.buildRule(metricRule).get());
		return Optional.of(metricRule) ; 
	}
	
	@Override
	public RuleParser getRuleParser() {
		return ruleService.getRuleParser();
	}

	class RuleEngineTask implements Task {
		   

		private String engineName ="Metric-RuleEngine";
		private Long id;
		
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}
	    public RuleEngineTask(Long id){this.id = id;}
	    
	    private ScheduledFuture future;
	    
	    public void setFuture(ScheduledFuture future) {
	      this.future = future;
	    }
	    
	    public ScheduledFuture getFuture() {
	      return future;
	    }
	    
	    @Override
	    public void run() {
	    	if (!Thread.currentThread().getName().equalsIgnoreCase(engineName)) {
	    		Thread.currentThread().setName(engineName);
	    	}
	        System.out.println(new Date()+" RuleEngine Task with "+Thread.currentThread().getName());
	        try {
	        	ruleEngine.fires();
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("Error running the rule engine", e);
			}
	    }
	}
	
	@Override
	public Optional<Status> startRuleEngine() {
		Long id = 100000L;
		if (threadPoolTaskManager.tasks().get(id) !=null ) {
			return Optional.of(new Status(" rule engine is already started"));
		}
		RuleEngineTask task = new RuleEngineTask(id);
	    ScheduledFuture future = threadPoolTaskManager.getThreadPoolTaskScheduler().schedule(task, new 
	    PeriodicTrigger(30000));
	    task.setFuture(future);
	    threadPoolTaskManager.tasks().put(id,task);
	    return Optional.of(new Status(" Started the rule engine"));
	}

	@Override
	public Optional<Status> stopRuleEngine() {
		
		Iterator<Task> it = threadPoolTaskManager.tasks().values().iterator();
		Long id = 100000L;
		if (threadPoolTaskManager.tasks().get(id) ==null ) {
			return Optional.of(new Status(" rule engine is already stopped"));
		}
		while (it.hasNext()) {
			Task task = (Task) it.next();
			if (id.equals(task.getId())) {
				task.getFuture().cancel(false);
				it.remove();
			}
		}
		return Optional.of(new Status(" Stopped the rule engine"));
	}

	@Override
	public RuleEngine getRuleEngine() {
		return ruleEngine;
	}

	@Override
	public RuleService getRuleService() {
		return ruleService;
	}

	@Override
	public AlertService getAlertService() {
		return alertService;
	}

	@Override
	public AlertStoreService getAlertStoreService() {
		return alertStoreService;
	}
}
