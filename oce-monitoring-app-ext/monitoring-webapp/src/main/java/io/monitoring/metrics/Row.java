/**
 * 
 */
package io.monitoring.metrics;

import java.io.Serializable;

/**
 * @author vipink
 *
 */
public class Row implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4465428253786189425L;
	/**
	 * 
	 */
	public Row() {}

	private Long timestamp;
	private String key;
	private String labels;
	private String value;
	private String instance;
	
	public Row(Long timestamp, String key, String value, String instance) {
		super();
		this.timestamp = timestamp;
		this.key = key;
		this.value = value;
		this.instance = instance;
	}
	
	
	public Row(Long timestamp, String key, String labels, String value,
			String instance) {
		super();
		this.timestamp = timestamp;
		this.key = key;
		this.labels = labels;
		this.value = value;
		this.instance = instance;
	}


	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
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
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public Row(Long timestamp) {
		super();
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Row [timestamp=" + timestamp + ", key=" + key + ", value="
				+ value + ", instance=" + instance + "]";
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	
	
}
