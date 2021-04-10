package io.monitoring.rule.engine;

import io.monitoring.metrics.Row;
import io.monitoring.service.impl.RuleHelper;

import java.io.Serializable;
import java.util.Optional;

import io.monitoring.metrics.Alert;

public class TSDBRule implements Serializable, IRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7869894768275864801L;
	private String name;
	private String exp;
	private String waitfor;
	private long sourceId;
	private Boolean shouldProcess = Boolean.FALSE;
	private Row row ;

	public TSDBRule(String name, 
			String exp, 
			String waitfor,
			long sourceId) {
		super();
		this.name = name;
		this.exp = exp;
		this.waitfor = waitfor;
		this.sourceId = sourceId;
	}

	public boolean shouldProcess(RuleContext context) {
		try {
			Condition cond = context.getRulesManager().getRuleService().getRuleParser().parse(exp);
			// find minutes or second
			Long timestamp = RuleHelper.findRuleDuration(waitfor);
			Optional<Row> record = context.getMetricsManager().getMetricService().getMetrics(cond, timestamp, sourceId);

			if (record.isPresent()) {
				shouldProcess = true;
				row = record.get();
				System.out.println(" Found r "+row);
			}
	
			return shouldProcess  ;
		} catch (RuleParserException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	/**
	 * Validate if the rule can be processed 
	 */
	
	public void fire(RuleContext context) {
		context.setWarnAlerts(row,name);
	}
}
