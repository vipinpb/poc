/**
 * 
 */
package io.monitoring.rest.client;

/**
 * @author vipink
 *
 */
public enum AuthType {
	
	BASIC(0), BEARER(1),OAUTH(2) ;
	
	private AuthType(int type) {
		this.type = type ;
	}
	
	private int type; 
	
	public int getType() {
		return type;
	}
}
