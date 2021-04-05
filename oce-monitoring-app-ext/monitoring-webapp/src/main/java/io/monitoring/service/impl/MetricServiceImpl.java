/**
 * 
 */
package io.monitoring.service.impl;

import io.monitoring.metrics.Row;
import io.monitoring.rule.engine.Condition;
import io.monitoring.service.MetricService;
import io.monitoring.service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author vipink
 *
 */

public class MetricServiceImpl implements MetricService {

	@Autowired
	StoreService storeService;

	
	@Override
	public Iterable<Row> getMetrics(Map<String, String[]> parameters, int limit, int offset) {
		return storeService.fetchMetrics(parameters, limit, offset);
	}

	@Override
	public void add(List<Row> records, String sourceName) {
		storeService.store(records, sourceName); 
	}

	@Override
	public Iterable<Row> getMetrics(Condition cond, long timestamp, long sourceId, int limit,
			int offset) {
		return storeService.getMetrics(cond, timestamp, sourceId, limit, offset);
	}
	
	@Override
	public Optional<Row> getMetrics(Condition cond, long timestamp, long sourceId) {
		return storeService.getMetrics(cond, timestamp, sourceId);
	}

	@Override
	public Iterable<Row> getMetrics(long timestamp, int limit, int offset) {
		return storeService.getMetrics(timestamp, limit, offset);
	}

	@Override
	public Iterable<Row> getKeys(int limit, int offset) {
		return storeService.getKeys(limit, offset);
	}
}