/**
 * 
 */
package io.monitoring.service;

import io.monitoring.metrics.Row;
import io.monitoring.rule.engine.Condition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author vipink
 *
 */

public interface MetricService {

	Iterable<Row> getMetrics(Map<String, String[]> parameters, int limit, int offset);
	
	void add(List<Row> records, String sourceName);
	
	Iterable<Row> getMetrics(Condition cond, long timestamp, long sourceId, int limit, int offset);
	
	Iterable<Row> getMetrics(long timestamp, int limit, int offset);
	
	Iterable<Row> getKeys(int limit, int offset);

	Optional<Row> getMetrics(Condition cond, long timestamp, long sourceId);
}
