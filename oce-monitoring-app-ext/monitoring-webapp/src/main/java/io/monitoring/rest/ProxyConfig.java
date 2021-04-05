/**
 * 
 */
package io.monitoring.rest;

import java.io.Serializable;
import java.net.URI;

/**
 * @author vipink
 *
 */
public class ProxyConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5166276144721396308L;

	/**
	 * 
	 */
	public ProxyConfig() {
	}

	private URI uri;
	private String userName;
	private char[] password;

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getUriString() {
		return uri == null ? null : uri.toString();
	}

	public void setUriString(String uriString) {
		this.uri = uriString == null ? null : URI.create(uriString);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.toCharArray();
	}

	@Override
	public String toString() {
		return "ProxyConfig [uri=" + uri + ", userName=" + userName + "]";
	}

}
