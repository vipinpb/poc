/**
 * 
 */
package io.monitoring.rule.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vipink
 *
 */
public class TestParser {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//String exp = "up == 0";
		//String exp = "jvm_memory_used_bytes == 0 && jvm_heap_free_percent < 15";
		String exp = "jvm_memory_used_bytes{area=\"heap\",id=\"PS Old Gen\",} > 0";
		Map<String,String> labelMap = new HashMap<>();
		int labelIndex = exp.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = exp.substring(0, labelIndex);
			String labels = exp.substring(labelIndex+1, exp.indexOf('}'));
			String label[] = labels.split(",");
			for (String l: label) {
				String nameValue [] = l.split("=");
				labelMap.put(nameValue[0].toLowerCase(),nameValue[1].replaceAll("\"", "").toLowerCase());
			}
			exp = metricName +exp.substring(exp.indexOf('}')+1);
		}
		
		String tokens[] = exp.split(" ");
		for (String t: tokens) {
			System.out.println(" Value " + t);
		}
		//waitfor 2m
		// replace variables if any ${test}
		// build condition out of tokens
		int counter = 0;
		Condition cond = null;
		ConditionBuilder builder = new ConditionBuilder();
		for (String token: tokens) {
			builder.push(token);
		}
		
		cond = builder.build();
		//jvm_memory_committed_bytes{area="heap",id="PS Survivor Space",}
		//where key='jvm_heap_free_percent' and value < 15 &&  key='jvm_memory_used_bytes' and value==0
		
		//where key='jvm_heap_free_percent' and value < 15
		//where key='jvm_memory_used_bytes' and value==0

		
		System.out.println(cond);
		
		// build query based on condition
		
		
	}

}
