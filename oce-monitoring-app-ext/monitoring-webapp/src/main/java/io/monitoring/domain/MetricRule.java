/**
 * 
 */
package io.monitoring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
//import javax.persistence.GeneratedType;

/**
 * @author vipink
 *
 */
@Entity
@Table(name = "metric_rules")
public class MetricRule {

	/**
	 * 
	 */
	public MetricRule() {}
	
    @Id
    //@GeneratedValue(strategy = GenerationType.);
	private long id;
	private String name;
	private String type;
	private String exp;
	private String waitfor;
	private long sourceId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getWaitfor() {
		return waitfor;
	}
	public void setWaitfor(String waitfor) {
		this.waitfor = waitfor;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSourceId() {
		return sourceId;
	}
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
}
