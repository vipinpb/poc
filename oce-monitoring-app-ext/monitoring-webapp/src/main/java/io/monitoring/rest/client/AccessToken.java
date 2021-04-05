/**
 * 
 */
package io.monitoring.rest.client;

import java.io.Serializable;

/**
 * @author vipink
 *
 */
public class AccessToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2157771509297068607L;
	/**
	 * 
	 */
	public AccessToken() {}

	private String value;
	// send as header or body 
	/*
	 * 1 means Basic header and 2 means body parameter
	 */
	private int type ;
	public String getValue() {
		return value;
	}
	public void setValue(String token) {
		this.value = token;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Token [token=" + value + ", type=" + type + "]";
	}
	public AccessToken(String value, int type) {
		super();
		this.value = value;
		this.type = type;
	}
	
	
}
