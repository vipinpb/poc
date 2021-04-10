/**
 * 
 */
package io.monitoring.common;

import io.monitoring.domain.MetricRule;
import io.monitoring.metrics.Mode;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.TSDBRule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List; 

import io.monitoring.metrics.Alert;


/**
 * @author vipink
 *
 */
public class Util {

	/**
	 * 
	 */
	public Util() {
		// TODO Auto-generated constructor stub
	}

	public static List<Alert> listOfAlerts = new ArrayList<>();
	static {
		listOfAlerts.add(new Alert("CPU","System is running with high cpu", Mode.WARN.get()));
		listOfAlerts.add(new Alert("StuckThread","System is running with stuck threads", Mode.WARN.get()));
		listOfAlerts.add(new Alert("Heap","System is running with high heap", Mode.WARN.get()));
		listOfAlerts.add(new Alert("HighThreadCount","System is low with available threads", Mode.WARN.get()));
		listOfAlerts.add(new Alert("OMLService","OML service is down", Mode.WARN.get()));
		listOfAlerts.add(new Alert("SearchService","Search is not available", Mode.WARN.get()));
		listOfAlerts.add(new Alert("DBService","System is running with high cpu", Mode.WARN.get()));
		listOfAlerts.add(new Alert("DBConnectionCount","DB is running low with available connections", Mode.WARN.get()));
		
	}
	
	public static List<Alert> availableAlerts() {
		return listOfAlerts;
	}
	
	public static Iterable<IRule> buildRules(Iterable<MetricRule> metricRules) {
		List<IRule> rules = new ArrayList<>();
		for (MetricRule mr:metricRules) {
			TSDBRule r = new TSDBRule(mr.getName(), mr.getExp(), mr.getWaitfor(), mr.getSourceId());
			rules.add(r);
		}
		return rules;
	}
	
	public static String processValue(String value) {
		try {
			BigDecimal bd = new BigDecimal(value);
			return bd.toPlainString();
		} catch (Exception ex) {
			return "0";
		}
	}
	
}

