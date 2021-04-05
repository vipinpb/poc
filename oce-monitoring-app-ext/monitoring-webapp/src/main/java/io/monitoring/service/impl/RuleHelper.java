/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.domain.MetricRule;
import io.monitoring.rule.engine.IRule;
import io.monitoring.rule.engine.TSDBRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




/**
 * @author vipink
 *
 */
public class RuleHelper {

	/**
	 * 
	 */
	public RuleHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<TSDBRule> ruleList() {
		List<TSDBRule> ruleList = new ArrayList<>();//jvm_heap_free_percent
//jvm_threads_states_threads
		TSDBRule r1 = new TSDBRule("cpu","process_cpu_usage > .3","1m",20);
		ruleList.add(r1);

		r1 = new TSDBRule("request-time","http_server_requests_seconds_sum > .1","1m",20);
		ruleList.add(r1);
		
		TSDBRule r2 = new TSDBRule("Heap","jvm_heap_free_percent < 15","1m",10);

		ruleList.add(r2);

		TSDBRule r3 = new TSDBRule("Memory","jvm_memory_used_bytes > 2.33","1m",20);

		ruleList.add(r3);
		
		return ruleList;
	}
	
	/**
	 * This should return milliseconds (Current time - waitFor)
	 * @param waitFor
	 * @return
	 */
	public static Long findRuleDuration(String waitFor) {
		Long timestamp = null;
		// find minutes or second
		if (waitFor != null) {
			boolean minutes = waitFor.endsWith("m");
			if (minutes) {
				timestamp = Long.parseLong(waitFor.substring(0, waitFor.length()-1)) * 60 * 1000;
			} else {
				timestamp = Long.parseLong(waitFor.substring(0, waitFor.length()-1)) * 1000;
			}
			 timestamp = System.currentTimeMillis() - timestamp;
		} else {
			 timestamp = System.currentTimeMillis() - 60000;
		}
		return timestamp;
	}
	
	public static Optional<IRule> buildRule(MetricRule  r) {
		if (r != null) {
			return Optional.of(new TSDBRule(
				r.getName(), r.getExp(), r.getWaitfor(), r.getSourceId()));
		} else {
			return Optional.empty();
		}
	}

}
