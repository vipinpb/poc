/**
 * 
 */
package io.monitoring.metrics;

import io.monitoring.common.Util;

import java.util.Map;

/**
 * @author vipink
 *
 */
public class Record { 

	/**
	 * 
	 */
	public Record() {
		// TODO Auto-generated constructor stub
	}
	String key;
	String value;
	Map<String, String> labels;
	Long timestamp;
	public Record(String key, String value, Long timestamp) {
		super();
		this.key = key;
		this.value = Util.processValue(value);
		this.timestamp = timestamp;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Map<String, String> getLabels() {
		return labels;
	}
	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Record [key=" + key + ", value=" + value + ", labels=" + labels
				+ ", timestamp=" + timestamp + "]";
	}
	
	
}
