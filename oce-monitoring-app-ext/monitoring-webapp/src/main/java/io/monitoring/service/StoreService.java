/**
 * 
 */
package io.monitoring.service;

import io.monitoring.rule.engine.Condition;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * this would represent what goes to database.  This is basic for now But it would be expanded to 
 * support various features of TSDB database
 * 
 * @author vipink
 */
public interface StoreService<T> {
	public Iterable<T> fetchMetrics(Map<String, String[]> parameters, int limit, int offset) ;
	public void store(List<T> records, String source);
	Iterable<T> getMetrics(Condition cond, long timestamp, long sourceId, int limit, int offset);	
	Iterable<T> getMetrics(long timestamp, int limit, int offset);
	Iterable<T> getKeys(int limit, int offset);
	public Optional<T> getMetrics(Condition cond, long timestamp, long sourceId);
}
