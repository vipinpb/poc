/**
 * 
 */
package io.monitoring.rest.client;


/**
 * @author vipink
 *
 */
public class BearerAuthProvider implements AuthProvider {

	
	private String token;
	/**
	 * 
	 */
	public BearerAuthProvider(String token) {
		this.token = token ;
	}

	public AuthType getAuthType() {
		return AuthType.BEARER;
	}
	/* (non-Javadoc)
	 * @see oracle.sites.oic.restclient.AuthProvider#getHeaderOrBodyToken()
	 */
	public AccessToken getToken() {
        AccessToken bearer = new AccessToken(token,1);
        return bearer;
	}

}
