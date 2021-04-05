/**
 * 
 */
package io.monitoring.source;

import io.monitoring.metrics.Record;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.util.StringUtils;


/**
 * @author vipink
 *
 */
public class WlsExportMetricsParser {

	/**
	 * 
	 */
	public WlsExportMetricsParser() {}

	public Record row(long timestamp, String metric) {
		Map<String,String> labelMaps = new HashMap<>();
		Record record = new Record();
		String key = null;
		String value = null;
		String input= metric;
		int labelIndex = input.indexOf('{');
		if (labelIndex >= 0 ) {
			String metricName = input.substring(0, labelIndex);
			final StringBuilder keyBuilder = new StringBuilder(metricName);
			String labels = input.substring(labelIndex+1, input.indexOf('}'));
			//System.out.println(labels);
			String label[] = labels.split(",");
			for (String l: label) {
				if (l.trim().length() == 0) {
					continue;
				}
				String nameValue [] = l.split("=");
				labelMaps.put(nameValue[0].toLowerCase(), nameValue[1].toLowerCase());
			}
			String values = input.substring(input.lastIndexOf('}')+1);
			key = keyBuilder.toString();
			String tokenValues[] = values.trim().split(" ");
			if (tokenValues != null && tokenValues.length == 2) {
				value = tokenValues[0];
			} else if (tokenValues != null && tokenValues.length ==1) {
				value = tokenValues[0];
			}
			record = new Record(key, value, timestamp);
			
			//System.out.println("key"+key);
			//System.out.println("labelMaps "+labelMaps);
			record.setLabels(labelMaps);
			
		} else {
			String tokenValues[] = input.trim().split(" ");
			if (tokenValues != null && tokenValues.length ==3) {
				key = tokenValues[0];
				value = tokenValues[1];
			} else if (tokenValues != null && tokenValues.length ==2) {
				key = tokenValues[0];				
				value = tokenValues[1];
			}
			if (StringUtils.isEmpty(key)) {
				return null;
			}
			record = new Record(key, value, timestamp);
		}
		return record;
	}
	
}
