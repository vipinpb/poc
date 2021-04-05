/**
 * 
 */
package io.monitoring.rest.client;

import io.monitoring.rest.Config;

import java.util.HashMap;
import java.util.Map;

/**
 * This would provide the necessary information for API to work correctly
 * 
 * @author vipink
 *
 */
public class RestContext {

	private AuthProvider authProvider;
	private Config config;
	private Map<String,String> properties = new HashMap<>();
	private String baseUrl;
	
	/**
	 * 
	 */
	public RestContext(AuthProvider authProvider) {
		this.authProvider = authProvider;
	}
	
	public AuthProvider getAuthProvider() {
		return authProvider;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public RestContext(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
	}
	
	public RestContext auth(AuthProvider authProvider) {
		this.authProvider = authProvider;
		return this;
	}

	public Config getConfig() {
		return config;
	}

	public RestContext config(Config config) {
		this.config = config;
		return this;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}

	public RestContext(AuthProvider authProvider, String baseUrl) {
		super();
		this.authProvider = authProvider;
		this.baseUrl = baseUrl;
	} 
	

}
