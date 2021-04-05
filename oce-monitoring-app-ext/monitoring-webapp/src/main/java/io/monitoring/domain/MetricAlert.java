/**
 * 
 */
package io.monitoring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.

/**
 * @author vipink
 *
 */
@Entity
@Table(name = "metric_alerts")
public class MetricAlert {

	/**
	 * 
	 */
	public MetricAlert() {}
    @Id
    //@GeneratedValue(strategy = IDENTITY)
	private long id;
	private String name;
	private long ruleId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getRuleId() {
		return ruleId;
	}
	public void setRuleId(long ruleId) {
		this.ruleId = ruleId;
	}
	@Override
	public String toString() {
		return "MetricAlert [id=" + id + ", name=" + name + ", ruleId="
				+ ruleId + "]";
	}
	
}
