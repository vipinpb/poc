/**
 * 
 */
package io.monitoring.store;

import io.monitoring.domain.Source;
import io.monitoring.domain.TimeSeriesMetric;
import io.monitoring.metrics.Row;
import io.monitoring.repository.SourceRepository;
import io.monitoring.repository.TimeSeriesMetricRepository;
import io.monitoring.rule.engine.Condition;
import io.monitoring.service.StoreService;
import io.monitoring.service.impl.CustomIterable;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author vipink
 *
 */
public class HSQLDBStoreService implements StoreService<Row> {

	@Autowired
	TimeSeriesMetricRepository timeSeriesMetricRepository;
	@Autowired
	SourceRepository sourceRepository;

	String tableName = "time_series_metric";
	
	public HSQLDBStoreService() {}

	public Iterable<Row> fetchMetrics(Map<String, String[]> parameters, int limit, int offset) {
		List<Row> records = new ArrayList<>();
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
		Iterable<TimeSeriesMetric> metrics = null;
		if (key != null) {
			metrics = timeSeriesMetricRepository.listByKeyAndTimestamp(key, timestamp, offset, limit);
			
			for (TimeSeriesMetric metric: metrics) {
				Optional<Source> src = sourceRepository.findById(metric.getSourceId());
					Row r = new Row(metric.getTimestamp(),
						metric.getKey(),metric.getLabels(),metric.getValue(),src.get().getName());
				records.add(r);
			}
		} else {
			metrics = timeSeriesMetricRepository.listByTimestamp(timestamp, offset, limit);
			
			for (TimeSeriesMetric metric: metrics) {
				Optional<Source> src = sourceRepository.findById(metric.getSourceId());
					Row r = new Row(metric.getTimestamp(),
						metric.getKey(),metric.getLabels(),metric.getValue(),src.get().getName());
				records.add(r);
			}
		}
		return records;
	}

	@Override
	public void store(List records, String source) {
		Optional<Source> src = sourceRepository.findByName(source);
		// time_series_metric
		List<TimeSeriesMetric> timesMetric = new ArrayList<>();
		long timestamp = System.currentTimeMillis();
		int k = 0;
		for (Object o : records) {
			Row r = (Row) o;
			if (r.getKey().equalsIgnoreCase("#")) {
				continue;
			}
			TimeSeriesMetric m = new TimeSeriesMetric();
			m.setId(timestamp+k);
			k++;
			m.setTimestamp(r.getTimestamp());
			m.setKey(r.getKey());
			m.setLabels(r.getLabels());
			m.setValue(r.getValue());
			m.setSourceId(src.get().getId());
			//System.out.println(m);
			timesMetric.add(m);
		}
		timeSeriesMetricRepository.saveAll(new CustomTimeSeriesMetricIterable(timesMetric));

	}
	
	class CustomTimeSeriesMetricIterable implements Iterable<TimeSeriesMetric> { 
		List<TimeSeriesMetric> timesMetric = null;
		public CustomTimeSeriesMetricIterable(List<TimeSeriesMetric> timesMetric ) {
			this.timesMetric = timesMetric;
		}
	    // code for data structure 
	    public Iterator<TimeSeriesMetric> iterator() { 
	        return timesMetric.iterator(); 
	    } 
	}

	@Override
	public Iterable<Row> getMetrics(Condition cond, long timestamp,
			long sourceId, 
			int limit,
			int offset) {

		Optional<Float> metricValue;
		Map<String,String> queryTokens = parseConditionForQuery(cond);

		System.out.println("queryTokens.get(operator)"+queryTokens.get("operator"));
		
		System.out.println("queryTokens.get(key)"+queryTokens.get("key"));

		System.out.println("queryTokens.get(value)"+queryTokens.get("value"));
		
		System.out.println("timestamp "+timestamp);
		String value = queryTokens.get("value");
		Float fValue = Float.parseFloat(value);
		if (queryTokens.get("operator").equals("equals")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueEquals(queryTokens.get("key"), fValue, timestamp);
		} else if (queryTokens.get("operator").equals("greaterThan")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueGreaterThan(queryTokens.get("key"), fValue, timestamp);
		} else if (queryTokens.get("operator").equals("lessThan")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueLessThan(queryTokens.get("key"), fValue, timestamp);
		} else {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndTimestamp(queryTokens.get("key"), timestamp);
		}
		
		System.out.println(" metricValue "+metricValue);
		Row metric = new Row();
		metric.setKey(queryTokens.get("key"));
		metric.setTimestamp(timestamp);
		if (metricValue.isPresent()) {
			metric.setValue(metricValue.get().toString());
		}

		return Arrays.asList(metric);
	}

	private Map<String,String> parseConditionForQuery(Condition cond) {
		Map<String,String> queryTokens = new HashMap<>();
		queryTokens.put("key",cond.getLeft());
		queryTokens.put("value",cond.getRight());

		if (cond.getOperation().equalsIgnoreCase("==")) {
			queryTokens.put("operator","equals");
		} else if (cond.getOperation().equalsIgnoreCase(">")) {
			queryTokens.put("operator","greaterThan");
		} else if (cond.getOperation().equalsIgnoreCase("<")) {
			queryTokens.put("operator","lessThan");
		} else {
			queryTokens.put("operator",cond.getOperation());
		}
		return queryTokens;
	}

	@Override
	public Iterable<Row> getMetrics(long timestamp, int limit, int offset) {
		List<Row> records =  new ArrayList<>();
		for (TimeSeriesMetric metric: timeSeriesMetricRepository.listByTimestamp(timestamp, offset, limit)) {
			Optional<Source> src = sourceRepository.findById(metric.getSourceId());
			Row r = new Row(metric.getTimestamp(),metric.getKey(),metric.getLabels(),metric.getValue(),src.get().getName());
			records.add(r);
		}
		return new CustomIterable(records);
	}

	@Override
	public Iterable<Row> getKeys(int limit, int offset) {
		List<Row> records = new ArrayList<>();
		Iterable<String> metricsKey = timeSeriesMetricRepository.findAllKey(offset, limit);
		for (String m: metricsKey) {
			Row r = new Row(0L,
					m,null,null,null);
			records.add(r);
		}
		return new CustomIterable(records);
	}

	@Override
	public Optional<Row> getMetrics(Condition cond, long timestamp,
			long sourceId) {
		Optional<Float> metricValue;
		Optional<Row> record = Optional.empty();
		Map<String,String> queryTokens = parseConditionForQuery(cond);
		String value = queryTokens.get("value");
		Float fValue = Float.parseFloat(value);

		if (queryTokens.get("operator").equals("equals")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueEquals(queryTokens.get("key"), fValue, timestamp);
		} else if (queryTokens.get("operator").equals("greaterThan")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueGreaterThan(queryTokens.get("key"), fValue, timestamp);
		} else if (queryTokens.get("operator").equals("lessThan")) {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndValueLessThan(queryTokens.get("key"), fValue, timestamp);
		} else {
			metricValue = timeSeriesMetricRepository.findAverageByKeyAndTimestamp(queryTokens.get("key"), timestamp);
		}
		
		if (metricValue.isPresent()) {
			Row metric = new Row();
			metric.setKey(queryTokens.get("key"));
			metric.setTimestamp(timestamp);
			metric.setValue(metricValue.get().toString());
			record = Optional.of(metric);
		}
		System.out.println(" metricValue after evaluating "+metricValue);

		return record;
	}

}
