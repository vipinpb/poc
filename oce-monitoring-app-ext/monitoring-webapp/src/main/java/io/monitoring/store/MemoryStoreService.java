/**
 * 
 */
package io.monitoring.store;

import io.monitoring.domain.Source;
import io.monitoring.domain.TimeSeriesMetric;
import io.monitoring.metrics.Row;
import io.monitoring.repository.SourceRepository;
import io.monitoring.rule.engine.Condition;
import io.monitoring.service.StoreService;
import io.monitoring.service.impl.CustomIterable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author vipink
 *
 */
public class MemoryStoreService implements StoreService<Row> {

	@Autowired
	SourceRepository sourceRepository;
	
	/**
	 * 
	 */
	
	public MemoryStoreService() {}

	public static Map<Long,Map<String,Row>> store = new HashMap<>();

	public Iterable<Row> fetchMetrics(Map<String, String[]> parameters, int limit, int offset) {
		Iterable<Row> records = new ArrayList<>();
		// default timestamp
		Long timestamp = System.currentTimeMillis() - 60000 ;
		String timestamps[] = parameters.get("timestamp");
		if (timestamps != null && timestamps.length > 0) {
			timestamp = Long.parseLong(timestamps[0]);
		}
		String key = null;
		String names[] = parameters.get("name");
		if (names != null && names.length > 0) {
			key = names[0];
		}
		
		if (key == null) {
			// find all the keys in last one hour
			List<Row> metricsKey = findRowsByTimestamp(timestamp);
			List<Row> metricsKeyRow = new ArrayList<>();
			for (Row m: metricsKey) {
					metricsKeyRow.add(m);
			}
			records = new CustomIterable(metricsKeyRow);
		} else {
			records = findAllByTimestampAndKey(timestamp,key);
		}
		return records;
	}


	private List<Row> findAllByTimestampAndKey(Long timestamp, String keyNameStr) {
		Set<Long> keys = store.keySet();
		List<Row> validKeys = new ArrayList<>();
		for (Long key : keys) {
			if (key > timestamp) {
				Map<String,Row> tempTimeStore = store.get(key);
				if (tempTimeStore.get(keyNameStr) != null ) {
					validKeys.add(tempTimeStore.get(keyNameStr));
				}
			}
		}
		return validKeys;
	}

	private List<Row> findRowsByTimestamp(Long timestamp) {
		Set<Long> keys = store.keySet();
		List<Row> validKeys = new ArrayList<>();
		for (Long key : keys) {
			if (key > timestamp) {
				Map<String,Row> tempTimeStore = store.get(key);
				for (String keyName: tempTimeStore.keySet()) {
					if (!validKeys.contains(keyName)) {
						validKeys.add(tempTimeStore.get(keyName));
					}
				}
			}
		}
		return validKeys;
	}

	private List<String> findAllByTimestamp(Long timestamp) {
		Set<Long> keys = store.keySet();
		List<String> validKeys = new ArrayList<>();
		for (Long key : keys) {
			if (key > timestamp) {
				Map<String,Row> tempTimeStore = store.get(key);
				for (String keyName: tempTimeStore.keySet()) {
					if (!validKeys.contains(keyName)) {
						validKeys.add(keyName);
					}
				}
			}
		}
		return validKeys;
	}

	private void add(List<Row> values) {
		Long timestamp = System.currentTimeMillis();
		int count = 0;
		Map<String,Row> timeStore = new HashMap<>();
		for (Row r: values) {
			if (count == 0) {
				//timestamp = r.getTimestamp();
			}
			timeStore.put(r.getKey(), r);
		}
		if (store.get(timestamp) != null) {
			Map<String,Row> tempTimeStore = store.get(timestamp);
			tempTimeStore.putAll(timeStore);
			
		} else {
			store.put(timestamp, timeStore);
		}
	}

	@Override
	public void store(List<Row> records, String source) {
		Optional<Source> src = sourceRepository.findByName(source);
		add(records);
	}


	@Override
	public Iterable<Row> getMetrics(Condition cond, long timestamp,
			long sourceId,
			int limit,
			int offset) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Iterable<Row> getMetrics(long timestamp, int limit, int offset) {
		List<Row> metricsKey = findRowsByTimestamp(timestamp);
		return new CustomIterable(metricsKey);
	}


	@Override
	public Iterable<Row> getKeys(int limit, int offset) {
		Iterable<Row> records = new ArrayList<>();
		Long timestamp = System.currentTimeMillis() - 600000 ;
		List<String> metricsKey = findAllByTimestamp(timestamp);
		List<Row> metricsKeyRow = new ArrayList<>();
		for (String m: metricsKey) {
			Row r = new Row(0L,
					m,null,null,null);
			metricsKeyRow.add(r);
		}
		records = new CustomIterable(metricsKeyRow);
		return records;
	}


	@Override
	public Optional<Row> getMetrics(Condition cond, long timestamp,
			long sourceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
