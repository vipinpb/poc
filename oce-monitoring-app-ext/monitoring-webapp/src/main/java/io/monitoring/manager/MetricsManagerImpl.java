/**
 * 
 */
package io.monitoring.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.monitoring.metrics.Metric;
import io.monitoring.metrics.MetricsParser;
import io.monitoring.metrics.Query;
import io.monitoring.metrics.QueryResponse;
import io.monitoring.metrics.Record;
import io.monitoring.metrics.Row;
import io.monitoring.rule.engine.Condition;
import io.monitoring.rule.engine.ConditionBuilder;
import io.monitoring.service.MetricScheduleService;
import io.monitoring.service.MetricService;
import io.monitoring.service.SourceService;

/**
 * @author vipink
 *
 */
@Service
public class MetricsManagerImpl implements MetricsManager {

	@Autowired
	MetricService metricService;
	@Autowired
	SourceService sourceService;
	@Autowired
	MetricScheduleService metricScheduleService;
	/**
	 * 
	 */
	public MetricsManagerImpl() {} 

	/* (non-Javadoc)
	 * @see oracle.monitoring.service.MetricsManager#store(java.lang.String)
	 */
	@Override
	public void store(List<String> metrics, String sourceName) {
		Long timestamp = System.currentTimeMillis();
		MetricsParser parser = new MetricsParser();
		
		List<Row> records = new ArrayList<>();
		//if (records == null ) { records = new ArrayList<>(); }
		for (String metric: metrics) {
			Row row = parser.row(timestamp, metric);
			records.add(row);
		}
		
		metricService.add(records, sourceName);
	}
	
	@Override
	public void store(Iterator<Record> metrics, String sourceName) {
		MetricsParser parser = new MetricsParser();
		List<Row> records = new ArrayList<>();
		while (metrics!= null && metrics.hasNext() ) {
			Record r = metrics.next();
			String labels = r.getLabels() != null ? convert(r.getLabels()) : "";
			Row row = new Row(r.getTimestamp(), r.getKey(), labels, r.getValue(),sourceName);
			records.add(row);
		}
		metricService.add(records, sourceName);
	}
	
	private String convert(Map<String, String> map) {
	    String mapAsString = map.keySet().stream()
	      .map(key -> key + "=" + map.get(key))
	      .collect(Collectors.joining(", ", "", ""));
	    return mapAsString;
	}
	/* (non-Javadoc)
	 * @see oracle.monitoring.service.MetricsManager#query(java.lang.String)
	 */
	@Override
	public QueryResponse query(String params) {
		System.out.println(" Query scraped metrics");
		int limit = 100;
		int offset = 0;
		Map<String, String[]> parameters = new HashMap<>();
		Iterable<Row> records = metricService.getMetrics(parameters, limit, offset);
		
		List<Metric> items = new ArrayList<>();
		
		QueryResponse<Metric> res = new QueryResponse();
		res.setHasMore(false);
		//res.setCount(records.size());
		res.setLimit(100);
		
		for (Row r : records) {
			Metric m = new Metric();
			m.setKey(r.getKey());
			m.setLabels(parseLabels(r.getLabels()));
			m.setValue(r.getValue());
			items.add(m);
		}
		res.setItems((Metric[])items.toArray());
		
		return res;
	}

	@Override
	public QueryResponse query(Map<String, String[]> parameters) {
		String values[] = parameters.get("q");
		String queryStr = values[0] ;
		Query query = new Query(queryStr);
		int limit = 100;
		int offset = 0;
		Iterable<Row> records = metricService.getMetrics(parameters, limit,offset);
		List<Metric> items = new ArrayList<>();
		QueryResponse<Metric> res = new QueryResponse<>();
		res.setHasMore(false);
		res.setLimit(100);
		
		for (Row r : records) {
			if (r.getKey().contains(query.getKey())) {
				Metric m = new Metric();
				m.setKey(query.getKey());
				m.setLabels(parseLabels(r.getLabels()));
				m.setValue(r.getValue());
				items.add(m);
			}
		}
		res.setCount(items.size());
		res.setItems((Metric[])items.toArray(new Metric[items.size()]));
		return res;
	}
	
	@Override
	public QueryResponse keys(Map<String, String[]> parameters) {
		int limit = 100; // default limit
		int offset = 0;
		String limits[] = parameters.get("limit");
		if (limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}
		String offsets[] = parameters.get("offset");
		if (offsets != null && offsets.length > 0) {
			offset = Integer.parseInt(offsets[0]);
		}

		Iterable<Row> records = metricService.getKeys(limit, offset);
		List<String> items = new ArrayList<>();
		QueryResponse<String> res = new QueryResponse<>();
		res.setHasMore(false);
		res.setLimit(limit);
		for (Row r : records) {
			if (!items.contains(r.getKey())) {
				items.add(r.getKey());
			}
		}
		res.setHasMore(items.size() > limit );
		res.setCount(items.size());
		res.setItems((String[])items.toArray(new String[items.size()]));
		return res;
	}
	
	private Map<String,String> parseLabels(String str) {
		Map<String,String> labels = new HashMap<>();
		if (str == null) { return labels; }
		String token[] = str.split(",");
		for (String tokenPair : token) {
			String nameValue[] = tokenPair.split("=");
			if (nameValue.length != 2) {
				continue;
			}
			labels.put(nameValue[0].trim(), nameValue[1].replaceAll("\"", ""));
		}
		return labels;
	}
	
	public QueryResponse match(String exp) {
		String tokens[] = exp.split(" ");
		
		// replace variables if any ${test}
		// build condition out of tokens
		ConditionBuilder builder = new ConditionBuilder();
		for (String token: tokens) {
			builder.push(token);
		}
		
		Condition cond = builder.build();
		String left = cond.getLeft();
		String right = cond.getRight();
		
		// get current timestamp
		Long timestamp = System.currentTimeMillis() - 60000;
		// get left value
		int limit = 100;
		int offset = 0;
		Iterable<Row> records = metricService.getMetrics(timestamp, limit, offset);
		List<Metric> items = new ArrayList<>();
		List<Metric> finalItems = new ArrayList<>();

		QueryResponse<Metric> res = new QueryResponse<>();
		res.setHasMore(false);
		res.setLimit(100);
		
		for (Row r : records) {
			if (r.getTimestamp() > timestamp && r.getKey().contains(left)) {
				Metric m = new Metric();
				m.setKey(r.getKey());
				m.setLabels(parseLabels(r.getLabels()));
				m.setValue(r.getValue());
				items.add(m);
				//System.out.println(" Found "+ r.getKey());
			}
		}
		for (Metric r: items) {
			boolean valid = evaluate (r, cond);
			if (valid) {
				finalItems.add(r);
			}
		}
		res.setCount(finalItems.size());
		res.setItems((Metric[])finalItems.toArray(new Metric[finalItems.size()]));
		return res;
	}

	private boolean evaluate(Metric r, Condition cond) {
		
		String value = r.getValue(); 
		String condStr = cond.getRight();
		try {
			long valueLong = Long.parseLong(condStr);
			String operation = cond.getOperation();
			if (operation.equalsIgnoreCase("==")) {
				return Long.parseLong(value) == valueLong ;
			} else if (operation.equalsIgnoreCase(">")) {
				return Long.parseLong(value) > valueLong ;
			} else if (operation.equalsIgnoreCase("<")) {
				return Long.parseLong(value) < valueLong ;
			} else if (operation.equalsIgnoreCase(">=")) {
				return Long.parseLong(value) >= valueLong ;
			}
		} catch (Exception ex) {
			// get the value from Metric
		}
		
		
		return false;
	}

	@Override
	public QueryResponse keys() {
		int limit = 100;
		int offset = 0;
		Map<String, String[]> parameters = new HashMap<>();
		Iterable<Row> records = metricService.getMetrics(parameters, limit, offset);
		List<String> items = new ArrayList<>();
		QueryResponse<String> res = new QueryResponse<>();
		res.setHasMore(false);
		res.setLimit(100);
		for (Row r : records) {
			if (!items.contains(r.getKey())) {
				items.add(r.getKey());
			}
		}
		res.setCount(items.size());
		res.setItems((String[])items.toArray(new String[items.size()]));
		return res;
	}

	@Override
	public QueryResponse match(Map<String, String[]> parameters) {
		String values[] = parameters.get("q");
		return match(values[0]);
	}

	@Override
	public QueryResponse findByKey(Map<String, String[]> parameters) {
		int limit = 100; 
		int offset = 0;
		String limits[] = parameters.get("limit");
		if (limits != null && limits.length > 0) {
			limit = Integer.parseInt(limits[0]);
		}
		String offsets[] = parameters.get("offset");
		if (offsets != null && offsets.length > 0) {
			offset = Integer.parseInt(offsets[0]);
		}
		Iterable<Row> records = metricService.getMetrics(parameters, limit, offset);
		List<Metric> items = new ArrayList<>();
		QueryResponse<Metric> res = new QueryResponse<>();
		res.setLimit(limit);
		int rCount = 0;
		for (Row r : records) {
			    if (rCount < (offset * limit)) {
			    	continue;
			    }
				Metric m = new Metric();
				m.setKey(r.getKey());
				m.setLabels(parseLabels(r.getLabels()));
				m.setValue(r.getValue());
				items.add(m);
				if (items.size() == limit) {
					break ;
				}
				rCount ++;
			
		}
		res.setHasMore(items.size() > limit );
		res.setCount(items.size());
		//res.setTotalResults(records.);
		res.setItems((Metric[])items.toArray(new Metric[items.size()]));
		return res;
	}

	@Override
	public MetricService getMetricService() {
		return metricService;
	}

	@Override
	public SourceService getSourceService() {
		return sourceService;
	}

	@Override
	public MetricScheduleService getMetricScheduleService() {
		return metricScheduleService;
	}
	
	
}
