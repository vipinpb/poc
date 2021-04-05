/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.manager.ScrapeManager;

import java.util.List;

/**
 * @author vipink
 *
 */
public class ScrapeManagerImpl implements ScrapeManager {

	/**
	 * 
	 */
	public ScrapeManagerImpl() {}

	@Override
	public List<String> scrape() {
		return MetricHelper.scrap();
	}

}
