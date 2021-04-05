/**
 * 
 */
package io.monitoring.metrics;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author vipink
 *
 */


@JsonInclude(JsonInclude.Include.NON_NULL) 
public class Metric implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793746847522431326L;

	/**
	 * 
	 */
	public Metric() {}
	private String key;
	private String value;
	private Map<String, String> labels;

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
}
