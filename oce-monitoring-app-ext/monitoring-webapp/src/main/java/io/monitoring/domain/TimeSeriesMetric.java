/**
 * 
 */
package io.monitoring.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author vipink
 *
 */
@Entity
@Table(name = "time_series_metric")
public class TimeSeriesMetric {

	/**
	 * 
	 */
	public TimeSeriesMetric() {}

    @Id
	private long id;
	private String key;
	private String labels;
	private long timestamp;
	private String value;
	private long sourceId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public String toString() {
		return "TimeSeriesMetric [id=" + id + ", key=" + key + ", labels="
				+ labels + ", timestamp=" + timestamp + ", value=" + value
				+ ", source=" + sourceId + "]";
	}

}
