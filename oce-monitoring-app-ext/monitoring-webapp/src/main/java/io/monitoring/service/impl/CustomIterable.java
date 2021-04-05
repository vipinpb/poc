package io.monitoring.service.impl;

import io.monitoring.metrics.Row;

import java.util.Iterator;
import java.util.List;

public class CustomIterable implements Iterable<Row> { 
	List<Row> timesMetric = null;
	public CustomIterable(List<Row> timesMetric ) {
		this.timesMetric = timesMetric;
	}
    // code for data structure 
    public Iterator<Row> iterator() { 
        return timesMetric.iterator(); 
    } 
}
