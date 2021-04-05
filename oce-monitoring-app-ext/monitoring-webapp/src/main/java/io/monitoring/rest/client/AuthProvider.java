/**
 * 
 */
package io.monitoring.rest.client;


/**
 * @author vipink
 *
 */
public interface AuthProvider {

	public AuthType getAuthType();
	public AccessToken getToken();
}
