/**
 * 
 */
package io.monitoring.rest.client;

import java.util.Base64;


/**
 * @author vipink
 *
 */
public class BasicAuthProvider implements AuthProvider {

	
	private String authString;
	private char[] password;
	/**
	 * 
	 */
	public BasicAuthProvider(String authString) {
		this.authString = authString ;
	}
	
	public AuthType getAuthType() {
		return AuthType.BASIC;
	}

	// preeti.yarashi@oracle.com
	/* (non-Javadoc)
	 * @see oracle.sites.oic.restclient.AuthProvider#getHeaderOrBodyToken()
	 */
	public AccessToken getToken() {
        String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
        AccessToken token = new AccessToken(authStringEnc,1);
        return token;
	}

}
