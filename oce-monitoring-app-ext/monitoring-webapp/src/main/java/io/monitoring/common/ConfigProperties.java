/**
 * 
 */
package io.monitoring.common;

import java.io.Serializable;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vipink
 *
 */

@ConditionalOnMissingBean
@ConfigurationProperties(prefix = "monitoring", ignoreUnknownFields = false)
public class ConfigProperties implements Serializable {

	/**
	 * 
	 */
	public ConfigProperties() {}

	private String appName;

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	
}
