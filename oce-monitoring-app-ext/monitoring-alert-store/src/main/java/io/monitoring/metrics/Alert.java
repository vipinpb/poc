/**
 * 
 */
package io.monitoring.metrics;

/**
 * @author vipink
 *
 */
public class Alert {
	
	private String name;
	private String description;
	private int mode;
	private String value;
	private long timestamp;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Alert(String name) {
		super();
		this.name = name;
	}

	public Alert(String name, String description) {
		super();
		this.name = name;
		this.description = description;
		this.timestamp = System.currentTimeMillis();
	}

	public Alert(String name, String description, int mode) {
		super();
		this.name = name;
		this.description = description;
		this.mode = mode;
		this.timestamp = System.currentTimeMillis();
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Alert [name=" + name + ", description=" + description
				+ ", mode=" + mode + "]";
	}
}
