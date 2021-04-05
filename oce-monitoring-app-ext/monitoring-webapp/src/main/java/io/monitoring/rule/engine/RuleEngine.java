package io.monitoring.rule.engine;

import io.monitoring.domain.MetricRule;
import io.monitoring.manager.MetricsManager;
import io.monitoring.manager.RulesManager;
import io.monitoring.service.AlertService;
import io.monitoring.service.AlertStoreService;
import io.monitoring.service.MetricService;
import io.monitoring.service.RuleException;
import io.monitoring.service.RuleService;
import io.monitoring.service.impl.RuleHelper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleEngine {

	@Autowired
	RulesManager rulesManager;
	@Autowired
	MetricsManager metricsManager;	
	
	public void fires() {
		
		try {
			Iterable<IRule> rulesItrable = rulesManager.getRuleService().buildAllRules();
			RuleContext context = new RuleContext(
					rulesManager,metricsManager);
			
			// go over the active rules
			for (String ruleName : rulesManager.getAlertStoreService().listActiveRules()) {
				// find the metricrule by name
				Iterable<MetricRule> rule = rulesManager.getRuleService().findByName(ruleName) ;
				for (MetricRule r: rule) {
					Optional<IRule>  iRuleOptional = RuleHelper.buildRule(r);
					if (iRuleOptional.isPresent()) {
						if (!iRuleOptional.get().shouldProcess(context)) {
							context.setHealthyAlerts(null, ruleName);
						}
					}
				}
			}
			for(IRule r : rulesItrable) {
				try {
					evaluateRule(context, r);
				} catch (RuleException ex) {
					ex.printStackTrace();
				}
			}
			
			// now go over the active rules 
		} catch (RuleException e) {
			e.printStackTrace();
		}
	}
	
	private void evaluateRule(RuleContext context, IRule matchedRule) throws RuleException {
		if (matchedRule.shouldProcess(context)) {
			matchedRule.fire(context);
		}
	}

	public void evaluateRule(IRule matchedRule) throws RuleException{
		RuleContext context = new RuleContext(
				rulesManager,metricsManager);
		
		if (matchedRule.shouldProcess(context)) {
			matchedRule.fire(context);
		}
	}
}
