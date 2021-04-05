/**
 * 
 */
package io.monitoring.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

/**
 * @author vipink
 *
 */
@Configuration(value = "config")
public class Config {

	private int readTimeout;
	private int connectTimeout;
	private Boolean useProxy;
	private String clientId;
	private String secret;
	private String baseUrl;
	private ProxyConfig proxy;
	private String userName;
	private char[] password;
	private Map<String,String> params = new HashMap<>();
	/**
	 * 
	 */
	public Config() {}

	public ProxyConfig getProxy() {

		if (!useProxy.booleanValue()) {
			return null;
		}
		if (proxy == null) {
			proxy = new ProxyConfig();
		}
		return proxy;
	}

	public void setProxy(ProxyConfig proxy) {
		this.proxy = proxy;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Boolean getUseProxy() {
		return useProxy;
	}

	public void setUseProxy(Boolean useProxy) {
		this.useProxy = useProxy;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String apiUrl) {
		this.baseUrl = apiUrl;
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

	public void setPassword(char[] password) {
		this.password = password;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public void addParam(String key, String value) {
		this.params.put(key, value);
	}
	
	public String getParam(String key) {
		return this.params.get(key);
	}

	public void setProxyConfig(ProxyConfig proxy2) {
		this.proxy = proxy2;
		
	}

	@Override
	public String toString() {
		return "Config [readTimeout=" + readTimeout + ", connectTimeout="
				+ connectTimeout + ", useProxy=" + useProxy + ", clientId="
				+ clientId + ", secret=" + secret + ", baseUrl=" + baseUrl
				+ ", proxy=" + proxy + ", userName=" + userName + ", password="
				+ Arrays.toString(password) + ", params=" + params + "]";
	}
	
}
