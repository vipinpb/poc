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
@Table(name = "metric_schedule")
public class MetricSchedule {

	/**
	 * 
	 */
	public MetricSchedule() {}
    @Id
	private long id;
	private String thread;
	private String schedule;
	
	private long sourceId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getThread() {
		return thread;
	}
	public void setThread(String thread) {
		this.thread = thread;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	@Override
	public String toString() {
		return "MetricSchedule [id=" + id + ", thread=" + thread
				+ ", schedule=" + schedule + ", sourceId=" + sourceId + "]";
	}
	
	
}
