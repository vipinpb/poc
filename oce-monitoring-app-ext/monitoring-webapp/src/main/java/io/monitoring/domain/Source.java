/**
 * 
 */
package io.monitoring.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;

/**
 * @author vipink
 *
 */
@Entity
@Table(name = "metric_source")
public class Source {

	/**
	 * 
	 */
	public Source() {
		// TODO Auto-generated constructor stub
	}

    @Id
    //@GeneratedValue(strategy = GenerationType);
	private long id;
	private String classname;
	private String configuration;
	private String name;
	private String description;
	private boolean disable;
	@Override
	public String toString() {
		return "Source [id=" + id + ", classname=" + classname
				+ ", configuration=" + configuration + ", name=" + name
				+ ", description=" + description + ", disable=" + disable + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public String getConfiguration() {
		return configuration;
	}
	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public Source(long id, String classname, String configuration, String name,
			String description) {
		super();
		this.id = id;
		this.classname = classname;
		this.configuration = configuration;
		this.name = name;
		this.description = description;
		this.disable = false;
	}
	
}
