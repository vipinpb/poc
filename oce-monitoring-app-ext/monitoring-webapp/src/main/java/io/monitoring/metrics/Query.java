/**
 * 
 */
package io.monitoring.metrics;

import java.util.HashMap;
import java.util.Map;

/**
 * @author vipink
 *
 */
public class Query {

	String range;
	Map<String,String> filters = new HashMap<>();
	String key;
	
	public Query(String range, String key) {
		super();
		this.range = range;
		this.key = key;
	}
	
	public Query(String query) {
		String input= query;
		int labelIndex = input.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = input.substring(0, labelIndex);
			final StringBuilder keyBuilder = new StringBuilder(metricName);
			String labels = input.substring(labelIndex+1, input.indexOf('}'));
			System.out.println(labels);
			String label[] = labels.split(",");
			for (String l: label) {
				String nameValue [] = l.split("=");
				filters.put(nameValue[0], nameValue[1]);
				keyBuilder.append("#"+nameValue[0].toLowerCase()+":"+nameValue[1].replaceAll("\"", "").toLowerCase());
			}
			String values = input.substring(input.indexOf('}')+1);
			this.key = keyBuilder.toString();
			String tokenValues[] = values.split(" ");
			if (tokenValues != null && tokenValues.length ==1) {
				this.range = tokenValues[0].substring(1, tokenValues[0].length()-1);
			}
		} else {
			String tokenValues[] = input.split(" ");
			if (tokenValues != null && tokenValues.length ==2) {
				this.key = tokenValues[0];
				this.range = tokenValues[1].substring(1, tokenValues[1].length()-1);
			} else {
				this.key = tokenValues[0];
			}
		}
		System.out.println("Setting key" + this.key);
	}

	public String getRange() {
		return range;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public String getKey() {
		return key;
	}
	
	
	
}
