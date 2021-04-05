/**
 * 
 */
package io.monitoring.manager;

import io.monitoring.domain.MetricSchedule;
import io.monitoring.domain.Source;
import io.monitoring.metrics.QueryResponse;
import io.monitoring.metrics.Record;
import io.monitoring.metrics.Status;
import io.monitoring.service.MetricScheduleService;
import io.monitoring.service.MetricService;
import io.monitoring.service.SourceService;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author vipink
 *
 */
public interface MetricsManager {
	
	MetricService getMetricService();
	
	SourceService getSourceService();
	
	MetricScheduleService getMetricScheduleService();
	
	QueryResponse keys(Map<String, String[]> parameters) ;
	/**
	 * Store the metrics in some storage
	 * @param metrics
	 */
    void store(List<String> metrics, String sourceName);
    
	/**
	 * Store the metrics in some storage
	 * @param metrics
	 */
     void store(Iterator<Record> metrics, String sourceName);
    
    /**
     * Return the response based on the metric passed.
     * It should honor Query part of the prometheus
     * https://prometheus.io/docs/prometheus/latest/querying/examples/
     * @param params
     * @return
     */
     QueryResponse query(String params);
    /**
     * Return the response based on the metric passed.
     * It should honor Query part of the prometheus
     * https://prometheus.io/docs/prometheus/latest/querying/examples/
     * @param parameters -- list of parameters passed
     * @return
     */
    QueryResponse query(Map<String, String[]> parameters);
    
	QueryResponse keys();
	
	QueryResponse match(Map<String, String[]> parameters);
	QueryResponse findByKey(Map<String, String[]> parameters);
}
